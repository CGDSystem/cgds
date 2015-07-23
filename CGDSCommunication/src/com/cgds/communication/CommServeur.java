package com.cgds.communication;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import com.cgds.communication.rmi.CommManager;
import com.cgds.communication.socket.CommSocketServeur;
import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.interfaces.detection.CalculCommInt;
import com.cgds.interfaces.detection.CalculCommManagerInt;
import com.cgds.interfaces.drone.DroneInt;

public class CommServeur {

	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException, NotBoundException {
		// CommServeur
		CommManager commManager = new CommManager();
		Registry registry1 = LocateRegistry
				.createRegistry(Constants.COMM_RMI_PORT);
		registry1.bind(Constants.COMM_RMI_ID, commManager);
		
		// Detection
		Registry registry2 = LocateRegistry.getRegistry("localhost",
				Constants.CALCUL_RMI_PORT);
		CalculCommManagerInt detectionInterface = (CalculCommManagerInt) registry2
				.lookup(Constants.CALCUL_RMI_ID);
		commManager.definirDetection(detectionInterface);
		
		//Vérification Connexion
		CommServeur comServeur = new CommServeur();
		comServeur.new VerifierConnexionThread(commManager).start();
		
		//Lancement du serveur de Socket
		new CommSocketServeur(commManager).start();
	}

	public class VerifierConnexionThread extends Thread {

		CommManager commManager;

		public VerifierConnexionThread(CommManager commManager) {
			this.commManager = commManager;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// ignores
				}
				commManager.verifierConnexion();
			}
		}
	}

}
