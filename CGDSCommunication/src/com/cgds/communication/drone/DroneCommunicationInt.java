package com.cgds.communication.drone;


public interface DroneCommunicationInt {
		
	public void envoyerCommande(String... args);
	
	public Boolean ping();
	
}
