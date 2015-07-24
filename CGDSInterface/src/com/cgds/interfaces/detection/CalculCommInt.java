package com.cgds.interfaces.detection;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.cgds.interfaces.communication.DroneCommunicationValue;

public interface CalculCommInt extends Remote{
	
	public void updateImage(DroneCommunicationValue droneValue) throws RemoteException;
	
}
