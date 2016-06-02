package sandbox;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.prefs.Preferences;
import java.awt.GridBagLayout;

import javax.swing.JMenuBar;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TableMaker {

	private JFrame frmOpcodeTableMaker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableMaker window = new TableMaker();
					window.frmOpcodeTableMaker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void argumentTypeChanged(ArgumentType argumentType) {
		switch (argumentType) {
		case NONE:
		case ADDRESS:
		case D8: // No panels
		case D16:
			setRegisterPanels(0);
			break;
		case R8: // byte register list
			cbRegister1.setModel(byteRegisterModel);
			setRegisterPanels(1);
			break;
		case R16: // word register list
			cbRegister1.setModel(wordRegisterModel);
			setRegisterPanels(1);
			break;
		case R8D8: // byte register
			cbRegister1.setModel(byteRegisterModel);
			setRegisterPanels(1);
			break;
		case R8R8: // byte register list X 2
			cbRegister1.setModel(byteRegisterModel);
			cbRegister2.setModel(byteRegisterModel);
			setRegisterPanels(2);
			break;
		case R16D16: // word register list
			cbRegister1.setModel(wordRegisterModel);
			setRegisterPanels(1);
			break;
		default:
			setRegisterPanels(0);
		}
	}// argumentTypeChanged

	private void setRegisterPanels(int numberOfRegisters) {
		switch (numberOfRegisters) {
		case 0:
			panelRegisters.setVisible(false);
			panelRegisterOne.setVisible(false);
			panelRegisterTwo.setVisible(false);
			break;
		case 1:
			panelRegisters.setVisible(true);
			panelRegisterOne.setVisible(true);
			panelRegisterTwo.setVisible(false);
			break;
		case 2:
			panelRegisters.setVisible(true);
			panelRegisterOne.setVisible(true);
			panelRegisterTwo.setVisible(true);
			break;
		default:
			panelRegisters.setVisible(false);
			panelRegisterOne.setVisible(false);
			panelRegisterTwo.setVisible(false);
		}// switch
	}// setRegisterPanels

	// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(TableMaker.class);
		Dimension dim = frmOpcodeTableMaker.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmOpcodeTableMaker.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}

	@SuppressWarnings("unchecked")
	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(TableMaker.class);
		frmOpcodeTableMaker.setSize(568, 608);
		frmOpcodeTableMaker.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;

		setRegisterPanels(0);
		byteRegisterModel = new DefaultComboBoxModel(byteRegisters);
		wordRegisterModel = new DefaultComboBoxModel(wordRegisters);

	}

	/**
	 * Create the application.
	 */
	public TableMaker() {
		initialize();
		appInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOpcodeTableMaker = new JFrame();
		frmOpcodeTableMaker.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
		frmOpcodeTableMaker.setTitle("Opcode Table Maker");
		frmOpcodeTableMaker.setBounds(100, 100, 450, 300);
		frmOpcodeTableMaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmOpcodeTableMaker.getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frmOpcodeTableMaker.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Opcode :");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		frmOpcodeTableMaker.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		HexSpinner hsOpcode = new HexSpinner();
		hsOpcode.setMinimumSize(new Dimension(50, 20));
		hsOpcode.setPreferredSize(new Dimension(50, 20));
		// hexSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		GridBagConstraints gbc_hsOpcode = new GridBagConstraints();
		gbc_hsOpcode.insets = new Insets(0, 0, 5, 0);
		gbc_hsOpcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_hsOpcode.gridx = 1;
		gbc_hsOpcode.gridy = 2;
		frmOpcodeTableMaker.getContentPane().add(hsOpcode, gbc_hsOpcode);

		JLabel lblOpcodeSize = new JLabel("OpCode Size :");
		GridBagConstraints gbc_lblOpcodeSize = new GridBagConstraints();
		gbc_lblOpcodeSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpcodeSize.gridx = 0;
		gbc_lblOpcodeSize.gridy = 3;
		frmOpcodeTableMaker.getContentPane().add(lblOpcodeSize, gbc_lblOpcodeSize);

		JSpinner spinOpcodeSize = new JSpinner();
		spinOpcodeSize.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		GridBagConstraints gbc_spinOpcodeSize = new GridBagConstraints();
		gbc_spinOpcodeSize.insets = new Insets(0, 0, 5, 0);
		gbc_spinOpcodeSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinOpcodeSize.gridx = 1;
		gbc_spinOpcodeSize.gridy = 3;
		frmOpcodeTableMaker.getContentPane().add(spinOpcodeSize, gbc_spinOpcodeSize);

		JLabel lblInstructionSize = new JLabel("Instruction Size :");
		GridBagConstraints gbc_lblInstructionSize = new GridBagConstraints();
		gbc_lblInstructionSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstructionSize.gridx = 0;
		gbc_lblInstructionSize.gridy = 4;
		frmOpcodeTableMaker.getContentPane().add(lblInstructionSize, gbc_lblInstructionSize);

		JSpinner spinInstructionSize = new JSpinner();
		spinInstructionSize.setModel(new SpinnerNumberModel(1, 1, 15, 1));
		GridBagConstraints gbc_spinInstructionSize = new GridBagConstraints();
		gbc_spinInstructionSize.insets = new Insets(0, 0, 5, 0);
		gbc_spinInstructionSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinInstructionSize.gridx = 1;
		gbc_spinInstructionSize.gridy = 4;
		frmOpcodeTableMaker.getContentPane().add(spinInstructionSize, gbc_spinInstructionSize);

		JLabel lblArguments = new JLabel("Arguments");
		GridBagConstraints gbc_lblArguments = new GridBagConstraints();
		gbc_lblArguments.anchor = GridBagConstraints.EAST;
		gbc_lblArguments.insets = new Insets(0, 0, 5, 5);
		gbc_lblArguments.gridx = 0;
		gbc_lblArguments.gridy = 6;
		frmOpcodeTableMaker.getContentPane().add(lblArguments, gbc_lblArguments);

		cbArguments = new JComboBox(argumentTypes);
		cbArguments.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				JComboBox jcb = (JComboBox) arg0.getSource();
				argumentTypeChanged((ArgumentType) jcb.getSelectedItem());
				System.out.println("StateChanged, item selected = " + jcb.getSelectedItem());
			}
		});

		GridBagConstraints gbc_cbArguments = new GridBagConstraints();
		gbc_cbArguments.insets = new Insets(0, 0, 5, 0);
		gbc_cbArguments.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbArguments.gridx = 1;
		gbc_cbArguments.gridy = 6;
		frmOpcodeTableMaker.getContentPane().add(cbArguments, gbc_cbArguments);

		panelRegisters = new JPanel();
		GridBagConstraints gbc_panelRegisters = new GridBagConstraints();
		gbc_panelRegisters.gridwidth = 2;
		gbc_panelRegisters.insets = new Insets(0, 0, 0, 5);
		gbc_panelRegisters.fill = GridBagConstraints.BOTH;
		gbc_panelRegisters.gridx = 0;
		gbc_panelRegisters.gridy = 8;
		frmOpcodeTableMaker.getContentPane().add(panelRegisters, gbc_panelRegisters);
		GridBagLayout gbl_panelRegisters = new GridBagLayout();
		gbl_panelRegisters.columnWidths = new int[] { 0, 0 };
		gbl_panelRegisters.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelRegisters.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRegisters.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelRegisters.setLayout(gbl_panelRegisters);

		JPanel panelRegisterZero = new JPanel();
		GridBagConstraints gbc_panelRegisterZero = new GridBagConstraints();
		gbc_panelRegisterZero.insets = new Insets(0, 0, 5, 0);
		gbc_panelRegisterZero.fill = GridBagConstraints.BOTH;
		gbc_panelRegisterZero.gridx = 0;
		gbc_panelRegisterZero.gridy = 0;
		panelRegisters.add(panelRegisterZero, gbc_panelRegisterZero);
		GridBagLayout gbl_panelRegisterZero = new GridBagLayout();
		gbl_panelRegisterZero.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelRegisterZero.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelRegisterZero.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelRegisterZero.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelRegisterZero.setLayout(gbl_panelRegisterZero);

		panelRegisterOne = new JPanel();
		GridBagConstraints gbc_panelRegisterOne = new GridBagConstraints();
		gbc_panelRegisterOne.insets = new Insets(0, 0, 5, 0);
		gbc_panelRegisterOne.fill = GridBagConstraints.BOTH;
		gbc_panelRegisterOne.gridx = 0;
		gbc_panelRegisterOne.gridy = 1;
		panelRegisters.add(panelRegisterOne, gbc_panelRegisterOne);
		GridBagLayout gbl_panelRegisterOne = new GridBagLayout();
		gbl_panelRegisterOne.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelRegisterOne.rowHeights = new int[] { 0, 0 };
		gbl_panelRegisterOne.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelRegisterOne.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelRegisterOne.setLayout(gbl_panelRegisterOne);

		JLabel lblRegister = new JLabel("Register 1 :");
		GridBagConstraints gbc_lblRegister = new GridBagConstraints();
		gbc_lblRegister.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegister.anchor = GridBagConstraints.EAST;
		gbc_lblRegister.gridx = 0;
		gbc_lblRegister.gridy = 0;
		panelRegisterOne.add(lblRegister, gbc_lblRegister);

		cbRegister1 = new JComboBox();
		GridBagConstraints gbc_cbRegister1 = new GridBagConstraints();
		gbc_cbRegister1.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbRegister1.gridx = 1;
		gbc_cbRegister1.gridy = 0;
		panelRegisterOne.add(cbRegister1, gbc_cbRegister1);

		panelRegisterTwo = new JPanel();
		GridBagConstraints gbc_panelRegisterTwo = new GridBagConstraints();
		gbc_panelRegisterTwo.fill = GridBagConstraints.BOTH;
		gbc_panelRegisterTwo.gridx = 0;
		gbc_panelRegisterTwo.gridy = 2;
		panelRegisters.add(panelRegisterTwo, gbc_panelRegisterTwo);
		GridBagLayout gbl_panelRegisterTwo = new GridBagLayout();
		gbl_panelRegisterTwo.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelRegisterTwo.rowHeights = new int[] { 0, 0 };
		gbl_panelRegisterTwo.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelRegisterTwo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelRegisterTwo.setLayout(gbl_panelRegisterTwo);

		JLabel lblRegister_2 = new JLabel("Register 2 :");
		GridBagConstraints gbc_lblRegister_2 = new GridBagConstraints();
		gbc_lblRegister_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegister_2.anchor = GridBagConstraints.EAST;
		gbc_lblRegister_2.gridx = 0;
		gbc_lblRegister_2.gridy = 0;
		panelRegisterTwo.add(lblRegister_2, gbc_lblRegister_2);

		cbRegister2 = new JComboBox();
		GridBagConstraints gbc_cbRegister2 = new GridBagConstraints();
		gbc_cbRegister2.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbRegister2.gridx = 1;
		gbc_cbRegister2.gridy = 0;
		panelRegisterTwo.add(cbRegister2, gbc_cbRegister2);

		JMenuBar menuBar = new JMenuBar();
		frmOpcodeTableMaker.setJMenuBar(menuBar);
	}// initialize
		// <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>

	Register[] byteRegisters = new Register[] { Register.A, Register.B, Register.C, Register.D,
			Register.E, Register.H, Register.L };

	Register[] wordRegisters = new Register[] { Register.AF, Register.BC, Register.DE, Register.HL, Register.M,
			Register.SP, Register.PC };
	ArgumentType[] argumentTypes = new ArgumentType[] { ArgumentType.NONE, ArgumentType.ADDRESS, ArgumentType.D8,
			ArgumentType.D16, ArgumentType.R8, ArgumentType.R8R8, ArgumentType.R16, ArgumentType.R8D8,
			ArgumentType.R16D16 };

	private JComboBox cbArguments;
	private JPanel panelRegisters;
	private JComboBox cbRegister1;
	private JPanel panelRegisterOne;
	private JPanel panelRegisterTwo;
	private JComboBox cbRegister2;
	private DefaultComboBoxModel byteRegisterModel;
	private DefaultComboBoxModel wordRegisterModel;

	// -------------------------------
	enum Register {
		// Single Byte Registers
		A, B, C, D, E, H, L,
		// Double Byte Registers
		// used for identification only
		// nothing is stored directly into one of these
		BC, DE, HL, M, SP, AF, PC
	}// enum

	enum ArgumentType {
		NONE,
		ADDRESS,
		D8,
		D16,
		R8,
		R8R8,
		R16,
		R8D8,
		R16D16
	}//enum
	
	enum BaseCode{
		STC,CMC,
	}//enum
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private static HashMap<String, String[]> baseCode;
	static {
		baseCode.put("STC",new String[]{"STC","C","Set Carry"});
		baseCode.put("CMC",new String[]{"CMC","C","Complement Carry"});
		baseCode.put("INR",new String[]{"INR","Z,S,P,Aux,C","Increment Register/Memory"});
		baseCode.put("DCR",new String[]{"DCR","Z,S,P,Aux,C","Deccrement Register/Memory"});
		baseCode.put("CMA",new String[]{"CMA","None","Complement Acc"});
		baseCode.put("DAA",new String[]{"DAA","Z,S,P,Aux,C","Decimal Adjust Acc"});
		baseCode.put("NOP",new String[]{"NOP","None","No Operation"});
		baseCode.put("MOV",new String[]{"MOV","None","Move"});
		baseCode.put("STAX",new String[]{"STAX","None","Store Acc"});
		baseCode.put("LDAX",new String[]{"LDAX","None","Load Acc"});
		baseCode.put("ADD",new String[]{"ADD","Z,S,P,Aux,C","Add Register/Memory to Acc"});
		baseCode.put("ADC",new String[]{"ADC","Z,S,P,Aux,C","Add Register/Memory to Acc with Carry"});
		baseCode.put("SUB",new String[]{"SUB","Z,S,P,Aux,C","Subtract Register/Memory from Acc"});
		baseCode.put("SBB",new String[]{"SBB","Z,S,P,Aux,C","Subtract Register/Memory from Acc with Borrow"});
		baseCode.put("ANA",new String[]{"ANA","Z,S,P,C, Aux*","Logical AND Register/Memory with Acc"});
		baseCode.put("XRA",new String[]{"XRA","Z,S,P,Aux,C","Logical XOR Register/Memory with Acc"});
		baseCode.put("ORA",new String[]{"ORA","Z,S,P,C, Aux*","Logical OR Register/Memory with Acc"});
		baseCode.put("CMP",new String[]{"CMP","Z,S,P,Aux,C","Compare Register/Memory with Acc"});
		baseCode.put("RLC",new String[]{"RLC","C","Rotate Left Acc"});
		baseCode.put("RRC",new String[]{"RRC","C","Rotate Right Acc"});
		baseCode.put("RAL",new String[]{"RAL","C","Rotate Left Acc Through Carry"});
		baseCode.put("RAR",new String[]{"RAR","C","Rotate Right Acc Through Carry"});
		baseCode.put("PUSH",new String[]{"PUSH","None","Push Data Onto Stack"});
		baseCode.put("POP",new String[]{"POP","None/Z,S,P,Aux,C","Pop Data Off the Stack"});
		baseCode.put("DAD",new String[]{"DAD","C","Double Add"});
		baseCode.put("INX",new String[]{"INX","None","Increment register Pair"});
		baseCode.put("DCX",new String[]{"DCX","None","Decrement register Pair"});
		baseCode.put("XCHG",new String[]{"XCHG","None","Exchange Registers"});
		baseCode.put("XTHL",new String[]{"XTHL","None","Exchange Stack"});
		baseCode.put("SPHL",new String[]{"SPHL","None","Load SP from H and L"});
		baseCode.put("LXI",new String[]{"LXI","None","Load Register Pair Immediate"});
		baseCode.put("MVI",new String[]{"MVI","None","Move Immediate Data",""});
		baseCode.put("ADI",new String[]{"ADI","Z,S,P,Aux,C","Add Immediate to Acc"});
		baseCode.put("ACI",new String[]{"ACI","Z,S,P,Aux,C","Add Immediate to Acc With Carry"});
		baseCode.put("SUI",new String[]{"SUI","Z,S,P,Aux,C","Subtract Immediate to Acc"});
		baseCode.put("SBI",new String[]{"SBI","Z,S,P,Aux,C","Subtract Immediate to Acc With Borrow"});
		baseCode.put("ANI",new String[]{"ANI","Z,S,P,C","AND Immediate with Acc"});
		baseCode.put("XRI",new String[]{"XRI","Z,S,P,C","XOR Immediate with Acc"});
		baseCode.put("ORI",new String[]{"ORI","Z,S,P,C","OR Immediate with Acc"});
		baseCode.put("CPI",new String[]{"CPI","Z,S,P,Aux,C","Compare Immediate with Acc"});
		baseCode.put("STA",new String[]{"STA","None","Store Acc Direct"});
		baseCode.put("LDA",new String[]{"LDA","None","Load Acc Direct"});
		baseCode.put("SHLD",new String[]{"SHLD","None","Store H and L Direct"});
		baseCode.put("LHLD",new String[]{"LHLD","None","Load H and L Direct"});
		baseCode.put("PCHL",new String[]{"PCHL","None","Load Program Counter from H and L"});
		baseCode.put("JMP",new String[]{"JMP","None","Jump"});
		baseCode.put("JC",new String[]{"JC","None","Jump if Carry"});
		baseCode.put("JNC",new String[]{"JNC","None","Jump if No Carry"});
		baseCode.put("JZ",new String[]{"JZ","None","Jump if Zero"});
		baseCode.put("JNZ",new String[]{"JNZ","None","Jump in Not Zero"});
		baseCode.put("JM",new String[]{"JM","None","Jump if Minus"});
		baseCode.put("JP",new String[]{"JP","None","Jump if Positive"});
		baseCode.put("JPE",new String[]{"JPE","None","Jump if Parity Even",""});
		baseCode.put("JPO",new String[]{"JPO","None","Jump if Parity Odd"});
		baseCode.put("CALL",new String[]{"CALL","None","Call"});
		baseCode.put("CC",new String[]{"CC","None","Call if Carry"});
		baseCode.put("CNC",new String[]{"CNC","None","Call if No Carry"});
		baseCode.put("CZ",new String[]{"CZ","None","Call if Zero"});
		baseCode.put("CNZ",new String[]{"CNZ","None","Call in Not Zero"});
		baseCode.put("CM",new String[]{"CM","None","Call if Minus"});
		baseCode.put("CP",new String[]{"CP","None","Call if Positive"});
		baseCode.put("CPE",new String[]{"CPE","None","Call if Parity Even"});
		baseCode.put("CPO",new String[]{"CPO","None","Call if Parity Odd"});
		baseCode.put("RET",new String[]{"RET","None","Return"});
		baseCode.put("RC",new String[]{"RC","None","Return if Carry"});
		baseCode.put("RNC",new String[]{"RNC","None","Return if No Carry"});
		baseCode.put("RZ",new String[]{"RZ","None","Return if Zero"});
		baseCode.put("RNZ",new String[]{"RNZ","None","Return in Not Zero"});
		baseCode.put("RM",new String[]{"RM","None","Return if Minus"});
		baseCode.put("RP",new String[]{"RP","None","Return if Positive"});
		baseCode.put("RPE",new String[]{"RPE","None","Return if Parity Even"});
		baseCode.put("RPO",new String[]{"RPO","None","Return if Parity Odd"});
		baseCode.put("RST",new String[]{"RST","None","Restart"});
		baseCode.put("EI",new String[]{"EI","None","Enable Interrupts"});
		baseCode.put("DI",new String[]{"DI","None","Disable Interrupts"});
		baseCode.put("IN",new String[]{"IN","None","Input"});
		baseCode.put("OUT",new String[]{"OUT","None","Output"});
		baseCode.put("HLT",new String[]{"HLT","None","Halt"});
			
	} //static
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}// class TableMaker
