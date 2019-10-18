import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/*
Author: Ziqi Tan
*/
public class CustomerMenu extends JPanel implements ActionListener {
	
	private JTextField checking;
	private JTextField saving;
	private JTextField euro;
	private JTextField cny;
	
	private OperationFrame operationFrame;
	private ATM atm;
	
	public CustomerMenu(OperationFrame operationFrame, ATM atm) {
		this.operationFrame = operationFrame;
		this.atm = atm;
		setLayout(null);
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/3*2;
		int y = windowHeight/7;
		int increment = 50;
				
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(x, y+increment, 100, 25);
        add(withdrawButton);
        withdrawButton.addActionListener(this);
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(x, y+increment*2, 100, 25);
        add(depositButton);
        depositButton.addActionListener(this);
        
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(x, y+increment*3, 100, 25);
        add(transferButton);
        transferButton.addActionListener(this);
        
        JButton loanButton = new JButton("Loan");
        loanButton.setBounds(x, y+increment*4, 100, 25);
        add(loanButton);
        loanButton.addActionListener(this);
        
        JButton inquiryButton = new JButton("Inquiry");
        inquiryButton.setBounds(x, y+increment*5, 100, 25);
        add(inquiryButton);
        inquiryButton.addActionListener(this);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(x, y+increment*6, 100, 25);
        add(logoutButton);
        logoutButton.addActionListener(this);
           
        x = windowWidth/6;
        y = windowHeight/4;
        checking = new JTextField("Checking Account: ");
        checking.setEditable(false);
        checking.setBounds(x, y, 200, 25);
		add(checking);
		
		saving = new JTextField("Saving Account: ");
		saving.setEditable(false);
		saving.setBounds(x, y+50, 200, 25);
		add(saving);
		
		euro = new JTextField("Euro Account: ");
		euro.setEditable(false);
		euro.setBounds(x, y+100, 200, 25);
		add(euro);
		
		cny = new JTextField("CNY Account: ");
		cny.setEditable(false);
		cny.setBounds(x, y+150, 200, 25);
		add(cny);
		
		JButton foreignCurrency = new JButton("Foreign Currency");
		foreignCurrency.setBounds(x, y+200, 200, 25);
        add(foreignCurrency);
        foreignCurrency.addActionListener(this);
        
	}
	
	/*
	 * Method: updateInfo
	 * Function: update the information on the customer panel, 
	 * 			including balance in all accounts.
	 * */
	public void updateInfo() {
		
		Customer cus = operationFrame.getCustomer();
		DecimalFormat df = new DecimalFormat(".00");
		
		double checkingBalance = cus.getCheckingAccount().getBalance();
		checking.setText("Checking Account: " + df.format(checkingBalance) + " USD");
		
		double savingBalance = cus.getSavingAccount().getBalance();
		saving.setText("Saving Account: " + df.format(savingBalance) + " USD");
		
		double euroBalance = cus.getEuroAccount().getBalance();
		euro.setText("Euro Account: " + df.format(euroBalance) + " EUR");
		
		double cnyBalance = cus.getCNYAccount().getBalance();
		cny.setText("CNY Account: " + df.format(cnyBalance) + " CNY");
	}
	
	/**
	 * Method: actionPerformed
	 * ActionListener for buttons: 
	 * 1. Withdraw;
	 * 2. Deposit;
	 * 3. Transfer;
	 * 4. Loan;
	 * 5. Inquiry;
	 * 6. Logout.
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// ActionListener for Withdraw button
		if( e.getActionCommand().equals("Withdraw") ) {
			try {
				String inputValue = JOptionPane.showInputDialog("Withdraw:");
				Double withdraw = Double.parseDouble(inputValue);
				Customer cus = operationFrame.getCustomer();
				double checkingBalance = cus.getCheckingAccount().getBalance();
				if( checkingBalance < withdraw ) {
					System.out.println("Not enought balance!");
					JOptionPane.showMessageDialog(null,"Not enough balance!");
				}
				else {
					System.out.println("Withdraw " + withdraw + " dallors!");
					JOptionPane.showMessageDialog(null, "Withdraw " + withdraw + " dallors!");
					atm.getCustomerDAOImp().withdraw(cus, -withdraw);
					updateInfo();				
				}

			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}
		} // Withdraw
		
		// ActionListener for Deposit button
		if( e.getActionCommand().equals("Deposit") ) {
			try {
				Object[] possibleValues = { "Checking", "Saving" };
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
				"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);

				System.out.println("You choose " + (String)selectedValue + " Account.");
				
				String choice = (String)selectedValue;
				Customer cus = operationFrame.getCustomer();
				
				if( choice.equals("Checking") ) {
					String inputValue = JOptionPane.showInputDialog("Deposit:");
					Double deposit = Double.parseDouble(inputValue);					
					JOptionPane.showMessageDialog(null, "Deposit " + deposit + " dallors!");
					atm.getCustomerDAOImp().deposit(cus, deposit, "CHECKING");
					updateInfo();
				}
				
				if( choice.equals("Saving") ) {
					String inputValue = JOptionPane.showInputDialog("Deposit:");
					Double deposit = Double.parseDouble(inputValue);					
					JOptionPane.showMessageDialog(null, "Deposit " + deposit + " dallors!");
					atm.getCustomerDAOImp().deposit(cus, deposit, "SAVING");
					updateInfo();
				}
				
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}				
		} // Deposit
		
		// ActionListerner for Transfer
		if( e.getActionCommand().equals("Transfer") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setTransferPanel();
		} // Transfer
		
		// ActionListener for Loan
		if( e.getActionCommand().equals("Loan") ) {
			try {
				String inputValue = JOptionPane.showInputDialog("Applying for a loan:");
				Double deposit = Double.parseDouble(inputValue);
				JOptionPane.showMessageDialog(null,"Your application has been submitted!");
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}		
		} // Loan
		
		// ActionListener for Inquiry
		if( e.getActionCommand().equals("Inquiry") ) {
			
			Object[] possibleValues = { "Checking", "Saving" ,"Euro", "CNY"};
			Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
			"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
			possibleValues[0]);

			System.out.println("You choose " + (String)selectedValue + " Account.");
							
			try {
				Customer cus = operationFrame.getCustomer();			
				List<List<String>> trans = atm.getCustomerDAOImp().inquiry(cus, (String)selectedValue);
				
				JFrame frame = new JFrame((String)selectedValue + " account transaction history");
		        frame.setSize(800,300);
		        frame.setLocationRelativeTo(null);

		        Container contentPane=frame.getContentPane();
		        Object[][] tableDate = new Object[trans.size()][trans.get(0).size()];
		        
		        for( int i = 0; i < trans.size(); i++ ) {		            
		            for( int j = 0; j < trans.get(0).size(); j++ ) {
		                tableDate[i][j] = trans.get(i).get(j);
		            }
		        }
		        String[] name = {"Time", "Sender", "Recipient", "Amount" };
		        JTable table = new JTable(tableDate,name);
		        contentPane.add(new JScrollPane(table));
		        frame.setVisible(true);
			}
			catch(Exception error) {
				System.out.println(error);
			}
					
		} // Inquiry
		
		// ActionListener for Logout
		if( e.getActionCommand().equals("Logout") ) {
			operationFrame.setCustomer(null);
			
			setEnabled(false);
			setVisible(false);
			operationFrame.setLoginPanel();
			
			JOptionPane.showMessageDialog(null,"Logout successfully!");
			
		} // Logout
		
		// ActionListener for Foreign Currency
		// Change to foreign currency panel
		if( e.getActionCommand().equals("Foreign Currency") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setForeignCurrencyPanel();
		}
		
	}
}
