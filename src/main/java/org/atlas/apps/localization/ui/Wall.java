package org.atlas.apps.localization.ui;

import java.awt.geom.Line2D;

public class Wall {

	private Line2D line;
	private double height; //Set max wall height
	private double attenuation = 3; //Set default attenuation
	
	public Line2D getLine() {
		return line;
	}
	public void setLine(Line2D line) {
		this.line = line;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}
	
	
}
