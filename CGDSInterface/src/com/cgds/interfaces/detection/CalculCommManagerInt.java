package com.cgds.interfaces.detection;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.cgds.interfaces.postecontrole.PosteControleInt;

public interface CalculCommManagerInt extends Remote {
	public void abonnerPosteDeControle (PosteControleInt poste, String droneNom) throws RemoteException;
	public CalculCommInt ajouterDrone (String drone) throws RemoteException;
}
