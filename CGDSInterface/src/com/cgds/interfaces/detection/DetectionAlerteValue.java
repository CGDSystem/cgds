package com.cgds.interfaces.detection;

import java.io.Serializable;

public class DetectionAlerteValue implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private double latitude;
		private double longitude;
	
		public DetectionAlerteValue(){
			
		}
		//Copy Constructor
		public DetectionAlerteValue(DetectionAlerteValue value){
			setLatitude(value.latitude);
			setLongitude(value.longitude);	
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		
		
	
}
