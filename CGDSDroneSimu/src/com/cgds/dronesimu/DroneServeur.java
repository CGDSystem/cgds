package com.cgds.dronesimu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.cgds.interfaces.Constants;
import com.cgds.interfaces.communication.CommManagerInt;
import com.cgds.interfaces.drone.DroneInt;

public class DroneServeur {

	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException, NotBoundException {
		
		Drone drone1 = new Drone(Constants.DRONE1_RMI_ID);
		Drone drone2 = new Drone(Constants.DRONE2_RMI_ID);

		drone1.setBatterie(0.75d);
		drone1.setDirection("N-NE");
		drone1.setGps("123 456");
		drone1.setIntensiteSignal(0.25d);
		drone1.setMission("Test");
		drone1.setNom("Drone1RMI");
		
		drone2.setBatterie(0.4d);
		drone2.setDirection("SE");
		drone2.setGps("321 654");
		drone2.setIntensiteSignal(0.8d);
		drone2.setMission("Test");
		drone1.setNom("Drone2RMI");

		// Registry registry = LocateRegistry
		// .createRegistry(Constants.DRONE_RMI_PORT);
		//
		//
		// Remote remoteDrone1 = UnicastRemoteObject.exportObject(drone1,0);
		// registry.bind(Constants.DRONE1_RMI_ID, remoteDrone1);
		//
		// Remote remoteDrone2 = UnicastRemoteObject.exportObject(drone2,0);
		// registry.bind(Constants.DRONE2_RMI_ID, remoteDrone2);
		//
		Registry registry2 = LocateRegistry.getRegistry("localhost",
				Constants.COMM_RMI_PORT);
		CommManagerInt commInterface = (CommManagerInt) registry2
				.lookup(Constants.COMM_RMI_ID);

		// drone1.addObserver(droneInterface);
		// drone2.addObserver(droneInterface);
		Remote remoteDrone1 = UnicastRemoteObject.exportObject(drone1, 0);
		Remote remoteDrone2 = UnicastRemoteObject.exportObject(drone2, 0);

		commInterface.ajouterDrone((DroneInt) remoteDrone1);
		commInterface.ajouterDrone((DroneInt) remoteDrone2);
		// droneInterface.addDrone(drone2);
		int i = 0;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ignores
			}
			double alt1 = (int) (Math.random() * 10 + 100);
			double alt2 = (int) (Math.random() * 10 + 200);

			drone1.setAltitude(alt1);
			drone2.setAltitude(alt2);
			drone1.setImage(genererImage(i, Color.RED));
			drone2.setImage(genererImage(i, Color.GREEN));
			drone1.notifier();
			drone2.notifier();
			i++;
		}
	}

	private synchronized static byte[] genererImage(int i, Color cibleCouleur) {

		int width = 400, height = 300;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.BLUE);
		g.clearRect(0, 0, width, height);
		g.setColor(cibleCouleur);
		g.fillOval((i * 10) % 400, 150, 10, 10);
		g.dispose();

		// Iterator writers = ImageIO.getImageWritersByFormatName("png");
		// ImageWriter writer = (ImageWriter) writers.next();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] imageInByte = null;
		try {
			Object obj = baos;
			ImageOutputStream ios = ImageIO.createImageOutputStream(obj);
			ImageIO.write(image, "png", ios);
			imageInByte = baos.toByteArray();
			// ImageOutputStream ios = ImageIO.createImageOutputStream(obj);
			// Iterator<?> writters =
			// ImageIO.getImageWritersByFormatName("bmp");
			// ImageWriter writer = (ImageWriter) writters.next();
			// Object obj = baos;
			// writer.setOutput(ios);
			// writer.write(image);
			// imageInByte = baos.toByteArray();
			//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageInByte;

	}

}
