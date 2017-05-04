package org.atlas.apps.localization.filters;

import org.atlas.apps.localization.domain.Position;

public abstract class Filter {

	/**
	 * Instantiates a new filter.
	 */
	public Filter() { }
	
	/**
	 * Abstract method applyFilter. This method can be used to apply a filter on a point to return a new point.
	 *
	 * @param point the point to apply the filter for
	 * @return the new point
	 */
	public abstract Position applyFilter(Position point);
}
