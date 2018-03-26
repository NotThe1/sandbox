package dualListBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class DualListBox extends JPanel {
	private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
	private static final String BTN_ADD = "btnAdd";
	private static final String BTN_ADD_LABEL = "Add >>";
	private static final String BTN_REMOVE = "btnRemove";
	private static final String BTN_REMOVE_LABEL = "<< Remove";
	private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Available Choices";
	private static final String DEFAULT_DEST_CHOICE_LABEL = "Your Choices";

	private JLabel lblSource;
	private JList sourceList;
	private SortedListModel sourceListModel;

	private JLabel lblDest;
	private JList destList;
	private SortedListModel destListModel;

	private JButton btnAdd;
	private JButton btnRemove;

	public DualListBox() {
		initScreen();
	}// Constructor

	public String getSourceChoicesTitle() {
		return lblSource.getText();
	}// getSourceChoicesTitle

	public void setSourceChoicesTitle(String title) {
		lblSource.setText(title);
	}// setSourceChoicesTitle

	public String getDestinationChoicesTitle() {
		return lblDest.getText();
	}// getDestinationChoicesTitle

	public void setDestinationChoicesTitle(String title) {
		lblDest.setText(title);
	}// setDestinationChoicesTitle

	public void clearSourceListModel() {
		sourceListModel.clear();
	}// clearSourceListModel

	public void clearDestinationListModel() {
		destListModel.clear();
	}// clearDestinationListModel

	public void addSourceElements(ListModel newValues) {
		fillListModel(sourceListModel, newValues);
	}// addSourceElements

	public void setSourceElements(ListModel newValues) {
		clearSourceListModel();
		addSourceElements(newValues);
	}// setSourceElements

	public void addDestinationElements(ListModel newValues) {
		fillListModel(destListModel, newValues);
	}// addSourceElements

	private void fillListModel(SortedListModel model, ListModel newValues) {
		int size = newValues.getSize();
		for (int i = 0; i < size; i++) {
			model.add((String) newValues.getElementAt(i));
		} // for
	}// fillListModel

	private void fillListModel(SortedListModel model, Object[] newValues) {
		model.addAll(newValues);
	}// fillListModel

	public void addSourceElements(Object[] newValues) {
		fillListModel(sourceListModel, newValues);
	}// addSourceElements

	public void setSourceElements(Object[] newValues) {
		clearSourceListModel();
		addSourceElements(newValues);
	}// setSourceElements

	public void addDestinationElements(Object[] newValues) {
		fillListModel(destListModel, newValues);
	}// addDestinationElements

	public Iterator sourceIterator() {
		return sourceListModel.iterator();
	}// sourceIterator

	public Iterator destinationIterator() {
		return destListModel.iterator();
	}// sourceIterator

	public void setSourceCellRenderer(ListCellRenderer renderer) {
		sourceList.setCellRenderer(renderer);
	}// setSourceCellRenderer

	public ListCellRenderer getSourceCellRenderer() {
		return sourceList.getCellRenderer();
	}// getSourceCellRenderer

	public void setDestinationCellRenderer(ListCellRenderer renderer) {
		destList.setCellRenderer(renderer);
	}// setDestinationCellRenderer

	public ListCellRenderer getDestinationCellRenderer() {
		return destList.getCellRenderer();
	}// getDestinationCellRenderer

	public void setVisibleRowCount(int value) {
		sourceList.setVisibleRowCount(value);
		destList.setVisibleRowCount(value);
	}// setVisibleRowCount

	public int getVisibleRowCount() {
		return sourceList.getVisibleRowCount();
	}// getVisibleRowCount

	public void setSelectionBackground(Color color) {
		sourceList.setSelectionBackground(color);
		destList.setSelectionBackground(color);
	}// setSelectionBackground

	public Color getSelectionBackgroundColor() {
		return sourceList.getSelectionBackground();
	}// getSelectionBackgroundColor

	public void setSelectionForeground(Color color) {
		sourceList.setSelectionForeground(color);
		destList.setSelectionForeground(color);
	}// setSelectionForeground

	public Color getSelectionForegroundColor() {
		return sourceList.getSelectionForeground();
	}// getSelectionForegroundColor

	private void clearSourceSelected() {
		Object[] selected = sourceList.getSelectedValuesList().toArray();
		for (Object item : selected) {
			sourceListModel.removeElement(item);
		} // for
		sourceList.getSelectionModel().clearSelection();
	}// clearSourceSelected

	private void clearDestinationSelected() {
		Object[] selected = destList.getSelectedValuesList().toArray();
		for (Object item : selected) {
			destListModel.removeElement(item);
		} // for
		destList.getSelectionModel().clearSelection();
	}// clearSourceSelected

	/////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Dual List Box Sample ");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				DualListBox dual = new DualListBox();
				dual.addSourceElements(new String[] { "One", "Two", "Three" });
				dual.addSourceElements(new String[] { "Four", "Five", "SIx" });
				dual.addSourceElements(new String[] { "Seven", "Eight", "Nine" });
				dual.addSourceElements(new String[] { "Ten", "Eleven", "Twelve" });
				dual.addSourceElements(new String[] { "Thirteen", "Fourteen", "Fifteen" });
				dual.addSourceElements(new String[] { "Sixteen", "SevenTeen", "Eighten" });
				dual.addSourceElements(new String[] { "Nineteen", "Twenty", "Thirty" });

				frame.add(dual, BorderLayout.CENTER);
				frame.setSize(500, 400);
				frame.setVisible(true);
			}// run
		};// Runnable runner
		EventQueue.invokeLater(runner);
	}// main

	///////////////////////////////////////////////////////////////////////////////////////

	private void initScreen() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridBagLayout());
		lblSource = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
		sourceListModel = new SortedListModel();
		sourceList = new JList(sourceListModel);
		add(lblSource, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));
		btnAdd = new JButton(BTN_ADD_LABEL);
		add(btnAdd, new GridBagConstraints(1, 2, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		btnAdd.addActionListener(new AddListener());
		btnRemove = new JButton(BTN_REMOVE_LABEL);
		add(btnRemove, new GridBagConstraints(1, 4, 1, 2, 0, .25, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 5, 0, 5), 0, 0));
		btnRemove.addActionListener(new RemoveListener());
		lblDest = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
		destListModel = new SortedListModel();
		destList = new JList(destListModel);
		add(lblDest, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				EMPTY_INSETS, 0, 0));
		add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 5, .5, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));
	}// initScreen

	private class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// String[] selected = (String[]) sourceList.getSelectedValuesList().toArray();
			Object[] selected = sourceList.getSelectedValuesList().toArray();
			addDestinationElements(selected);
			clearSourceSelected();
		}// actionPerformed
	}// class AddListener

	private class RemoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			Object[] selected = destList.getSelectedValuesList().toArray();
			addSourceElements(selected);
			clearDestinationSelected();
		}// actionPerformed
	}// class RemoveListener

}// class DualListBox
