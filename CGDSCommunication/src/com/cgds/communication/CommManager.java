package com.cgds.communication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.cgds.communication.client.CommClientObservable;
import com.cgds.communication.drone.CommDroneInterface;
import com.cgds.communication.drone.CommDroneMorseInterface;
import com.cgds.communication.drone.DroneCommunicationInt;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.interfaces.drone.DroneInt;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public class CommManager extends UnicastRemoteObject implements CommManagerInt, Serializable  {

	private static final long serialVersionUID = 1L;

	int nbDrone = 0;
	HashMap<String, CommClientObservable> dronesObservable;
	HashMap<String, DroneCommunicationInt> dronesInterfaces;

	protected CommManager() throws RemoteException {
		super();
		dronesObservable = new HashMap<>();
		dronesInterfaces = new HashMap<>();
		// TODO Auto-generated constructor stub
	}

	
	public void abonnerPosteDeControle (PosteControleInt poste, String droneNom) throws RemoteException{
		dronesObservable.get(droneNom).addObserver(poste);
	}

	public void ajouterDrone (DroneInt drone) throws RemoteException{
		CommClientObservable clientObservable = new CommClientObservable();
		CommDroneInterface droneInterface = new CommDroneInterface(drone, clientObservable);
		String droneNom = "Drone"+nbDrone++;
		dronesObservable.put(droneNom, clientObservable);
		dronesInterfaces.put(droneNom, droneInterface);
	}
	
	public CommClientObservable ajouterDroneSocket (CommDroneMorseInterface morseInt) throws RemoteException{
		CommClientObservable clientObservable = new CommClientObservable();
		String droneNom = "Drone"+nbDrone++;
		dronesObservable.put(droneNom, clientObservable);
		dronesInterfaces.put(droneNom, morseInt);
		return clientObservable;
	}
	
	public void envoyerCommande(String droneNom, String... args) throws RemoteException {
		dronesInterfaces.get(droneNom).envoyerCommande(args);
		
	}


	@Override
	public List<String> getListeDrones() throws RemoteException {
		return new ArrayList<>(dronesInterfaces.keySet());
	}
	
	public void verifierConnexion(){
		HashSet<String> listeDrones = new HashSet<>(dronesInterfaces.keySet());
		for (String droneNom : listeDrones) {
			DroneCommunicationInt  commDrone = this.dronesInterfaces.get(droneNom);
			if(!commDrone.ping()){
				dronesObservable.remove(droneNom);
				dronesInterfaces.remove(droneNom);
			}
		}
	}


}
