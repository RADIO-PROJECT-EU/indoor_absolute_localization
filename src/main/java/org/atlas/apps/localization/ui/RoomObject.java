package org.atlas.apps.localization.ui;

import org.atlas.apps.localization.domain.Position;

public class RoomObject {

	private String identifier;
	private RoomObjectType type;
	private Position position;
	private double height;
	
	public RoomObject(){}
	
	public RoomObject(String identifier, RoomObjectType type, Position position, double height){
		this.identifier = identifier;
		this.type = type;
		this.position = position;
		this.height = height;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public RoomObjectType getType() {
		return type;
	}
	public void setType(RoomObjectType type) {
		this.type = type;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	
}
