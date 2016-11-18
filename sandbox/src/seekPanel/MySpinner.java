package seekPanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import seekPanel.XSpinnerXX.NumberEditorFormatter;

public class MySpinner extends JComponent {

	private SpinnerModel model;
	private JComponent editor;
	private EventListenerList listenerList;

	public MySpinner() {
		this(new SpinnerNumberModel());
	}// Constructor

	public MySpinner(SpinnerModel model) {
		this.listenerList = new EventListenerList();
		this.model = model;
		this.editor = createEditor(model);
		model.addChangeListener(new ModelListener());
		updateUI();
	}// Constructor

	protected JComponent createEditor(SpinnerModel model) {
		return new NumberEditor(this);
		// if (model instanceof SpinnerDateModel)
		// return new DateEditor(this);
		// else if (model instanceof SpinnerNumberModel)
		// return new NumberEditor(this);
		// else if (model instanceof SpinnerListModel)
		// return new ListEditor(this);
		// else
		// return new DefaultEditor(this);
		// }//if
	}// createEditor

	public Object getValue() {
		return model.getValue();
	}// getValue

	public SpinnerModel getModel() {
		return model;
	}// getModel

	public void addChangeListener(ChangeListener listener) {
		listenerList.add(ChangeListener.class, listener);
	}// addChangeListener

	public void removeChangeListener(ChangeListener listener) {
		listenerList.remove(ChangeListener.class, listener);
	}// removeChangeListener

	protected void fireStateChanged() {
		ChangeEvent evt = new ChangeEvent(this);
		ChangeListener[] listeners = getChangeListeners();

		for (int i = 0; i < listeners.length; ++i)
			listeners[i].stateChanged(evt);
	}// fireStateChanged

	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) listenerList.getListeners(ChangeListener.class);
	}// getChangeListeners

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public static class NumberEditor extends DefaultEditor {

		private static final long serialVersionUID = 3791956183098282942L;

		public NumberEditor(MySpinner spinner) {
			super(spinner);
			NumberEditorFormatter nef = new NumberEditorFormatter();
			nef.setMinimum(getModel().getMinimum());
			nef.setMaximum(getModel().getMaximum());
			ftf.setFormatterFactory(new DefaultFormatterFactory(nef));
		}

		/**
		 * Creates a new <code>NumberEditor</code> object.
		 *
		 * @param spinner
		 *            the spinner.
		 * @param decimalFormatPattern
		 *            the number format pattern.
		 */
		public NumberEditor(MySpinner spinner, String decimalFormatPattern) {
			super(spinner);
			NumberEditorFormatter nef = new NumberEditorFormatter(decimalFormatPattern);
			nef.setMinimum(getModel().getMinimum());
			nef.setMaximum(getModel().getMaximum());
			ftf.setFormatterFactory(new DefaultFormatterFactory(nef));
		}

		/**
		 * Returns the format used by the text field.
		 *
		 * @return The format used by the text field.
		 */
		public DecimalFormat getFormat() {
			NumberFormatter formatter = (NumberFormatter) ftf.getFormatter();
			return (DecimalFormat) formatter.getFormat();
		}

		/**
		 * Returns the model used by the editor's {@link MySpinner} component,
		 * cast to a {@link SpinnerNumberModel}.
		 *
		 * @return The model.
		 */
		public SpinnerNumberModel getModel() {
			return (SpinnerNumberModel) getSpinner().getModel();
		}
	}

	// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	public static class DefaultEditor extends JPanel implements ChangeListener, PropertyChangeListener, LayoutManager {

		private MySpinner spinner;

		/** The JFormattedTextField that backs the editor. */
		JFormattedTextField ftf;

		/**
		 * For compatability with Sun's JDK 1.4.2 rev. 5
		 */
		private static final long serialVersionUID = -5317788736173368172L;

		/**
		 * Creates a new <code>DefaultEditor</code> object. The editor is
		 * registered with the spinner as a {@link ChangeListener} here.
		 *
		 * @param spinner
		 *            the <code>MySpinner</code> associated with this editor
		 */
		public DefaultEditor(MySpinner spinner) {
			super();
			// setLayout(this);//TODO ********
			this.spinner = spinner;
			ftf = new JFormattedTextField();
			add(ftf);
			ftf.setValue(spinner.getValue());
			ftf.addPropertyChangeListener(this);
			if (getComponentOrientation().isLeftToRight())
				ftf.setHorizontalAlignment(JTextField.RIGHT);
			else
				ftf.setHorizontalAlignment(JTextField.LEFT);
			spinner.addChangeListener(this);
		}

		/**
		 * Returns the <code>MySpinner</code> component that the editor is
		 * assigned to.
		 *
		 * @return The spinner that the editor is assigned to.
		 */
		public MySpinner getSpinner() {
			return spinner;
		}

		/**
		 * DOCUMENT ME!
		 */
		public void commitEdit() throws ParseException {
			// TODO: Implement this properly.
		}

		/**
		 * Removes the editor from the {@link ChangeListener} list maintained by
		 * the specified <code>spinner</code>.
		 *
		 * @param spinner
		 *            the spinner (<code>null</code> not permitted).
		 */
		public void dismiss(MySpinner spinner) {
			spinner.removeChangeListener(this);
		}

		/**
		 * Returns the text field used to display and edit the current value in
		 * the spinner.
		 *
		 * @return The text field.
		 */
		public JFormattedTextField getTextField() {
			return ftf;
		}

		/**
		 * Sets the bounds for the child components in this container. In this
		 * case, the text field is the only component to be laid out.
		 *
		 * @param parent
		 *            the parent container.
		 */
		public void layoutContainer(Container parent) {
			Insets insets = getInsets();
			Dimension size = getSize();
			ftf.setBounds(insets.left, insets.top, size.width - insets.left - insets.right,
					size.height - insets.top - insets.bottom);
		}

		/**
		 * Calculates the minimum size for this component. In this case, the
		 * text field is the only subcomponent, so the return value is the
		 * minimum size of the text field plus the insets of this component.
		 *
		 * @param parent
		 *            the parent container.
		 *
		 * @return The minimum size.
		 */
		public Dimension minimumLayoutSize(Container parent) {
			Insets insets = getInsets();
			Dimension minSize = ftf.getMinimumSize();
			return new Dimension(minSize.width + insets.left + insets.right,
					minSize.height + insets.top + insets.bottom);
		}

		/**
		 * Calculates the preferred size for this component. In this case, the
		 * text field is the only subcomponent, so the return value is the
		 * preferred size of the text field plus the insets of this component.
		 *
		 * @param parent
		 *            the parent container.
		 *
		 * @return The preferred size.
		 */
		public Dimension preferredLayoutSize(Container parent) {
			Insets insets = getInsets();
			Dimension prefSize = ftf.getPreferredSize();
			return new Dimension(prefSize.width + insets.left + insets.right,
					prefSize.height + insets.top + insets.bottom);
		}

		/**
		 * Receives notification of property changes. If the text field's
		 * 'value' property changes, the spinner's model is updated accordingly.
		 *
		 * @param event
		 *            the event.
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getSource() == ftf) {
				if (event.getPropertyName().equals("value"))
					spinner.getModel().setValue(event.getNewValue());
			}
		}

		/**
		 * Receives notification of changes in the state of the
		 * {@link MySpinner} that the editor belongs to - the content of the
		 * text field is updated accordingly.
		 *
		 * @param event
		 *            the change event.
		 */
		public void stateChanged(ChangeEvent event) {
			ftf.setValue(spinner.getValue());
		}

		/**
		 * This method does nothing. It is required by the {@link LayoutManager}
		 * interface, but since this component has a single child, there is no
		 * need to use this method.
		 *
		 * @param child
		 *            the child component to remove.
		 */
		public void removeLayoutComponent(Component child) {
			// Nothing to do here.
		}

		/**
		 * This method does nothing. It is required by the {@link LayoutManager}
		 * interface, but since this component has a single child, there is no
		 * need to use this method.
		 *
		 * @param name
		 *            the name.
		 * @param child
		 *            the child component to add.
		 */
		public void addLayoutComponent(String name, Component child) {
			// Nothing to do here.
		}
	}

	// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
	class ModelListener implements ChangeListener {

		public ModelListener() {
			// nothing to do here
		}// Constructor

		public void stateChanged(ChangeEvent event) {
			fireStateChanged();
		}
	}// class ModelListener
		// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

}// class MySpinner
