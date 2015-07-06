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

import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.interfaces.drone.DroneInt;

public class ComServeur {

	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException {
		// CommServeur
		CommManager commManager = new CommManager();
		Registry registry1 = LocateRegistry
				.createRegistry(Constants.COMM_RMI_PORT);
		registry1.bind(Constants.COMM_RMI_ID, commManager);
		ComServeur comServeur = new ComServeur();
		comServeur.new VerifierConnexionThread(commManager).start();
		new CommSocketServeur(commManager).start();
		// Registry registry2 =
		// LocateRegistry.getRegistry("localhost",Constants.DRONE_RMI_PORT);
		// DroneInt drone1 = null;
		// DroneInt drone2 = null;
		// try {
		// drone1 = (DroneInt) registry2.lookup(Constants.DRONE1_RMI_ID);
		// drone2 = (DroneInt) registry2.lookup(Constants.DRONE2_RMI_ID);
		// } catch (NotBoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// droneInterface.addDrone(drone1);
		// droneInterface.addDrone(drone2);
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
