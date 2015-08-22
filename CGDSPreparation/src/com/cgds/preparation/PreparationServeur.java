package com.cgds.preparation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;

public class PreparationServeur {

	public static void main(String[] args) {

		try {

			// Connection au manager
			Registry registry = LocateRegistry.getRegistry("localhost",
					Constants.COMM_RMI_PORT);
			CommManagerInt commManager = (CommManagerInt) registry
					.lookup(Constants.COMM_RMI_ID);

			
			System.out.println("Liste des drones connectées:");

				List<String> listeDrones = commManager.getListeDrones();

				if (listeDrones.isEmpty()){	
					System.out.println("Aucun");
				}else{
					int i = 0;
					for(String drone : listeDrones){
						i++;
						System.out.println(i +" - " + drone);
					}
				}


			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}