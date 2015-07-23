package com.cgds.communication.client;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.cgds.interfaces.communication.DroneCommunicationValue;
import com.cgds.interfaces.postecontrole.PosteControleInt;
/**
 * L'objet produit par cette classe:
 * 	 est une interface entre un PosteDeControle et un CommDroneInterface
 * 	 est un adaptateur d'Observable pour un CommDroneInterface
 *	 produit un adaptateur d'Observeur à un PosteDeControle
 * @author hps
 *
 */
public class CommClientObservable extends Observable{
	
	
	public CommClientObservable() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void addObserver(PosteControleInt observer) {
		//Création de l'adaptateur du poste de controle à abonner
		WrappedObserver obs = new WrappedObserver(observer);
		//Abonnement du WrappedObserver à CommClientObservable
        addObserver(obs);
        System.out.println("Connexion avec un poste de controle");
		
	}
	
	public void notifier(final DroneCommunicationValue message) {
		setChanged();
		//Appel de la méthode update des Observer (qui sont des WrappedObserver de PosteDeControle)
		notifyObservers(message);
	}
	
	public void notifierImage(final byte[] image) {
		setChanged();
		DroneCommunicationValue message = new DroneCommunicationValue();
		message.setImage(image);
		//Appel de la méthode update des Observer (qui sont des WrappedObserver de PosteDeControle)
		notifyObservers(message);
	}
	
	public void notifierInfo(final List<String> infos) {
		setChanged();
		DroneCommunicationValue message = new DroneCommunicationValue();
		message.setInfos(infos);
		//Appel de la méthode update des Observer (qui sont des WrappedObserver de PosteDeControle)
		notifyObservers(message);
	}
	/**
	 * Adaptateur - PosteDeControle/Observer 
	 * Appel la methode update du Poste de controle distant
	 * 
	 * @author hps
	 *
	 */
	private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private PosteControleInt ro = null;

        public WrappedObserver(PosteControleInt ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.droneUpdate(new DroneCommunicationValue((DroneCommunicationValue) arg));
            } catch (RemoteException e) {
                System.out
                        .println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
                e.printStackTrace();
            }
        }
    }
}
