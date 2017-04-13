package savingsAccount;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class MainGUI
{

	private JFrame frame;
	private JComboBox cbAcctNum;
	private JTextField txtName;
	private JTextField txtAcctNum;
	private JTextField txtBalance;
	private JTextField txtMonthlyInterest;
	private JTextField txtAnnualRate;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnCalcInterest;
	private String pathName;
	private String fileName;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI()
	{
		initialize();
		if (SavingsAccountList.readAccountFile())
		{// success - set buttons
			setDefaultButtonState();
			loadAccountComboBox();
		}
		else
		{// no records read - set buttons
			setNewButtonState();
		}
		// txtAnnualRate.setText(String.format("%12.2f",SavingsAccountList.saList.getAnnualInterestRate()));
	}// end MainGUI()

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		cbAcctNum = new JComboBox();
		cbAcctNum.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * update the text fields with the selected item
				 */
				updateTextFields(cbAcctNum.getSelectedIndex());
			}
		});
		cbAcctNum.setBounds(10, 32, 91, 20);
		frame.getContentPane().add(cbAcctNum);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(242, 15, 46, 14);
		frame.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setBounds(242, 29, 110, 20);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);

		JLabel lblAccounts = new JLabel("Accounts");
		lblAccounts.setBounds(10, 15, 62, 14);
		frame.getContentPane().add(lblAccounts);

		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(262, 70, 68, 14);
		frame.getContentPane().add(lblBalance);

		txtBalance = new JTextField();
		txtBalance.setBounds(262, 85, 68, 20);
		frame.getContentPane().add(txtBalance);
		txtBalance.setColumns(10);

		JLabel lblMonthlyIntrest = new JLabel("Monthly Int.");
		lblMonthlyIntrest.setBounds(262, 116, 91, 14);
		frame.getContentPane().add(lblMonthlyIntrest);

		txtMonthlyInterest = new JTextField();
		txtMonthlyInterest.setEditable(false);
		txtMonthlyInterest.setBounds(262, 131, 68, 20);
		frame.getContentPane().add(txtMonthlyInterest);
		txtMonthlyInterest.setColumns(10);

		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				/*
				 * set new button state and clear text fields
				 */
				setNewButtonState();
				clearTextFields();
			}
		});
		btnNew.setBounds(143, 29, 89, 23);
		frame.getContentPane().add(btnNew);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * create a new account with data from balance, acct number and
				 * name text fields
				 */
				SavingsAccount sa = new SavingsAccount(Double.parseDouble(txtBalance.getText()), txtAcctNum.getText(),
						txtName.getText());
				// validate account number
				if (isValidAccountNumber(txtAcctNum))
				{
					// rome
					// add the account to the savings account list
					SavingsAccountList.saList.add(sa);
					// add the account number to the combo box
					cbAcctNum.addItem(sa.getAccountNumber());
					// set selected item to this item
					cbAcctNum.setSelectedIndex(SavingsAccountList.saList.size() - 1);
					// set default button state
					setDefaultButtonState();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid account number entered. Terminating.");
					System.exit(1);
				}
			}

			private boolean isValidAccountNumber(JTextField txtAcctNum)
			{
				String account = txtAcctNum.getText(); 
				if(account.matches("S[0-9]{4}"))
				{
					return true;
				}
				return false;
			}
		});
		btnSave.setBounds(143, 60, 89, 23);
		frame.getContentPane().add(btnSave);

		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * display JOptionPane input dialog to get deposit amount add
				 * deposit amount to balance update balance text field
				 */
				String sDeposit = JOptionPane.showInputDialog("Please enter deposit amount",
						JOptionPane.QUESTION_MESSAGE);
				SavingsAccount sa = SavingsAccountList.saList.get(cbAcctNum.getSelectedIndex());
				sa.credit(Double.parseDouble(sDeposit));
				txtBalance.setText(String.format("%.2f", sa.getSavingsBalance()));
			}
		});
		btnDeposit.setBounds(143, 94, 89, 23);
		frame.getContentPane().add(btnDeposit);

		btnWithdraw = new JButton("Withdrawal");
		btnWithdraw.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * display JOptionPane input dialog to get withdrawal amount
				 * deduct withdrawal amount from balance update balance text
				 * field
				 */
				String sWithdraw = JOptionPane.showInputDialog("Please enter withdrawal amount",
						JOptionPane.QUESTION_MESSAGE);
				SavingsAccount sa = SavingsAccountList.saList.get(cbAcctNum.getSelectedIndex());
				sa.debit(Double.parseDouble(sWithdraw));
				txtBalance.setText(String.format("%.2f", sa.getSavingsBalance()));
			}
		});
		btnWithdraw.setBounds(143, 128, 107, 23);
		frame.getContentPane().add(btnWithdraw);

		btnCalcInterest = new JButton("Calc Monthly Interest");
		btnCalcInterest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * get the savings account corresponding to the selected item in
				 * the combobox
				 */
				SavingsAccount sa = SavingsAccountList.saList.get(cbAcctNum.getSelectedIndex());
				// calculate and update the monthly interest text field
				txtMonthlyInterest.setText(String.format("%.2f", sa.calculateMonthlyInterest()));
				txtBalance.setText(String.format("%.2f", sa.getSavingsBalance()));
			}
		});
		btnCalcInterest.setBounds(262, 159, 168, 23);
		frame.getContentPane().add(btnCalcInterest);

		JLabel lblAnnualInterestRate = new JLabel("Annual Rt");
		lblAnnualInterestRate.setBounds(344, 70, 86, 14);
		frame.getContentPane().add(lblAnnualInterestRate);

		txtAnnualRate = new JTextField();
		txtAnnualRate.setBounds(344, 86, 62, 20);
		frame.getContentPane().add(txtAnnualRate);
		txtAnnualRate.setColumns(10);

		JLabel lblAccountNumber = new JLabel("Acct Number");
		lblAccountNumber.setBounds(362, 15, 68, 14);
		frame.getContentPane().add(lblAccountNumber);

		txtAcctNum = new JTextField();
		txtAcctNum.setBounds(362, 29, 62, 20);
		frame.getContentPane().add(txtAcctNum);
		txtAcctNum.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				/*
				 * for 10 points extra credit, open the file from a FileChooser
				 * object
				 */
				SavingsAccountList.readAccountFile();
			}
		});
		mntmOpen.setIcon(new ImageIcon(MainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		mnFile.add(mntmOpen);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (SavingsAccountList.saList.size() == 0)
					JOptionPane.showMessageDialog(frame, "The list is empty!", "Woops...", JOptionPane.ERROR_MESSAGE);
				else
					SavingsAccountList.writeAccountFile();
			}
		});
		mntmSave.setIcon(new ImageIcon(MainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * for 10 points extra credit, save the file with a FileChooser
				 * object
				 */
				SavingsAccountList.writeAccountFileSaveAs();
			}
		});
		mnFile.add(mntmSaveAs);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		mntmExit.setIcon(new ImageIcon(MainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				About.main(null);
			}
		});
		mntmAbout.setIcon(new ImageIcon(MainGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnHelp.add(mntmAbout);
	}// end initialize()

	/*
	 * button state when file IS read upon program launch OR when new account is
	 * added
	 */
	private void setDefaultButtonState()
	{
		btnNew.setEnabled(true);
		btnSave.setEnabled(false);
		btnDeposit.setEnabled(true);
		btnWithdraw.setEnabled(true);
		btnCalcInterest.setEnabled(true);
		txtBalance.setEditable(false);
		txtAnnualRate.setEditable(true);
	}

	/*
	 * button state when file IS NOT read upon program launch OR when new
	 * account button is pressed
	 */
	private void setNewButtonState()
	{
		btnNew.setEnabled(true);
		btnSave.setEnabled(true);
		btnDeposit.setEnabled(false);
		btnWithdraw.setEnabled(false);
		btnCalcInterest.setEnabled(false);
		txtBalance.setEditable(true);
		txtAnnualRate.setEditable(true);
	}

	/*
	 * loads account combobox from savings account list when file is read
	 */
	private void loadAccountComboBox()
	{
		// add the account number for each account in the list
		for (SavingsAccount sa : SavingsAccountList.saList)
			cbAcctNum.addItem(sa.getAccountNumber());
		// select the index of the first item in the list
		cbAcctNum.setSelectedIndex(0);
		// update the text fields with selected item's data
		updateTextFields(0);
	}

	/*
	 * clear the text fields when the new button is pressed
	 */
	private void clearTextFields()
	{
		txtName.setText("");
		txtAcctNum.setText("");
		txtBalance.setText("");
		txtMonthlyInterest.setText("");
		txtAnnualRate.setText("");
	}

	/*
	 * update the text fields with data from the item selected in the combobox
	 */
	private void updateTextFields(int idx)
	{
		// get the savings account from the list
		SavingsAccount sa = SavingsAccountList.saList.get(idx);
		// update the name text field
		txtName.setText(sa.getSaverName());
		// update the balance text field
		txtBalance.setText(String.format("%.2f", sa.getSavingsBalance()));
		// update the annual rate field
		txtAnnualRate.setText(String.format("%.2f", sa.getAnnualInterestRate()));
	}

}// end class MainGUI
