package com.cgds.communication.drone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import com.cgds.communication.CommManager;
import com.cgds.communication.client.CommClientObservable;

public class CommDroneMorseInterface implements DroneCommunicationInt, Runnable {
	
	private Thread _t; // contiendra le thread du client
	  private Socket _s; // recevra le socket liant au client
	  private PrintWriter _out; // pour gestion du flux de sortie
	  private BufferedReader _in; // pour gestion du flux d'entrée
	  private int _numClient=0; // contiendra le numéro de client géré par ce thread
	  private  CommClientObservable _commClientObservable;

	public CommDroneMorseInterface(Socket s, CommManager commManager) throws RemoteException // le param s est donnée dans BlablaServ par ss.accept()
	  {
		
		_commClientObservable=commManager.ajouterDroneSocket(this); // passage de local en global (pour gestion dans les autres méthodes)
	    _s=s; // passage de local en global
	    try
	    {
	      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
	      _out = new PrintWriter(_s.getOutputStream());
	      // fabrication d'une variable permettant l'utilisation du flux d'entrée avec des string
	      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	      // ajoute le flux de sortie dans la liste et récupération de son numero
	      //_numClient = blablaServ.addClient(_out);
	    }
	    catch (IOException e){ }

	    _t = new Thread(this); // instanciation du thread
	    _t.start(); // demarrage du thread, la fonction run() est ici lancée
	  }

	public void run()
	  {
	    String message = ""; // déclaration de la variable qui recevra les messages du client
	    // on indique dans la console la connection d'un nouveau client
	    System.out.println("Un nouveau client s'est connecte, no "+_numClient);
	    try
	    {
	      // la lecture des données entrantes se fait caractère par caractère ...
	      // ... jusqu'à trouver un caractère de fin de chaine
	      char charCur[] = new char[1]; // déclaration d'un tableau de char d'1 élement, _in.read() y stockera le char lu
	      boolean typeTrouve = false;
	      while(_in.read(charCur)!=-1) // attente en boucle des messages provenant du client (bloquant sur _in.read())
	      {
			String raw = new String(charCur);
			String typeMessage = raw.substring(0,3);
			
			if(typeMessage.equals("IMG") || typeMessage.equals("INF")){
				if(typeMessage.equals("IMG")){
					_commClientObservable.notifierImage(Base64.getDecoder().decode(raw.substring(3)));
				}else if(typeMessage.equals("INF")){
					String[] array = raw.substring(3).split(",");
					ArrayList<String> listInfos = new ArrayList<String>(Arrays.asList(array));
					_commClientObservable.notifierInfo(listInfos);
				}
			}
	   
//	          _blablaServ.sendAll(message,""+charCur[0]);
//	          else _blablaServ.sendAll(message,""); // sinon on envoi le message à tous
			message = ""; // on vide la chaine de message pour qu'elle soit réutilisée
	      }
	    }
	    catch (Exception e){ }
	    finally // finally se produira le plus souvent lors de la deconnexion du client
	    {
	      try
	      {
	      	// on indique à la console la deconnexion du client
	        System.out.println("Le client no "+_numClient+" s'est deconnecte");
//	        _blablaServ.delClient(_numClient); // on supprime le client de la liste
	        _s.close(); // fermeture du socket si il ne l'a pas déjà été (à cause de l'exception levée plus haut)
	      }
	      catch (IOException e){ }
	    }
	  }
	private void sendImage(byte[] image){
//		_commClientObservable.
	}

	@Override
	public void envoyerCommande(String... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean ping() {
		// TODO Auto-generated method stub
		return null;
	}
}
