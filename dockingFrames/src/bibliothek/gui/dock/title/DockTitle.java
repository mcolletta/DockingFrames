/**
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * 
 * Wunderklingerstr. 59
 * 8215 Hallau
 * CH - Switzerland
 */


package bibliothek.gui.dock.title;

import java.awt.Component;

import javax.swing.event.MouseInputListener;

import bibliothek.gui.DockController;
import bibliothek.gui.dock.Dockable;
import bibliothek.gui.dock.action.DockAction;
import bibliothek.gui.dock.event.DockTitleEvent;

/**
 * A component which is shown aside a {@link Dockable}. A DockTitle
 * displays some information about its <code>Dockable</code>, for
 * example a title-text.<br>
 * Every DockTitle is owned by exactly one Dockable. The owner can't
 * be changed.
 * @author Benjamin Sigg
 */
public interface DockTitle {
    /** How to layout a {@link DockTitle} */
    public static enum Orientation{
        /** 
         * Tells a DockTitle that its preferred width should be smaller
         * than its preferred height 
         */
        VERTICAL, 
        /**
         * Tells a DockTitle that its preferred height should be smaller
         * than its preferred width
         */
        HORIZONTAL };
    
    /**
     * Gets a Component which represents the {@link DockTitle}. 
     * The Component is displayed aside the owner of this title.
     * This method must always return the same Component.
     * @return always the same Component
     */
    public Component getComponent();
        
    /**
     * Adds a listener to all {@link Component Components} of this title
     * which are visible and which may be "grabbed" by the mouse.
     * @param listener the new listener
     */
    public void addMouseInputListener( MouseInputListener listener );
    
    /**
     * Removes a listener.
     * @param listener the listener to remove
     */
    public void removeMouseInputListener( MouseInputListener listener );
    
    /**
     * Gets the owner of this title.
     * @return the owner
     */
    public Dockable getDockable();
    
    /**
     * Sets the orientation of this title. The layout of this title
     * should be influenced by the orientation.
     * @param orientation the orientation
     */
    public void setOrientation( Orientation orientation );
    
    /**
     * Gets the orientation of this title.
     * @return the orientation
     * @see #setOrientation(bibliothek.gui.dock.title.DockTitle.Orientation)
     */
    public Orientation getOrientation();
    
    /**
     * Called if a property (of this title, of the owner or anything else)
     * has changed. The title might change some of its own properties. 
     * @param event information about the current state
     */
    public void changed( DockTitleEvent event );
    
    /**
     * Tells whether this title is selected (active) or not. The title
     * knows its state through the event-object of the method
     * {@link #changed(DockTitleEvent) changed}.
     * @return the selection state
     */
    public boolean isActive();
    
    /**
     * Called before the title is displayed. The method should connect the
     * title with other objects, like its owner. If the title wants to 
     * show some {@link DockAction DockActions} (see the method
     * {@link DockController#listOffers(Dockable)}), then this method
     * should {@link DockAction#bind(Dockable) bind} them too.<br>
     * Clients should never call this method directly, they should call
     * {@link Dockable}.{@link Dockable#bind(DockTitle)}. The {@link DockController}
     * will call the bind-method, as soon as the Dockable knows the controller.
     */
    public void bind();
    
    /**
     * The reverse of {@link #bind()}. The title should remove any connections
     * to other objects and {@link DockAction#unbind(Dockable) unbind} its
     * DockActions.<br>
     * Clients should never call this method directly, they should call
     * {@link Dockable}.{@link Dockable#unbind(DockTitle)}. The {@link DockController}
     * will call the unbind-method before the Dockable looses the controller.
     */
    public void unbind();
    
    /**
     * Gets the version which was used to create this title. If this title
     * was not created through the regular methods, then this method is allowed
     * to return <code>null</code>. However, some features will only work correctly
     * if this value is not <code>null</code>.
     * @return the title-version, might be <code>null</code>
     */
    public DockTitleVersion getOrigin();
}