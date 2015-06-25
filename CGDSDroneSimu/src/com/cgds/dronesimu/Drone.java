package com.cgds.dronesimu;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.cgds.interfaces.communication.CommDroneInt;
import com.cgds.interfaces.drone.DroneCommunicationValue;
import com.cgds.interfaces.drone.DroneInt;

public class Drone extends Observable implements DroneInt, Serializable {

	private static final long serialVersionUID = 1L;

	private String nom = null;
	private Double batterie;
	private Double altitude;
	private String direction;
	private String gps;
	private Double intensiteSignal;
	private String mission;

	private byte[] image;

	protected Drone(String nom) throws RemoteException {
		super();
		this.setNom(nom);
	}

	public String getNom() throws RemoteException {
		return nom;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	public Double getBatterie() {
		return batterie;
	}

	public void setBatterie(Double batterie) {
		this.batterie = batterie;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String drirection) {
		this.direction = drirection;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public Double getIntensiteSignal() {
		return intensiteSignal;
	}

	public void setIntensiteSignal(Double intensiteSignal) {
		this.intensiteSignal = intensiteSignal;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void addObserver(CommDroneInt observer) throws RemoteException {
		WrappedObserver obsInfo = new WrappedObserver(observer);
		addObserver(obsInfo);
		System.out.println("Added observer:" + observer);
	}

	public void notifier() throws RemoteException {
		DroneCommunicationValue message = new DroneCommunicationValue();
		message.setId(getNom());
		message.setInfos(getInfos());
		message.setImage(getImage());
		setChanged();
		notifyObservers(message);
	}

	private List<String> getInfos() {
		List<String> listeInfos = new ArrayList<String>();
		listeInfos.add(getAltitude().toString());
		listeInfos.add(getBatterie().toString());
		listeInfos.add(getDirection());
		listeInfos.add(getGps());
		listeInfos.add(getIntensiteSignal().toString());
		listeInfos.add(getMission());
		return listeInfos;
	}

	@Override
	public void recevoirCommande(String... args) throws RemoteException {
		System.out.println(this.getNom() + " - Commande recu : ");
		for (String string : args) {
			System.out.println(string);
		}

	}

	public Boolean ping() {
		return true;
	}

	private class WrappedObserver implements Observer, Serializable {

		private static final long serialVersionUID = 1L;

		private CommDroneInt ro = null;

		public WrappedObserver(CommDroneInt ro) {
			this.ro = ro;
		}

		@Override
		public void update(Observable o, Object arg) {
			try {
				ro.droneUpdate(((Drone) o).getNom(),
						(DroneCommunicationValue) arg);

			} catch (RemoteException e) {
				System.out
						.println("Remote exception removing observer:" + this);
				o.deleteObserver(this);
			}
		}

	}

}
