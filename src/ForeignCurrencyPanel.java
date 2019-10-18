import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/*
Author: Ziqi Tan
*/
public class ForeignCurrencyPanel extends JPanel implements ActionListener {
	
	private OperationFrame operationFrame;
	private ATM atm;
	
	public ForeignCurrencyPanel(OperationFrame operationFrame, ATM atm) {
		this.operationFrame = operationFrame;
		this.atm = atm;
		
		setLayout(null);
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/4;
		int y = windowHeight/7;
		int increment = 50;
		
		JLabel panelTitle = new JLabel("Foreign Currency Account");
		panelTitle.setBounds(x, y, 200, 25);
		add(panelTitle);
		
		JButton withdrawButton = new JButton("Purchase");
		withdrawButton.setBounds(x, y+increment, 200, 25);
        add(withdrawButton);
        withdrawButton.addActionListener(this);
        
        JButton depositButton = new JButton("Withdraw");
        depositButton.setBounds(x, y+increment*2, 200, 25);
        add(depositButton);
        depositButton.addActionListener(this);
        
        JButton loanButton = new JButton("Return");
        loanButton.setBounds(x, y+increment*3, 200, 25);
        add(loanButton);
        loanButton.addActionListener(this);	
	}
	
	/**
	 * Method: actionPerformed
	 * Function: Action listener for components,
	 * 			including "Purchase" button, "Withdraw" button and "Return" button.
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Action listener for "Return" button
		if( e.getActionCommand().equals("Return") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setCustomerMenu();
		}
		
		// Action listener for "Purchase" button
		if( e.getActionCommand().equals("Purchase") ) {
			try {
				// Purchase EUR or CNY
				Object[] possibleValues = { "EUR", "CNY" };
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
				"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);

				System.out.println("You choose " + (String)selectedValue + ".");
				
				String choice = (String)selectedValue;
				Customer cus = operationFrame.getCustomer();
				
				// Purchase EUR
				if( choice.equals("EUR") ) {
					String inputValue = JOptionPane.showInputDialog("EUR Amount:");
					Double purchase = Double.parseDouble(inputValue);
					double exchangeRate = atm.getCustomerDAOImp().getEURRate();
					double checkingBalance = cus.getCheckingAccount().getBalance();
					// Enough money in checking account?
					if( purchase/exchangeRate > checkingBalance ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Purchase " + purchase + " EUR!");
						atm.getCustomerDAOImp().purchaseEUR(cus, purchase);
					}								
				}
				
				// Purchase CNY
				if( choice.equals("CNY") ) {
					String inputValue = JOptionPane.showInputDialog("CNY Amount:");
					Double purchase = Double.parseDouble(inputValue);
					double exchangeRate = atm.getCustomerDAOImp().getCNYRate();
					double checkingBalance = cus.getCheckingAccount().getBalance();
					// Enough money in checking account?
					if( purchase/exchangeRate > checkingBalance ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Purchase " + purchase + " CNY!");
						atm.getCustomerDAOImp().purchaseCNY(cus, purchase);
					}								
				}
				
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}				
		}
		
		// Action lister for "Withdraw" button
		if( e.getActionCommand().equals("Withdraw") ) {
			try {
				// Withdraw from EUR account or CNY account
				Object[] possibleValues = { "EUR", "CNY" };
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
				"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);

				System.out.println("You choose " + (String)selectedValue + ".");
				
				String choice = (String)selectedValue;
				Customer cus = operationFrame.getCustomer();
				
				// Withdraw EUR
				if( choice.equals("EUR") ) {
					String inputValue = JOptionPane.showInputDialog("Withdraw:");
					Double withdraw = Double.parseDouble(inputValue);
					double euroBalance = cus.getEuroAccount().getBalance();
					if( withdraw > euroBalance ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Withdraw " + withdraw + " EUR!");
						atm.getCustomerDAOImp().withdrawEUR(cus, withdraw);
					}								
				}
				
				// Withdraw CNY
				if( choice.equals("CNY") ) {
					String inputValue = JOptionPane.showInputDialog("Withdraw:");
					Double withdraw = Double.parseDouble(inputValue);
					double cnyBalance = cus.getCNYAccount().getBalance();
					if( withdraw > cnyBalance ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Withdraw " + withdraw + " CNY!");
						atm.getCustomerDAOImp().withdrawCNY(cus, withdraw);
					}								
				}
				
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}	
		}
		
		
	}

}
