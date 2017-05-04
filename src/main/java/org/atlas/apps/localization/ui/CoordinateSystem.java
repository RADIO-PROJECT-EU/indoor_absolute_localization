package org.atlas.apps.localization.ui;

import org.atlas.apps.localization.domain.Position;

/**
 * Main class for transform from one coordinate system to another.
 * @author kasnot
 */
public class CoordinateSystem {
	
	//1cm on the localization app is 0.4 to Unity
	private final double UNITY_TRANSFORM_VARIABLE = 0.4;
	
	/**
	 * TODO
	 * Transform the system(Software Coordination System) position to the real world position
	 * @param position
	 * @return - The transformed position
	 */
	public Position systemToWorldPosition(Position position){
		return null;
	}

	/**
	 * TODO
	 * Transform the real world position to the system position(Software Coordination System)
	 * @param position
	 * @return - The transformed position
	 */
	public Position worldToSystemPosition(Position position){
		return null;
	}
	
	/**
	 * TODO
	 * Transform the real world position to the Unity position
	 * @param position
	 * @return - The transformed position
	 */
	public Position worldToUnityPosition(Position position){
		return null;
	}
	
	/**
	 * TODO
	 * Transform the Unity position to the real world position
	 * @param position
	 * @return - The transformed position
	 */
	public Position unityToWorldPosition(Position position){
		return null;
	}
	
	/**
	 * TODO
	 * Transform the system position to the Unity position
	 * @param position
	 * @return - The transformed position
	 */
	public Position systemToUnityPosition(Position position){
		return null;
	}
	
	/**
	 * TODO
	 * Transform the Unity position to the system position
	 * @param position
	 * @return - The transformed position
	 */
	public Position unityToSystemPosition(Position position){
		return null;
	}
	
}
