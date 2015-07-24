package com.cgds.detection.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import com.cgds.interfaces.detection.CalculCommManagerInt;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public class CommManager extends UnicastRemoteObject implements CalculCommManagerInt  {


		private static final long serialVersionUID = 1L;

		int nbDrone = 0;
		HashMap<String, ConnectionComm> imageObservables;

		public CommManager() throws RemoteException {
			super();
			imageObservables = new HashMap<>();

		}

		
		public void abonnerPosteDeControle (PosteControleInt poste, String droneNom) throws RemoteException{
			imageObservables.get(droneNom).addObserver(poste);
		}

		public ConnectionComm ajouterDrone (String drone) throws RemoteException{
			ConnectionComm imageObservable = new ConnectionComm();
			imageObservables.put(drone, imageObservable);
			return imageObservable;
		}
		
}
