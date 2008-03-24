/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */
package bibliothek.gui.dock.frontend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import bibliothek.gui.DockFrontend;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.DockFactory;
import bibliothek.gui.dock.layout.BackupFactoryData;
import bibliothek.util.xml.XElement;

/**
 * A {@link DockFactory} that wrapps around another factory and adds all elements
 * that are read through {@link #layout(BackupFactoryData)} or {@link #layout(BackupFactoryData, Map)}
 * to a {@link DockFrontend}.
 * @author Benjamin Sigg
 * @param <D> the kind of elements read by this factory
 * @param <L> the kind of data the wrapped factory needs
 */
public class RegisteringDockFactory<D extends Dockable, L> implements DockFactory<D, BackupFactoryData<L>> {
    /** the {@link DockFrontend} to which this factory will add new elements */
    private DockFrontend frontend;
    /** delegate used to read new elements */
    private DockFactory<D, L> factory;
    
    /**
     * Creates a new factory
     * @param frontend the frontend to which this factory will add new elements
     * @param factory delegated used to read and create new elements
     */
    public RegisteringDockFactory( DockFrontend frontend, DockFactory<D, L> factory ){
        this.frontend = frontend;
        this.factory = factory;
    }

    public String getID() {
        return factory.getID();
    }

    public BackupFactoryData<L> getLayout( D element, Map<Dockable, Integer> children ) {
        return new BackupFactoryData<L>( null, factory.getLayout( element, children ));
    }

    public D layout( BackupFactoryData<L> layout, Map<Integer, Dockable> children ) {
        D element = factory.layout( layout.getData() );
        if( element != null ){
            if( frontend.getDockable( layout.getIdentifier() ) == null ){
                frontend.add( element, layout.getIdentifier() );
            }
        }
        return element;
    }

    public D layout( BackupFactoryData<L> layout ) {
        D element = factory.layout( layout.getData() );
        if( element != null ){
            if( frontend.getDockable( layout.getIdentifier() ) == null ){
                frontend.add( element, layout.getIdentifier() );
            }
        }
        return element;
    }

    public BackupFactoryData<L> read( DataInputStream in ) throws IOException {
        return new BackupFactoryData<L>( null, factory.read( in ));
    }

    public BackupFactoryData<L> read( XElement element ) {
        return new BackupFactoryData<L>( null, factory.read( element ));
    }

    public void setLayout( D element, BackupFactoryData<L> layout, Map<Integer, Dockable> children ) {
        factory.setLayout( element, layout.getData(), children );
    }

    public void setLayout( D element, BackupFactoryData<L> layout ) {
        factory.setLayout( element, layout.getData() );
    }

    public void write( BackupFactoryData<L> layout, DataOutputStream out ) throws IOException {
        factory.write( layout.getData(), out );
    }

    public void write( BackupFactoryData<L> layout, XElement element ) {
        factory.write( layout.getData(), element );
    }
}