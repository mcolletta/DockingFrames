package bibliothek.gui.dock.action.dropdown;

import bibliothek.gui.dock.Dockable;
import bibliothek.gui.dock.action.DropDownAction;
import bibliothek.gui.dock.action.views.dropdown.DropDownViewItem;

/**
 * An implementation of {@link DropDownFilter}. This filter uses the contents
 * of the selected element whenever possible. The tooltip is uses the action
 * if the selection is not triggerable.
 * @author Benjamin Sigg
 */
public class DefaultDropDownFilter extends AbstractDropDownFilter {
	/**
	 * A factory for this type of filter
	 */
	public static final DropDownFilterFactory FACTORY = new DropDownFilterFactory(){
		public DropDownFilter createView( DropDownAction action, Dockable dockable, DropDownView view ){
			return new DefaultDropDownFilter( action, dockable, view );
		}
	};
	
	/**
	 * Creates a new filter
	 * @param action the action to filter
	 * @param dockable the owner of <code>action</code>
	 * @param view the view where this filter will write into
	 */
	public DefaultDropDownFilter( DropDownAction action, Dockable dockable, DropDownView view ){
		super( action, dockable, view );
	}

	@Override
    public void update( DropDownViewItem selection ){
		updateEnabled( selection );
		updateSelected( selection );
		updateIcon( selection );
		updateDisabledIcon( selection );
		updateText( selection );
		updateTooltip( selection );
	}
	
	/**
	 * Updates the enabled-state of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateEnabled( DropDownViewItem selection ){
		getView().setEnabled( enabled );
	}
	
	/**
	 * Updates the selected-state of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateSelected( DropDownViewItem selection ){
		getView().setSelected( selected );
	}

	/**
	 * Updates the icon of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateIcon( DropDownViewItem selection ){
		if( icon == null )
			getView().setIcon( getAction().getIcon( getDockable() ) );
		else
			getView().setIcon( icon );
	}
	
	/**
	 * Updates the disabled icon of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateDisabledIcon( DropDownViewItem selection ){
		if( disabledIcon == null )
			getView().setDisabledIcon( getAction().getDisabledIcon( getDockable() ) );
		else
			getView().setDisabledIcon( disabledIcon );
	}
	
	/**
	 * Updates the text of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateText( DropDownViewItem selection ){
		if( text == null )
			getView().setText( getAction().getText( getDockable() ) );
		else
			getView().setText( null );
	}
	
	/**
	 * Updates the tooltip of the {@link #getView() view}.
	 * @param selection the selected item, ignored by the default implementation
	 */
	protected void updateTooltip( DropDownViewItem selection ){
		if( selection == null || !selection.isTriggerable( true ) ){
			String tooltip = getAction().getTooltipText( getDockable() );
			if( tooltip == null )
				tooltip = super.tooltip;
			if( tooltip == null )
				tooltip = getAction().getText( getDockable() );
			if( tooltip == null )
				tooltip = super.text;
			getView().setTooltip( tooltip );
		}
		else{
			String tooltip = super.tooltip;
			if( tooltip == null )
				tooltip = getAction().getTooltipText( getDockable() );
			if( tooltip == null )
				tooltip = super.text;
			if( tooltip == null )
				tooltip = getAction().getText( getDockable() );
			getView().setTooltip( tooltip );
		}
	}
}