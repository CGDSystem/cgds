package com.cgds.postecontrol.rmi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.cgds.interfaces.communication.DroneCommunicationValue;
import com.cgds.interfaces.detection.DetectionAlerteValue;
import com.cgds.interfaces.postecontrole.PosteControleInt;
import com.cgds.postecontrol.ihm.Supervision;
import com.cgds.postecontrol.ihm.VisuSwg;
import com.cgds.postecontrol.ihm.Visualisation;

public class PosteControleConnection extends UnicastRemoteObject implements
		PosteControleInt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Supervision supervision = null;
	private Visualisation visualisation = null;
	private VisuSwg visuSwg = null;
	
	
	private PosteDeControleController controller;

	public PosteControleConnection(Supervision supervision, PosteDeControleController controller)
			throws RemoteException {
		super();
		this.supervision = supervision;
		this.controller = controller;
	}

	public PosteControleConnection(Visualisation visualisation, PosteDeControleController controller)
			throws RemoteException {
		super();
		this.visualisation = visualisation;
		this.controller = controller;
	}
	
	public PosteControleConnection(VisuSwg visuSwg, PosteDeControleController controller)
			throws RemoteException {
		super();
		this.visuSwg = visuSwg;
		this.controller = controller;
	}


	@Override
	public void droneUpdate(DroneCommunicationValue updateMsg)
			throws RemoteException {
		try {
			System.out.println(updateMsg.getId());
			if (supervision != null && updateMsg.getInfos() != null)
				supervision.update(updateMsg.getInfos());
			if (visualisation != null && updateMsg.getImage() != null)
				visualisation.update(updateMsg.getImage());
			if (visuSwg != null && updateMsg.getImage() != null)
				visuSwg.update(updateMsg.getImage());
		} catch (Exception e) {
			controller.deconnexionFermetureInterface(this);
		}
	}

	@Override
	public void alerte(DetectionAlerteValue alerte) throws RemoteException {
		System.out.println("Alerte recu ! : "+alerte.getLatitude()+" "+alerte.getLongitude());
	}

}
