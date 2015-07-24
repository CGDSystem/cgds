package com.cgds.detection.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.cgds.interfaces.communication.DroneCommunicationValue;
import com.cgds.interfaces.detection.CalculCommInt;
import com.cgds.interfaces.detection.DetectionAlerteValue;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public class ConnectionComm extends UnicastRemoteObject implements CalculCommInt {

	private static final long serialVersionUID = 1L;

	ArrayList<PosteControleInt> listPostesAbonnes;
	
	public ConnectionComm() throws RemoteException{
		super();
		listPostesAbonnes = new ArrayList<PosteControleInt>();
	}
	

	@Override
	public void updateImage(DroneCommunicationValue droneValue)
			throws RemoteException {
		//L'image arrive ici
		if (droneValue != null && droneValue.getImage() != null) {
			System.out.println("Image reçu");
		}else{
			System.out.println("Aucune donnée reçue");
		}
		
		//L'alerte passe par-là
		DetectionAlerteValue alerte = new DetectionAlerteValue();
		alerte.setLatitude(10);
		alerte.setLongitude(20);
		notifierAlert(alerte);
	}	
	
	public void addObserver(PosteControleInt observer) {
		listPostesAbonnes.add(observer);
        System.out.println("Connexion avec un poste de controle");	
	}
	
	
	public void notifierAlert(DetectionAlerteValue alerte) {
		DroneCommunicationValue message = new DroneCommunicationValue();

		for (PosteControleInt posteControleInt : listPostesAbonnes) {
			UpdateAsyncrone notificationAsyncrone = new UpdateAsyncrone(posteControleInt, alerte);
			notificationAsyncrone.start();
		}
	}
	
	public class UpdateAsyncrone extends Thread {

		PosteControleInt arg = null;
		DetectionAlerteValue alerte = null;
		
		public UpdateAsyncrone(PosteControleInt arg,DetectionAlerteValue alerte) {
			this.arg = arg;
			this.alerte = alerte;
		}
		
		@Override
		public void run() {
			try {
				arg.alerte(alerte);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
