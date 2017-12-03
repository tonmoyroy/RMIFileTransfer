package com.Common;

import java.io.Serializable;

public class ServerFiles implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3374543383694774796L;
	private double userid;
	private String filename;
	private String filepath;

	public double getUserid() {
		return userid;
	}

	public void setUserid(double userid) {
		this.userid = userid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public ServerFiles(double userid, String filename, String filepath) {
		this.userid = userid;
		this.filename = filename;
		this.filepath = filepath;
	}
}
