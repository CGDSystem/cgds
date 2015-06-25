package com.cgds.interfaces.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cgds.interfaces.drone.DroneInt;
import com.cgds.interfaces.postecontrole.PosteControleInt;

public interface CommManagerInt extends Remote {

	
	public void abonnerPosteDeControle (PosteControleInt poste, String droneNom) throws RemoteException;
	public void ajouterDrone (DroneInt drone) throws RemoteException;
	public void envoyerCommande(String droneNom, String... args) throws RemoteException;
	public List<String> getListeDrones() throws RemoteException;
}
