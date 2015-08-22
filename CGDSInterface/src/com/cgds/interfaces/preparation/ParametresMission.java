package com.cgds.interfaces.preparation;

import java.util.ArrayList;
import java.util.List;

import com.cgds.interfaces.detection.DetectionAlerteValue;

public class ParametresMission {

	public List<Coordonnees> parcours;
	public String intitule;
	public String description;
	
	
	
	public ParametresMission(ParametresMission value){
		setIntitule(new String(value.intitule));
		setDescription(new String(value.description));
		setParcours(new ArrayList<>(value.parcours));
	}
	
	public List<Coordonnees> getParcours() {
		return parcours;
	}

	public void setParcours(List<Coordonnees> parcours) {
		this.parcours = parcours;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public class Coordonnees {
		
		public double longitude;
		public double latitude;
		public double altitude;
		
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getAltitude() {
			return altitude;
		}
		public void setAltitude(double altitude) {
			this.altitude = altitude;
		}
		
		
		
	}
	
	
}
