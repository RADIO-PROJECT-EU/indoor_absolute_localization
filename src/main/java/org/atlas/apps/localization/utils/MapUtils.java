package org.atlas.apps.localization.utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.atlas.apps.localization.algorithms.GrahamScan;
import org.atlas.apps.localization.beacons.Beacon;
import org.atlas.apps.localization.domain.Position;
import org.atlas.apps.localization.domain.Zone;
import org.atlas.apps.localization.estimators.DistanceDecision;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapUtils {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final int CIRCLE_TOTAL_POINTS = 25;
	
	/**
	 * Fix the Subset Decision Tree ( Check Data Mining Decision Tree - Subset )
	 * @return	
	 */
	public static List<DistanceDecision> loadDistanceDecisionTree(){
		try {
			List<DistanceDecision> tree = mapper.readValue(new File("storage/maps/rssi-to-distance-maps.json"), new TypeReference<List<DistanceDecision>>() {});
			return tree;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Load Beacons.
	 * @return	
	 */
	public static List<Beacon> loadBeacons(){
		try {
			List<Beacon> beacons = mapper.readValue(new File("config/beacons.json"), new TypeReference<List<Beacon>>() {});
			return beacons;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Load Map Zones
	 */
	public static List<Zone> loadZones(){
		try {
			List<Zone> zones = mapper.readValue(new File("config/zones.json"), new TypeReference<List<Zone>>() {});
			return zones;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Checking if a given position is inside a polygon
	 * @param polygon
	 * @param position
	 * @return
	 */
	public static Position isPositionInsideRegion(List<Position> polygon, Position position){
		int arraySize = polygon.size()+1;//+1 for add the Position object.
		int[] xs = new int[arraySize];
		int[] ys = new int[arraySize];
		int cnt=0;
		for( Position tmpPos : polygon ){
			xs[cnt] = (int) tmpPos.getX();
			ys[cnt] = (int) tmpPos.getY();
			cnt++;
		}
		xs[cnt] = (int) position.getX();
		ys[cnt] = (int) position.getY();
		List<Point> convexHull = GrahamScan.getConvexHull(xs, ys);
		for(Point p : convexHull) {
		    if( (p.getX() == (int)position.getX()) && (p.getY() == (int)position.getY()) ) return null;
		}
		return position;
	}
	
	
	public static List<Point2D> createCircleOnPosition(Position position, double radius){
		List<Point2D> circle = new ArrayList<Point2D>();
		double slice = 2 * Math.PI / CIRCLE_TOTAL_POINTS;
		for (int i = 0; i < CIRCLE_TOTAL_POINTS; i++){
	        double angle = slice * i;
	        double newX = (position.getX() + radius * Math.cos(angle));
	        double newY = (position.getY() + radius * Math.sin(angle));
	        circle.add(new Point2D.Double(newX, newY));
	    }
		return circle;
	}
	
}

