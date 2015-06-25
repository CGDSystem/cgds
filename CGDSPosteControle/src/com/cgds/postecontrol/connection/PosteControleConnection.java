package com.cgds.postecontrol.connection;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.cgds.interfaces.drone.DroneCommunicationValue;
import com.cgds.interfaces.postecontrole.PosteControleInt;
import com.cgds.postecontrol.ihm.Supervision;
import com.cgds.postecontrol.ihm.Visualisation;

public class PosteControleConnection extends UnicastRemoteObject implements
		PosteControleInt {

	private Supervision supervision = null;
	private Visualisation visualisation = null;
	
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

	@Override
	public void droneUpdate(DroneCommunicationValue updateMsg)
			throws RemoteException {
		try {
			System.out.println(updateMsg.getId());
			if (supervision != null)
				supervision.update(updateMsg.getInfos());
			if (visualisation != null)
				visualisation.update(updateMsg.getImage());
		} catch (Exception e) {
			controller.deconnexionFermetureInterface(this);
		}
	}
}
