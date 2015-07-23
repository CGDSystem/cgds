package com.cgds.communication.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.cgds.communication.client.CommClientObservable;
import com.cgds.communication.drone.DroneCommunicationInt;
import com.cgds.interfaces.communication.CommDroneInt;
import com.cgds.interfaces.communication.DroneCommunicationValue;
import com.cgds.interfaces.detection.CalculCommInt;
import com.cgds.interfaces.drone.DroneInt;

public class CommDroneInterface extends UnicastRemoteObject implements CommDroneInt, DroneCommunicationInt, Serializable {
	

	private static final long serialVersionUID = 1L;
	private DroneInt droneInt = null;
	private CommClientObservable clientObservable;
	private String droneNom = null;
	private CalculCommInt detectionCommunication = null;

	public CommDroneInterface(DroneInt drone,CommClientObservable clientObservable,CalculCommInt detectionCommunication) throws RemoteException {
		super();
		this.clientObservable = clientObservable;
		droneInt = drone;
		drone.addObserver(this);
		droneNom = drone.getNom();
		System.out.println("Connexion avec " + droneNom);
		if(detectionCommunication != null){
			this.detectionCommunication = detectionCommunication; 
		}
	}


	@Override
	//Méthode appelé par les drones pour envoyer de nouvelles données
	public void droneUpdate(Object observable, DroneCommunicationValue updateMsg)
			throws RemoteException {
		String droneNom = (String) observable;
		
		StringBuilder messageBuild = new StringBuilder();
		List<String> args = updateMsg.getInfos();
		for (String string : args) {
			messageBuild.append(string);
			messageBuild.append(" ");
		}
		
		boolean detectionOK = false;
		if(detectionCommunication != null){
			this.detectionCommunication.updateImage(updateMsg);
			detectionOK = true;
		}
		
		System.out.println("CommDroneInterface - Message recu de "+droneNom+" Detection: " + (detectionOK?"OK":"KO"));
		clientObservable.notifier(updateMsg);
		
	}
	
	public void envoyerCommande(String... args) {
		try {
			droneInt.recevoirCommande(args);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean ping(){
		try {
			return droneInt.ping();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Connexion perdu avec " + droneNom);
			return false;
		}
	}
	
}
