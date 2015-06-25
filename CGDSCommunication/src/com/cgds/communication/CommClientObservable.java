package com.cgds.communication;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.cgds.interfaces.drone.DroneCommunicationValue;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public class CommClientObservable extends Observable{
	
	
	protected CommClientObservable() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	public void addObserver(PosteControleInt observer) throws RemoteException {
		WrappedObserver obs = new WrappedObserver(observer);
        addObserver(obs);
        System.out.println("Connexion avec un poste de controle");
		
	}
	
	public void notifier(final DroneCommunicationValue message) {
		setChanged();
		notifyObservers(message);
}
	
	private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private PosteControleInt ro = null;

        public WrappedObserver(PosteControleInt ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.droneUpdate((DroneCommunicationValue) arg);
                ((DroneCommunicationValue) arg).getId();
            } catch (RemoteException e) {
                System.out
                        .println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
                e.printStackTrace();
            }
        }
    }
}
