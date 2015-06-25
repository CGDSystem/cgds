package com.cgds.communication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.cgds.interfaces.communication.CommDroneInt;
import com.cgds.interfaces.drone.DroneCommunicationValue;
import com.cgds.interfaces.drone.DroneInt;

public class CommDroneInterface extends UnicastRemoteObject implements CommDroneInt, Serializable {
	

	private static final long serialVersionUID = 1L;
	private DroneInt droneInt = null;
	private CommClientObservable clientObservable;
	private String droneNom = null;

	protected CommDroneInterface(DroneInt drone,CommClientObservable clientObservable) throws RemoteException {
		super();
		this.clientObservable = clientObservable;
		droneInt = drone;
		drone.addObserver(this);
		droneNom = drone.getNom();
		System.out.println("Connexion avec " + droneNom);
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
		
		System.out.println("CommDroneInterface - Message recu de "+droneNom+": " + messageBuild.toString());
		clientObservable.notifier(updateMsg);
		
	}
	
	public void envoyerCommande(String... args) throws RemoteException {
		droneInt.recevoirCommande(args);
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
