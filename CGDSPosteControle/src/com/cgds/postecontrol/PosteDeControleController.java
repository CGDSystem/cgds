package com.cgds.postecontrol;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.postecontrol.ihm.Supervision;
import com.cgds.postecontrol.ihm.Visualisation;
import com.cgds.postecontrol.rmi.PosteControleConnection;

public class PosteDeControleController {

	CommManagerInt commManager;
	List<String> nomsDrones;

	public PosteDeControleController(CommManagerInt commManager) {
		this.commManager = commManager;

	}

	public void creerConnexionSupervision(String nomDrone) {

		try {
			Supervision supervision = new Supervision(nomDrone);
			PosteControleConnection posteControle = null;
			posteControle = new PosteControleConnection(supervision,this);
			commManager.abonnerPosteDeControle(posteControle,
					nomDrone);
			supervision.open();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void creerConnexionVisualisation(String nomDrone, Visualisation visualisation) {

		try {
			PosteControleConnection posteControle = null;
			posteControle = new PosteControleConnection(visualisation,this);
			commManager.abonnerPosteDeControle(posteControle,nomDrone);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	
	public String[] listeDrones() {
		List<String> listeDrones = new ArrayList<>();
		String[] args = new String[listeDrones.size()];
		try {
			listeDrones = commManager.getListeDrones();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeDrones.toArray(args);
	}
	
	public void deconnexionFermetureInterface(PosteControleConnection conn){
		try {
			UnicastRemoteObject.unexportObject(conn, true);
		} catch (NoSuchObjectException e) {
			System.out.println("Erreur deconnexion Interface");
		}
	}
}
