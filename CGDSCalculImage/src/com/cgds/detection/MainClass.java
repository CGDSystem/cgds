package com.cgds.detection;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



import com.cgds.detection.rmi.CommManager;
import com.cgds.interfaces.Constants;

public class MainClass {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		// CommServeur
		CommManager commManager = new CommManager();
		Registry registry1 = LocateRegistry
				.createRegistry(Constants.CALCUL_RMI_PORT);
		registry1.bind(Constants.CALCUL_RMI_ID, commManager);
	}

}
