package com.cgds.interfaces.postecontrole;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cgds.interfaces.communication.DroneCommunicationValue;
import com.cgds.interfaces.detection.DetectionAlerteValue;


public interface PosteControleInt extends Remote {
		//Observer - methode appelé par la Communication pour envoyer une mise à jour
	    void droneUpdate(DroneCommunicationValue updateMsg) throws RemoteException;
	    void alerte(DetectionAlerteValue alerte) throws RemoteException;
}
