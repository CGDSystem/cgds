package com.cgds.interfaces.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cgds.interfaces.postecontrole.PosteControleInt;

public interface CommClientInt extends Remote {
	//Observable - méthode appelé par les poste de controle pour s'abonner au Drone
	public void abonner(PosteControleInt observer, String droneNom) throws RemoteException;
	public void envoyerCommande(String droneNom, List<String> args) throws RemoteException;
}
