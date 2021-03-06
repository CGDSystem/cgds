package com.cgds.interfaces.drone;


import java.rmi.Remote;
import java.rmi.RemoteException;

import com.cgds.interfaces.communication.CommDroneInt;
import com.cgds.interfaces.preparation.ParametresMission;

// Interface de l'observable du modele.
// Les vues qui s'abonnent utilisent cette interface
public interface DroneInt extends Remote
{
	//Observable - méthode appelé par le serveur de Communication pour s'abonner au Drone
    public void addObserver(CommDroneInt observer) throws RemoteException;
    
    public String getNom() throws RemoteException;
    
    public void recevoirCommande(String... args) throws RemoteException;
    
    //Renvoi les parametres de la mission (Configuration)
    public ParametresMission getParametresMission() throws RemoteException;
    
    public Boolean ping() throws RemoteException;
}