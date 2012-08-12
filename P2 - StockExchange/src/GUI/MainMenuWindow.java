package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import classes.Company;
import classes.DataHandler;
import classes.FileHandler;
import classes.Shareholder;
import classes.Transaction;
import exceptions.DuplicateCompanyException;
import exceptions.InsufficientSharesToBuyException;
import exceptions.InvalidCompanyException;
import exceptions.InvalidShareholderException;
import exceptions.NoSuchShareException;
import exceptions.UnsoldSharesOfException;

public class MainMenuWindow {

	private JFrame frmStockExchnageProject;
	private JTextField AddCompany_Name_textField;
	private JTextField AddCompany_Symbol_textField;
	private JTextField AddCompany_SharesAmount_textField;
	private JTextField AddCompany_ShareValue_textField;
	private JTextField RemoveCompany_Symbol_textField;
	private JTextField AddShares_Symbol_textField;
	private JTextField AddShares_Amount_textField;
	private JTextField ViewReportOfCompany_Symbol_textField;
	private JTextPane CompanyReport_textPane;
	private JTextField AddShareholder_Name_textField;
	private JTextField RemoveShareholder_ID_textField;
	private JTextField ViewReportOfShareholder_ID_textField;
	private JTextPane ShareholderReport_textPane;
	private JTextField SplitCompany_Symbol_textField;
	private JTextField UpdateShareV_Symbol_textField;
	private JTextField UpdateShareV_Price_textField;
	private JTextField ViewReportOfStocks_Symbol_textField;
	private JTextPane StocksReport_textPane;
	private JTextField Transaction_ID_textField;
	private JTextField Transaction_Symbol_textField;
	private JTextField Transaction_Amount_textField;
	private JTable Transactions_table;
	private JTable Companies_table;
	private JTable Shareholders_table;

	/**
	 * Create the application.
	 */
	public MainMenuWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStockExchnageProject = new JFrame();
		frmStockExchnageProject.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new SaveOnCloseDialog();
			}
		});
		frmStockExchnageProject.setTitle("Stock Exchnage Project");
		frmStockExchnageProject.setBounds(100, 100, 450, 325);
		frmStockExchnageProject.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmStockExchnageProject.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel MainMenuPanel = new JPanel();
		frmStockExchnageProject.getContentPane().add(MainMenuPanel, "MainMenu");

		JButton btnNewButton = new JButton("Administrate Companies");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) frmStockExchnageProject.getContentPane().getLayout()).show(frmStockExchnageProject.getContentPane(), "AdminCompanies");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel = new JLabel("Main Menu:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));

		JButton btnAdministrateShareholders = new JButton("Administrate Shareholders");
		btnAdministrateShareholders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) frmStockExchnageProject.getContentPane().getLayout()).show(frmStockExchnageProject.getContentPane(), "AdminShareholders");
			}
		});
		btnAdministrateShareholders.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnAdministrateStocks = new JButton("Administrate Stocks");
		btnAdministrateStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) frmStockExchnageProject.getContentPane().getLayout()).show(frmStockExchnageProject.getContentPane(), "AdminStocks");
			}
		});
		btnAdministrateStocks.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnPerformTransaction = new JButton("Perform Transaction");
		btnPerformTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) frmStockExchnageProject.getContentPane().getLayout()).show(frmStockExchnageProject.getContentPane(), "Transactions");
			}
		});
		btnPerformTransaction.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_MainMenuPanel = new GroupLayout(MainMenuPanel);
		gl_MainMenuPanel.setHorizontalGroup(
				gl_MainMenuPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MainMenuPanel.createSequentialGroup()
						.addGroup(gl_MainMenuPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_MainMenuPanel.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblNewLabel))
										.addGroup(gl_MainMenuPanel.createSequentialGroup()
												.addGap(104)
												.addGroup(gl_MainMenuPanel.createParallelGroup(Alignment.LEADING, false)
														.addComponent(btnAdministrateShareholders, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnAdministrateStocks, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnPerformTransaction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
														.addContainerGap(117, Short.MAX_VALUE))
				);
		gl_MainMenuPanel.setVerticalGroup(
				gl_MainMenuPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MainMenuPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnNewButton)
						.addGap(18)
						.addComponent(btnAdministrateShareholders)
						.addGap(18)
						.addComponent(btnAdministrateStocks)
						.addGap(18)
						.addComponent(btnPerformTransaction)
						.addContainerGap(17, Short.MAX_VALUE))
				);
		MainMenuPanel.setLayout(gl_MainMenuPanel);

		JPanel AdminCompanies = new JPanel();
		frmStockExchnageProject.getContentPane().add(AdminCompanies, "AdminCompanies");
		AdminCompanies.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton_1 = new JButton("Return to Main Menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMainMenuAction();
			}
		});
		AdminCompanies.add(btnNewButton_1, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 4)
					refillCompaniesTable();
			}
		});
		AdminCompanies.add(tabbedPane, BorderLayout.CENTER);

		JPanel AddCompany = new JPanel();
		tabbedPane.addTab("Add Company", null, AddCompany, null);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		AddCompany_Name_textField = new JTextField();
		AddCompany_Name_textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Symbol:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);

		AddCompany_Symbol_textField = new JTextField();
		AddCompany_Symbol_textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Initial Shares Amount:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);

		AddCompany_SharesAmount_textField = new JTextField();
		AddCompany_SharesAmount_textField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Value of Share:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);

		AddCompany_ShareValue_textField = new JTextField();
		AddCompany_ShareValue_textField.setColumns(10);

		JButton btnNewButton_2 = new JButton("ADD");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AddCompany_Name_textField.getText().isEmpty() ||
						AddCompany_Symbol_textField.getText().isEmpty() ||
						AddCompany_SharesAmount_textField.getText().isEmpty() ||
						AddCompany_ShareValue_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameters.");
					return;
				}

				String companyName = AddCompany_Name_textField.getText().trim();
				String companySymbol = AddCompany_Symbol_textField.getText().trim();
				String amountOfShares = AddCompany_SharesAmount_textField.getText().trim();
				String sharePrice = AddCompany_ShareValue_textField.getText().trim();

				try {
					if(companySymbol.length() > 5 || companySymbol.length() < 3){
						new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
						return;}

					if(Integer.parseInt(amountOfShares) < 10000){
						new MessageDialog("Invalid parameters:\nAmountOfShares must be greater than 10,000.");
						return;}

					if(Double.parseDouble(sharePrice) < 0){
						new MessageDialog("Invalid parameters:\nSharePrice cannot be negative.");
						return;}
					if(sharePrice.indexOf('.') != -1){
						if(sharePrice.split("\\.")[1].length() > 2){
							new MessageDialog("Invalid parameters:\nSharePrice can only contain two decimal places.");
							return;}
					}
				} catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}

				Company c = new Company(companyName, companySymbol.toUpperCase(), Integer.parseInt(amountOfShares), Double.parseDouble(sharePrice));
				try {
					DataHandler.addCompany(c);
					new MessageDialog("Company added successfully.");
					AddCompany_Name_textField.setText("");
					AddCompany_Symbol_textField.setText("");
					AddCompany_SharesAmount_textField.setText("");
					AddCompany_ShareValue_textField.setText("");
				} catch (DuplicateCompanyException e1) {
					new MessageDialog("Unable to add company:\nCompany already exists.");
				}
			}
		});

		GroupLayout gl_AddCompany = new GroupLayout(AddCompany);
		gl_AddCompany.setHorizontalGroup(
				gl_AddCompany.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_AddCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_AddCompany.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18)
								.addGroup(gl_AddCompany.createParallelGroup(Alignment.LEADING)
										.addComponent(AddCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(AddCompany_SharesAmount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(AddCompany_ShareValue_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(AddCompany_Name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(209, Short.MAX_VALUE))
										.addGroup(gl_AddCompany.createSequentialGroup()
												.addContainerGap(366, Short.MAX_VALUE)
												.addComponent(btnNewButton_2)
												.addContainerGap())
				);
		gl_AddCompany.setVerticalGroup(
				gl_AddCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_AddCompany.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(AddCompany_Name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_AddCompany.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_2)
										.addComponent(AddCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_AddCompany.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_3)
												.addComponent(AddCompany_SharesAmount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(18)
												.addGroup(gl_AddCompany.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_4)
														.addComponent(AddCompany_ShareValue_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
														.addComponent(btnNewButton_2)
														.addContainerGap())
				);
		AddCompany.setLayout(gl_AddCompany);

		JPanel RemoveCompany = new JPanel();
		tabbedPane.addTab("Remove Company", null, RemoveCompany, null);

		JLabel lblSymbol = new JLabel("Symbol:");
		lblSymbol.setHorizontalAlignment(SwingConstants.RIGHT);

		RemoveCompany_Symbol_textField = new JTextField();
		RemoveCompany_Symbol_textField.setColumns(10);

		JButton btnRemove = new JButton("REMOVE");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(RemoveCompany_Symbol_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}
				String companySymbol = RemoveCompany_Symbol_textField.getText().trim();

				if(companySymbol.length() > 5 || companySymbol.length() < 3){
					new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
					return;}

				try {
					DataHandler.removeCompany(companySymbol);
					new MessageDialog("Company removed sucessfully");
					RemoveCompany_Symbol_textField.setText("");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to remove company:\nNo such company exists.");
				} catch (UnsoldSharesOfException ex) {
					new MessageDialog("Unable to remove company:\nCompany has shareholders.");
				}
			}
		});
		GroupLayout gl_RemoveCompany = new GroupLayout(RemoveCompany);
		gl_RemoveCompany.setHorizontalGroup(
				gl_RemoveCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RemoveCompany.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblSymbol)
						.addGap(18)
						.addComponent(RemoveCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(277, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_RemoveCompany.createSequentialGroup()
								.addContainerGap(346, Short.MAX_VALUE)
								.addComponent(btnRemove)
								.addContainerGap())
				);
		gl_RemoveCompany.setVerticalGroup(
				gl_RemoveCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RemoveCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_RemoveCompany.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSymbol)
								.addComponent(RemoveCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
								.addComponent(btnRemove)
								.addContainerGap())
				);
		RemoveCompany.setLayout(gl_RemoveCompany);

		JPanel AddShares = new JPanel();
		tabbedPane.addTab("Add Shares", null, AddShares, null);

		JLabel lblSymbol_1 = new JLabel("Symbol:");
		lblSymbol_1.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblAmountOfShares = new JLabel("Amount of shares:");

		AddShares_Symbol_textField = new JTextField();
		AddShares_Symbol_textField.setColumns(10);

		AddShares_Amount_textField = new JTextField();
		AddShares_Amount_textField.setColumns(10);

		JButton btnAddShares = new JButton("ADD SHARES");
		btnAddShares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AddShares_Symbol_textField.getText().isEmpty() ||
						AddShares_Amount_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameters.");
					return;
				}

				String companySymbol = AddShares_Symbol_textField.getText().trim();
				String amountOfShares = AddShares_Amount_textField.getText().trim();

				try {
					if(companySymbol.length() > 5 || companySymbol.length() < 3){
						new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
						return;}

					if(Integer.parseInt(amountOfShares) < 1){
						new MessageDialog("Invalid parameter:\nAmount must be greater than zero.");
						return;}

				} catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}

				try {
					int addedShares = DataHandler.addShareToCompany(companySymbol, Integer.parseInt(amountOfShares));
					new MessageDialog("Shares added sucessfully.\nNew amount: " + addedShares);
					AddShares_Symbol_textField.setText("");
					AddShares_Amount_textField.setText("");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to add shares:\nNo such company exists.");
				}
			}
		});
		GroupLayout gl_AddShares = new GroupLayout(AddShares);
		gl_AddShares.setHorizontalGroup(
				gl_AddShares.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddShares.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_AddShares.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAmountOfShares)
								.addComponent(lblSymbol_1))
								.addGap(18)
								.addGroup(gl_AddShares.createParallelGroup(Alignment.LEADING)
										.addComponent(AddShares_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(AddShares_Amount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(226, Short.MAX_VALUE))
										.addGroup(Alignment.TRAILING, gl_AddShares.createSequentialGroup()
												.addContainerGap(330, Short.MAX_VALUE)
												.addComponent(btnAddShares)
												.addContainerGap())
				);
		gl_AddShares.setVerticalGroup(
				gl_AddShares.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddShares.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_AddShares.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSymbol_1)
								.addComponent(AddShares_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_AddShares.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAmountOfShares)
										.addComponent(AddShares_Amount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
										.addComponent(btnAddShares)
										.addContainerGap())
				);
		AddShares.setLayout(gl_AddShares);

		JPanel ViewReportOfCompany = new JPanel();
		tabbedPane.addTab("View Report", null, ViewReportOfCompany, null);

		JLabel lblSymbol_2 = new JLabel("Symbol:");
		lblSymbol_2.setHorizontalAlignment(SwingConstants.RIGHT);

		ViewReportOfCompany_Symbol_textField = new JTextField();
		ViewReportOfCompany_Symbol_textField.setColumns(10);

		JButton btnViewReport = new JButton("VIEW REPORT");
		btnViewReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ViewReportOfCompany_Symbol_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}
				String companySymbol = ViewReportOfCompany_Symbol_textField.getText().trim();

				if(companySymbol.length() > 5 || companySymbol.length() < 3){
					new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
					return;}
				try {
					CompanyReport_textPane.setText((DataHandler.showReportOfCompany(companySymbol)));
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to get report:\nNo such company exists.");
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_ViewReportOfCompany = new GroupLayout(ViewReportOfCompany);
		gl_ViewReportOfCompany.setHorizontalGroup(
				gl_ViewReportOfCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_ViewReportOfCompany.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
								.addGroup(gl_ViewReportOfCompany.createSequentialGroup()
										.addComponent(lblSymbol_2)
										.addGap(18)
										.addComponent(ViewReportOfCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnViewReport, Alignment.TRAILING))
										.addContainerGap())
				);
		gl_ViewReportOfCompany.setVerticalGroup(
				gl_ViewReportOfCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_ViewReportOfCompany.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSymbol_2)
								.addComponent(ViewReportOfCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnViewReport)
								.addContainerGap())
				);

		CompanyReport_textPane = new JTextPane();
		CompanyReport_textPane.setEditable(false);
		scrollPane.setViewportView(CompanyReport_textPane);
		ViewReportOfCompany.setLayout(gl_ViewReportOfCompany);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Companies List", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setEnabled(false);
		panel.add(scrollPane_4, BorderLayout.CENTER);
		
		Companies_table = new JTable();
		scrollPane_4.setViewportView(Companies_table);

		JPanel AdminShareholders = new JPanel();
		frmStockExchnageProject.getContentPane().add(AdminShareholders, "AdminShareholders");
		AdminShareholders.setLayout(new BorderLayout(0, 0));

		JButton btnReturnToMain = new JButton("Return to Main Menu");
		btnReturnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMainMenuAction();
			}
		});
		AdminShareholders.add(btnReturnToMain, BorderLayout.NORTH);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(((JTabbedPane)e.getSource()).getSelectedIndex() == 3)
					refillShareholdersTable();
			}
		});
		tabbedPane_1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		AdminShareholders.add(tabbedPane_1, BorderLayout.CENTER);

		JPanel AddShareholder = new JPanel();
		tabbedPane_1.addTab("Add Shaeholder", null, AddShareholder, null);

		JLabel lblNewLabel_5 = new JLabel("Name:");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);

		AddShareholder_Name_textField = new JTextField();
		AddShareholder_Name_textField.setColumns(10);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AddShareholder_Name_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}

				String shareholderName = AddShareholder_Name_textField.getText().trim();

				new MessageDialog("Added shareholder sucessfully.\nID is: " + DataHandler.addShareholder(shareholderName));
				AddShareholder_Name_textField.setText("");
			}
		});
		GroupLayout gl_AddShareholder = new GroupLayout(AddShareholder);
		gl_AddShareholder.setHorizontalGroup(
				gl_AddShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddShareholder.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel_5)
						.addGap(18)
						.addComponent(AddShareholder_Name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(284, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_AddShareholder.createSequentialGroup()
								.addContainerGap(330, Short.MAX_VALUE)
								.addComponent(btnAdd)
								.addContainerGap())
				);
		gl_AddShareholder.setVerticalGroup(
				gl_AddShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddShareholder.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_AddShareholder.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_5)
								.addComponent(AddShareholder_Name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
								.addComponent(btnAdd)
								.addContainerGap())
				);
		AddShareholder.setLayout(gl_AddShareholder);

		JPanel RemoveShareholder = new JPanel();
		tabbedPane_1.addTab("Remove Shareholder", null, RemoveShareholder, null);

		JLabel lblNewLabel_6 = new JLabel("Shareholder ID:");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);

		RemoveShareholder_ID_textField = new JTextField();
		RemoveShareholder_ID_textField.setColumns(10);

		JButton btnRemove_1 = new JButton("REMOVE");
		btnRemove_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(RemoveShareholder_ID_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}

				int shareholderId;
				try{
					shareholderId = Integer.parseInt(RemoveShareholder_ID_textField.getText().trim());
				}  catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}

				try {
					DataHandler.removeShareholder(shareholderId);
					new MessageDialog("Shareholder removed sucessfully.");
					RemoveShareholder_ID_textField.setText("");
				} catch (InvalidShareholderException ex) {
					new MessageDialog("Unable to remove shareholder:\nNo such shareholder exists.");
				} catch (UnsoldSharesOfException ex) {
					new MessageDialog("Unable to remove shareholder:\nShareholder has unsold shares.");
				}
			}
		});
		GroupLayout gl_RemoveShareholder = new GroupLayout(RemoveShareholder);
		gl_RemoveShareholder.setHorizontalGroup(
				gl_RemoveShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RemoveShareholder.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel_6)
						.addGap(18)
						.addComponent(RemoveShareholder_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(239, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_RemoveShareholder.createSequentialGroup()
								.addContainerGap(330, Short.MAX_VALUE)
								.addComponent(btnRemove_1)
								.addContainerGap())
				);
		gl_RemoveShareholder.setVerticalGroup(
				gl_RemoveShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RemoveShareholder.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_RemoveShareholder.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_6)
								.addComponent(RemoveShareholder_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
								.addComponent(btnRemove_1)
								.addContainerGap())
				);
		RemoveShareholder.setLayout(gl_RemoveShareholder);

		JPanel ViewReportOfShareholder = new JPanel();
		tabbedPane_1.addTab("View Report", null, ViewReportOfShareholder, null);

		JLabel lblShareholderId = new JLabel("Shareholder ID:");
		lblShareholderId.setHorizontalAlignment(SwingConstants.RIGHT);

		ViewReportOfShareholder_ID_textField = new JTextField();
		ViewReportOfShareholder_ID_textField.setColumns(10);

		JButton btnViewReport_1 = new JButton("VIEW REPORT");
		btnViewReport_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ViewReportOfShareholder_ID_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}

				int shareholderId;
				try{
					shareholderId = Integer.parseInt(ViewReportOfShareholder_ID_textField.getText().trim());
				}  catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}

				try {
					ShareholderReport_textPane.setText(DataHandler.showReportOfShareholder(shareholderId));
				} catch (InvalidShareholderException ex) {
					new MessageDialog("Unable to get report:\nNo such shareholder exists.");
				}
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_ViewReportOfShareholder = new GroupLayout(ViewReportOfShareholder);
		gl_ViewReportOfShareholder.setHorizontalGroup(
				gl_ViewReportOfShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfShareholder.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_ViewReportOfShareholder.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_ViewReportOfShareholder.createSequentialGroup()
										.addComponent(lblShareholderId)
										.addGap(18)
										.addComponent(ViewReportOfShareholder_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnViewReport_1, Alignment.TRAILING)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
										.addContainerGap())
				);
		gl_ViewReportOfShareholder.setVerticalGroup(
				gl_ViewReportOfShareholder.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfShareholder.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_ViewReportOfShareholder.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblShareholderId)
								.addComponent(ViewReportOfShareholder_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnViewReport_1)
								.addContainerGap())
				);

		ShareholderReport_textPane = new JTextPane();
		scrollPane_1.setViewportView(ShareholderReport_textPane);
		ViewReportOfShareholder.setLayout(gl_ViewReportOfShareholder);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("Shareholders List", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_5 = new JScrollPane();
		panel_1.add(scrollPane_5, BorderLayout.CENTER);
		
		Shareholders_table = new JTable();
		scrollPane_5.setViewportView(Shareholders_table);

		JPanel AdminStocks = new JPanel();
		frmStockExchnageProject.getContentPane().add(AdminStocks, "AdminStocks");
		AdminStocks.setLayout(new BorderLayout(0, 0));

		JButton btnReturnToMain_1 = new JButton("Return to Main Menu");
		btnReturnToMain_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMainMenuAction();
			}
		});
		AdminStocks.add(btnReturnToMain_1, BorderLayout.NORTH);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		AdminStocks.add(tabbedPane_2, BorderLayout.CENTER);

		JPanel SplitCompany = new JPanel();
		tabbedPane_2.addTab("Split Company", null, SplitCompany, null);

		JLabel lblCompanySymbol = new JLabel("Company Symbol:");
		lblCompanySymbol.setHorizontalAlignment(SwingConstants.RIGHT);

		SplitCompany_Symbol_textField = new JTextField();
		SplitCompany_Symbol_textField.setColumns(10);

		JButton btnSplit = new JButton("SPLIT");
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SplitCompany_Symbol_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}

				String companySymbol = SplitCompany_Symbol_textField.getText().trim();

				if(companySymbol.length() > 5 || companySymbol.length() < 3){
					new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
					return;}

				try {
					new MessageDialog(DataHandler.splitCompany(companySymbol));
					SplitCompany_Symbol_textField.setText("");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to perform split:\nNo such company exists.");
				}
			}
		});
		GroupLayout gl_SplitCompany = new GroupLayout(SplitCompany);
		gl_SplitCompany.setHorizontalGroup(
				gl_SplitCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SplitCompany.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCompanySymbol)
						.addGap(18)
						.addComponent(SplitCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(229, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_SplitCompany.createSequentialGroup()
								.addContainerGap(330, Short.MAX_VALUE)
								.addComponent(btnSplit)
								.addContainerGap())
				);
		gl_SplitCompany.setVerticalGroup(
				gl_SplitCompany.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SplitCompany.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_SplitCompany.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCompanySymbol)
								.addComponent(SplitCompany_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
								.addComponent(btnSplit)
								.addContainerGap())
				);
		SplitCompany.setLayout(gl_SplitCompany);

		JPanel UpdateShareValue = new JPanel();
		tabbedPane_2.addTab("Update Share Value", null, UpdateShareValue, null);

		JLabel lblCompanySymbol_1 = new JLabel("Company Symbol:");

		UpdateShareV_Symbol_textField = new JTextField();
		UpdateShareV_Symbol_textField.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("New Share Price:");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);

		UpdateShareV_Price_textField = new JTextField();
		UpdateShareV_Price_textField.setColumns(10);

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UpdateShareV_Symbol_textField.getText().isEmpty() || UpdateShareV_Price_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameters.");
					return;}

				String companySymbol = UpdateShareV_Symbol_textField.getText().trim();
				String sharePrice = UpdateShareV_Price_textField.getText().trim();
				
				try {
					if(companySymbol.length() > 5 || companySymbol.length() < 3){
						new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
						return;}
					if(Double.parseDouble(sharePrice) < 0){
						new MessageDialog("Invalid parameters:\nSharePrice cannot be negative.");
						return;}
					if(sharePrice.indexOf('.') != -1){
						if(sharePrice.split("\\.")[1].length() > 2){
							new MessageDialog("Invalid parameters:\nSharePrice can only contain two decimal places.");
							return;}
					}
				} catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}
				
				try {
					DataHandler.updateShareValue(companySymbol, Double.parseDouble(sharePrice));
					new MessageDialog("Successfully updated share value.");
					UpdateShareV_Symbol_textField.setText("");
					UpdateShareV_Price_textField.setText("");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to update share value:\nNo such company exists.");
				}

			}
		});
		GroupLayout gl_UpdateShareValue = new GroupLayout(UpdateShareValue);
		gl_UpdateShareValue.setHorizontalGroup(
				gl_UpdateShareValue.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UpdateShareValue.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_UpdateShareValue.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_7)
								.addComponent(lblCompanySymbol_1))
								.addGap(18)
								.addGroup(gl_UpdateShareValue.createParallelGroup(Alignment.LEADING)
										.addComponent(UpdateShareV_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(UpdateShareV_Price_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(229, Short.MAX_VALUE))
										.addGroup(Alignment.TRAILING, gl_UpdateShareValue.createSequentialGroup()
												.addContainerGap(330, Short.MAX_VALUE)
												.addComponent(btnUpdate)
												.addContainerGap())
				);
		gl_UpdateShareValue.setVerticalGroup(
				gl_UpdateShareValue.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UpdateShareValue.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_UpdateShareValue.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCompanySymbol_1)
								.addComponent(UpdateShareV_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_UpdateShareValue.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_7)
										.addComponent(UpdateShareV_Price_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
										.addComponent(btnUpdate)
										.addContainerGap())
				);
		UpdateShareValue.setLayout(gl_UpdateShareValue);

		JPanel ViewReportOfStocks = new JPanel();
		tabbedPane_2.addTab("View Report", null, ViewReportOfStocks, null);
		
		JLabel lblCompanySymbol_2 = new JLabel("Company Symbol:");
		lblCompanySymbol_2.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ViewReportOfStocks_Symbol_textField = new JTextField();
		ViewReportOfStocks_Symbol_textField.setColumns(10);
		
		JButton btnViewReport_2 = new JButton("VIEW REPORT");
		btnViewReport_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ViewReportOfStocks_Symbol_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameter.");
					return;}
				String companySymbol = ViewReportOfStocks_Symbol_textField.getText().trim();

				if(companySymbol.length() > 5 || companySymbol.length() < 3){
					new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
					return;}
				try {
					StocksReport_textPane.setText(DataHandler.showReportOfShare(companySymbol));
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to get report:\nNo such company exists.");
				}

			}
		});
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_ViewReportOfStocks = new GroupLayout(ViewReportOfStocks);
		gl_ViewReportOfStocks.setHorizontalGroup(
			gl_ViewReportOfStocks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfStocks.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_ViewReportOfStocks.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ViewReportOfStocks.createSequentialGroup()
							.addComponent(lblCompanySymbol_2)
							.addGap(18)
							.addComponent(ViewReportOfStocks_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnViewReport_2, Alignment.TRAILING)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_ViewReportOfStocks.setVerticalGroup(
			gl_ViewReportOfStocks.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewReportOfStocks.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_ViewReportOfStocks.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompanySymbol_2)
						.addComponent(ViewReportOfStocks_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnViewReport_2)
					.addContainerGap())
		);
		
		StocksReport_textPane = new JTextPane();
		scrollPane_2.setViewportView(StocksReport_textPane);
		ViewReportOfStocks.setLayout(gl_ViewReportOfStocks);
		
		JPanel Transactions = new JPanel();
		frmStockExchnageProject.getContentPane().add(Transactions, "Transactions");
		Transactions.setLayout(new BorderLayout(0, 0));
		
		JButton btnReturnToMain_2 = new JButton("Return To Main Menu");
		btnReturnToMain_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMainMenuAction();
			}
		});
		Transactions.add(btnReturnToMain_2, BorderLayout.NORTH);
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 1)
					refillTransactionTable();
			}
		});
		Transactions.add(tabbedPane_3, BorderLayout.CENTER);
		
		JPanel BuyOrSell = new JPanel();
		tabbedPane_3.addTab("Buy or Sell Shares", null, BuyOrSell, null);
		
		JLabel lblNewLabel_8 = new JLabel("Shareholder ID:");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Transaction_ID_textField = new JTextField();
		Transaction_ID_textField.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Company Symbol:");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Transaction_Symbol_textField = new JTextField();
		Transaction_Symbol_textField.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Amount of Shares:");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Transaction_Amount_textField = new JTextField();
		Transaction_Amount_textField.setColumns(10);
		
		JButton btnSell = new JButton("SELL");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Transaction_Symbol_textField.getText().isEmpty() || 
						Transaction_ID_textField.getText().isEmpty() ||
						Transaction_Amount_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameters.");
					return;}

				int shareholderId;
				String companySymbol = Transaction_Symbol_textField.getText().trim();
				String amountOfShares = Transaction_Amount_textField.getText().trim();

				try {
					if(companySymbol.length() > 5 || companySymbol.length() < 3){
						new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
						return;}
					
					if(Integer.parseInt(amountOfShares) < 1){
						new MessageDialog("Invalid parameter:\nAmount must be greater than zero.");
						return;}
					
					shareholderId = Integer.parseInt(Transaction_ID_textField.getText().trim());
				}  catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}
				
				try {
					String resume = DataHandler.sellShare(shareholderId, companySymbol, Integer.parseInt(amountOfShares));
					new MessageDialog("Sell operation successful:\nCapital gain was of $" + resume);
					Transaction_Symbol_textField.setText("");
					Transaction_ID_textField.setText("");
					Transaction_Amount_textField.setText("");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to perform sell:\nNo such company exists.");
				} catch (InvalidShareholderException ex) {
					new MessageDialog("Unable to perform sell:\nNo such shareholder exists.");
				} catch (NoSuchShareException ex) {
					new MessageDialog("Unable to perform sell:\nShareholder dosen't own any share of such Company.");
				} catch (InsufficientSharesToBuyException ex) {
					new MessageDialog("Unable to perform sell:\nShareholder dosen't have enough shares to sell.");
				}
			}
		});
		
		JButton btnBuy = new JButton("BUY");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Transaction_Symbol_textField.getText().isEmpty() || 
						Transaction_ID_textField.getText().isEmpty() ||
						Transaction_Amount_textField.getText().isEmpty()){
					new MessageDialog("Missing Parameters.");
					return;}

				int shareholderId;
				String companySymbol = Transaction_Symbol_textField.getText().trim();
				String amountOfShares = Transaction_Amount_textField.getText().trim();

				try {
					if(companySymbol.length() > 5 || companySymbol.length() < 3){
						new MessageDialog("Invalid parameter:\nCompany Symbol is invalid.");
						return;}
					
					if(Integer.parseInt(amountOfShares) < 1){
						new MessageDialog("Invalid parameter:\nAmount must be greater than zero.");
						return;}
					
					shareholderId = Integer.parseInt(Transaction_ID_textField.getText().trim());
				}  catch (InputMismatchException ex){
					new MessageDialog("Invalid parameters.");
					return;
				} catch (NumberFormatException ex){
					new MessageDialog("Invalid parameters.");
					return;
				}
				
				try {
					String resume = DataHandler.buyShares(shareholderId, companySymbol, Integer.parseInt(amountOfShares));
					new MessageDialog(resume);
					Transaction_Symbol_textField.setText("");
					Transaction_ID_textField.setText("");
					Transaction_Amount_textField.setText("");
				} catch (InvalidShareholderException ex) {
					new MessageDialog("Unable to perform buy:\nNo such shareholder exists.");
				} catch (InvalidCompanyException ex) {
					new MessageDialog("Unable to perform buy:\nNo such company exists.");
				} catch (InsufficientSharesToBuyException ex) {
					new MessageDialog("Unable to perform buy:\nInsufficient shares owned by company to buy from.");
				}
			}
		});
		GroupLayout gl_BuyOrSell = new GroupLayout(BuyOrSell);
		gl_BuyOrSell.setHorizontalGroup(
			gl_BuyOrSell.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BuyOrSell.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_10)
						.addComponent(lblNewLabel_9)
						.addComponent(lblNewLabel_8))
					.addGap(18)
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.LEADING)
						.addComponent(Transaction_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Transaction_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Transaction_Amount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(225, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_BuyOrSell.createSequentialGroup()
					.addContainerGap(257, Short.MAX_VALUE)
					.addComponent(btnBuy)
					.addGap(18)
					.addComponent(btnSell)
					.addContainerGap())
		);
		gl_BuyOrSell.setVerticalGroup(
			gl_BuyOrSell.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BuyOrSell.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_8)
						.addComponent(Transaction_ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_9)
						.addComponent(Transaction_Symbol_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_10)
						.addComponent(Transaction_Amount_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
					.addGroup(gl_BuyOrSell.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSell)
						.addComponent(btnBuy))
					.addContainerGap())
		);
		BuyOrSell.setLayout(gl_BuyOrSell);
		
		JPanel TransactionList = new JPanel();
		tabbedPane_3.addTab("Transaction List", null, TransactionList, null);
		TransactionList.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		TransactionList.add(scrollPane_3, BorderLayout.CENTER);
		
		Transactions_table = new JTable();
		scrollPane_3.setViewportView(Transactions_table);

		JMenuBar menuBar = new JMenuBar();
		frmStockExchnageProject.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileHandler.writeAllData();
					new MessageDialog("Saved successfully.");
				} catch (IOException ex) {
					ex.printStackTrace();
					System.out.println("IO Error upon saving.");
				}
			}
		});
		mnFile.add(mntmSave);
		frmStockExchnageProject.setVisible(true);
	}

	private void returnToMainMenuAction() {
		((CardLayout) frmStockExchnageProject.getContentPane().getLayout()).show(frmStockExchnageProject.getContentPane(), "MainMenu");
	}
	
	private String[][] createTransactionsTable(
			ArrayList<Transaction> list) {
		String data[][] = new String[list.size()][6];
		for(int i=0; i < list.size(); i++){
			Transaction t = list.get(i);
			if(t.getBuyOrSell() == Transaction.BUY)
				data[i][0] = "B";
			else
				data[i][0] = "S";
			data[i][1] = "" + t.getShareholderId();
			data[i][2] = t.getCompanySymbol();
			data[i][3] = "" + t.getAmount();
			data[i][4] = "" + t.getPrice();
			data[i][5] = DataHandler.dateFormat.format(t.getDate());
			
		}
		return data;
	}
	
	private String[][] createCompaniesTable(
			ArrayList<Company> list) {
		String data[][] = new String[list.size()][2];
		for(int i=0; i < list.size(); i++){
			Company c = list.get(i);
			data[i][0] = c.getCompanySymbol();
			data[i][1] = c.getCompanyName();
		}
		return data;
	}
	
	private Object[][] createShareholdersTable(
			ArrayList<Shareholder> list) {
		Object data[][] = new Object[list.size()][3];
		for(int i=0; i < list.size(); i++){
			Shareholder s = list.get(i);
			data[i][0] = "" + s.getShareholderId();
			data[i][1] = s.getShareholderName();
			data[i][2] = s.isShareHolderActive();
		}
		return data;
	}
	
	private void refillTransactionTable() {
		JScrollPane scrollPane = (JScrollPane) Transactions_table.getParent().getParent();
		
		String columnNames[] = {"Buy/Sell","ShareholderID","CompanySymbol","AmountOfShares","Price","Date"};
		Object data[][] = createTransactionsTable(DataHandler.getTransactions());
		Transactions_table = new JTable(data, columnNames);
		Transactions_table.setEnabled(false);
		Transactions_table.setColumnSelectionAllowed(true);
		Transactions_table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(Transactions_table);
	}
	
	private void refillCompaniesTable() {
		JScrollPane scrollPane = (JScrollPane) Companies_table.getParent().getParent();
		
		String columnNames[] = {"Symbol","Name"};
		Object data[][] = createCompaniesTable(DataHandler.getCompanies());
		Companies_table = new JTable(data, columnNames);
		Companies_table.setEnabled(false);
		Companies_table.setColumnSelectionAllowed(true);
		Companies_table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(Companies_table);
	}
	
	private void refillShareholdersTable() {
		JScrollPane scrollPane = (JScrollPane) Shareholders_table.getParent().getParent();
		
		String columnNames[] = {"ID","Name","Active"};
		Object data[][] = createShareholdersTable(DataHandler.getShareholders());
		Shareholders_table = new JTable(data, columnNames);
		Shareholders_table.setEnabled(false);
		Shareholders_table.setColumnSelectionAllowed(true);
		Shareholders_table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(Shareholders_table);
	}
}
