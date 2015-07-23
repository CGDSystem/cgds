package com.cgds.communication.socket;

import java.net.ServerSocket;

import com.cgds.communication.rmi.CommManager;
import com.cgds.interfaces.Constants;

public class CommSocketServeur extends Thread{
	
	CommManager commManager;
	
	
	public CommSocketServeur(CommManager commManager){
		this.commManager = commManager;
	}
	
	public void run(){
		    try
		    {
		      Integer port=new Integer(Constants.COMM_SOCKET_PORT);

		      ServerSocket ss = new ServerSocket(port.intValue()); // ouverture d'un socket serveur sur port
		      //printWelcome(port);
		      while (true) // attente en boucle de connexion (bloquant sur ss.accept)
		      {
		    	
		        new CommDroneMorseInterface(ss.accept(),commManager); // un client se connecte, un nouveau thread client est lancé
		      }
		    }
		    catch (Exception e) { }
	}
}
