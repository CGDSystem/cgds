package com.cgds.interfaces.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.cgds.interfaces.drone.DroneCommunicationValue;

public interface CommDroneInt extends Remote {
	//Observer - methode appelé par les Drones pour envoyer une mise à jour
    void droneUpdate(Object observable, DroneCommunicationValue updateMsg) throws RemoteException;

    //Observer - methode appelé par les Drones pour abonner l'interface
//    void addDrone(DroneInt drone) throws RemoteException;
	
    //Observable - méthode appelé par les autres composant pour s'abonner aux Drones
//  public void addObserver(CommInt observer) throws RemoteException;
}