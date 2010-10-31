/*
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.openstreetmap.josm.plugins.fixAddresses;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangeCommand;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.BBox;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Way;

public class NodeEntityBase implements INodeEntity, Comparable<INodeEntity> {
	public static final String ANONYMOUS = tr("No name");
	private static List<IAddressEditContainerListener> listeners = new ArrayList<IAddressEditContainerListener>();
	
	protected OsmPrimitive osmObject;
	
	/**
	 * @param osmObject
	 */
	public NodeEntityBase(OsmPrimitive osmObject) {
		super();
		this.osmObject = osmObject;
	}
	
	/**
	 * @param osmObject the osmObject to set
	 */
	protected void setOsmObject(OsmPrimitive osmObject) {
		this.osmObject = osmObject;
	}

	/**
	 * Adds a change listener.
	 * @param listener
	 */
	public static void addChangedListener(IAddressEditContainerListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a change listener.
	 * @param listener
	 */
	public static void removeChangedListener(IAddressEditContainerListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Notifies clients that the address container changed.
	 */
	protected static void fireEntityChanged(INodeEntity entity) {
		for (IAddressEditContainerListener listener : listeners) {
			listener.entityChanged(entity);
		}
	}

	public OsmPrimitive getOsmObject() {
		return osmObject;
	}

	@Override
	public List<INodeEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Gets the name of the street or ANONYMOUS, if street has no name.
	 * @return
	 */
	public String getName() {
		if (TagUtils.hasNameTag(osmObject)) {
			return  TagUtils.getNameValue(osmObject);
		}
		return "";
	}
	
	/* (non-Javadoc)
	 * @see org.openstreetmap.josm.plugins.addressEdit.INodeEntity#hasName()
	 */
	@Override
	public boolean hasName() {
		return TagUtils.hasNameTag(osmObject);
	}
	
	/**
	 * Internal helper method which changes the given property and
	 * puts the appropriate command {@link src.org.openstreetmap.josm.command.Command}
	 * into the undo/redo queue.
	 * @param tag The tag to change.
	 * @param newValue The new value for the tag.
	 */
	protected void setOSMTag(String tag, String newValue) {
		OsmPrimitive oldObject = osmObject;
		OsmPrimitive newObject = null;
			
		// I would appreciate a clone method...
		if (oldObject instanceof Node) {
			newObject = new Node();
		} else if (oldObject instanceof Way) {
			newObject = new Way();
		}
		
		if (newObject != null) {
			newObject.cloneFrom(oldObject);
			newObject.put(tag, newValue);
			Main.main.undoRedo.add( new ChangeCommand(oldObject, newObject));
			fireEntityChanged(this);
		} else {
			throw new RuntimeException("Cannot modify tag for " + osmObject);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (hasName()) {
			return this.getClass().getName() + ": " + getName();
		}
		return this.getClass().getName() + ": " + ANONYMOUS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(INodeEntity o) {
		if (o == null || !(o instanceof NodeEntityBase)) return -1;
		return this.getName().compareTo(o.getName());
	}

	/* (non-Javadoc)
	 * @see org.openstreetmap.josm.plugins.fixAddresses.INodeEntity#getCoor()
	 */
	@Override
	public LatLon getCoor() {
		OsmPrimitive osm = getOsmObject();
		if (osm == null) return null;
		
		if (osm instanceof Node) {
			return ((Node)osm).getCoor();
		// way: return center
		} else if (osm instanceof Way) {
			Way w = (Way) osm;
			BBox bb = w.getBBox();
			return bb.getBottomRight().getCenter(bb.getTopLeft());
		}
		// relations??
		return null;
	}
}