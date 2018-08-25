import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import myComponents.hdnComponents.HDNumberBox;

public class StyledNumberBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public StyledNumberBox() {
		setBorder(new LineBorder(Color.RED));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
		gridBagLayout.columnWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);

		JLabel lblA = new JLabel("A");
		lblA.setFont(new Font("Courier New", Font.BOLD, 14));
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 0;
		gbc_lblA.gridy = 0;
		add(lblA, gbc_lblA);

		HDNumberBox hdn1 = new HDNumberBox();
		hdn1.setMaximumSize(new Dimension(77, 35));
		hdn1.setPreferredSize(new Dimension(77, 35));
		hdn1.setHexDisplay("%02X");
		hdn1.setMaxValue(255);
		hdn1.setMinValue(0);
		GridBagConstraints gbc_hdn1 = new GridBagConstraints();
		gbc_hdn1.insets = new Insets(0, 0, 5, 5);
		gbc_hdn1.gridx = 0;
		gbc_hdn1.gridy = 1;
		add(hdn1, gbc_hdn1);
		GridBagLayout gbl_hdn1 = new GridBagLayout();
		gbl_hdn1.columnWidths = new int[] { 0 };
		gbl_hdn1.rowHeights = new int[] { 0 };
		gbl_hdn1.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_hdn1.rowWeights = new double[] { Double.MIN_VALUE };
		hdn1.setLayout(gbl_hdn1);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
	}

}
