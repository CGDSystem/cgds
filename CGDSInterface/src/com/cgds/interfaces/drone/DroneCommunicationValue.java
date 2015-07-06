package com.cgds.interfaces.drone;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class DroneCommunicationValue implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String id = null;
	private List<String> infos = null;
	private byte[] image = null;;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getInfos() {
		return infos;
	}

	public void setInfos(List<String> infos) {
		this.infos = infos;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}