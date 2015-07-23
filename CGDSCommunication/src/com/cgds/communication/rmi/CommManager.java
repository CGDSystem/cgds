package com.cgds.communication.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.cgds.communication.client.CommClientObservable;
import com.cgds.communication.drone.DroneCommunicationInt;
import com.cgds.communication.socket.CommDroneMorseInterface;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.interfaces.detection.CalculCommInt;
import com.cgds.interfaces.detection.CalculCommManagerInt;
import com.cgds.interfaces.drone.DroneInt;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public class CommManager extends UnicastRemoteObject implements CommManagerInt, Serializable  {

	private static final long serialVersionUID = 1L;

	int nbDrone = 0;
	HashMap<String, CommClientObservable> dronesObservable;
	HashMap<String, DroneCommunicationInt> dronesInterfaces;
	CalculCommManagerInt detection = null;

	public CommManager() throws RemoteException {
		super();
		dronesObservable = new HashMap<>();
		dronesInterfaces = new HashMap<>();
		// TODO Auto-generated constructor stub
	}

	
	public void abonnerPosteDeControle (PosteControleInt poste, String droneNom) throws RemoteException{
		dronesObservable.get(droneNom).addObserver(poste);
		detection.abonnerPosteDeControle(poste, droneNom);
	}

	public void definirDetection(CalculCommManagerInt detection){
		this.detection = detection;
		
	}
	
	public void ajouterDrone (DroneInt drone) throws RemoteException{
		CalculCommInt calculComm = null;
		if(this.detection != null){
			calculComm = this.detection.ajouterDrone(drone.getNom());		
		}
		CommClientObservable clientObservable = new CommClientObservable();
		CommDroneInterface droneInterface = new CommDroneInterface(drone, clientObservable,calculComm);

		dronesObservable.put(drone.getNom(), clientObservable);
		dronesInterfaces.put(drone.getNom(), droneInterface);
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
