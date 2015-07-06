package com.cgds.interfaces.postecontrole;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cgds.interfaces.drone.DroneCommunicationValue;


public interface PosteControleInt extends Remote {
		//Observer - methode appelé par la Communication pour envoyer une mise à jour
	    void droneUpdate(DroneCommunicationValue updateMsg) throws RemoteException;

}
