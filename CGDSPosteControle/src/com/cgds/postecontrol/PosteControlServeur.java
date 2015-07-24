package com.cgds.postecontrol;

import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.postecontrol.ihm.Accueil;
import com.cgds.postecontrol.ihm.AccueilSwg;


public class PosteControlServeur {
	
	
	public static void main(String[] args) {
		
		try {
			
			//Connection au manager
			Registry registry = LocateRegistry.getRegistry("localhost",Constants.COMM_RMI_PORT);
			CommManagerInt commManager = (CommManagerInt) registry.lookup(Constants.COMM_RMI_ID);

//			for (String string : commManager.getListeDrones()) {
//				System.out.println(string);
//			}
//			
			//Ouverture de l'Accueil
			final PosteDeControleController controller = new PosteDeControleController(commManager);
			Accueil window = new Accueil(controller);
			
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						AccueilSwg frame = new AccueilSwg(controller);
//						frame.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
