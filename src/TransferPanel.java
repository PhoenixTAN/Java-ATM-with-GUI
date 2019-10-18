import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class TransferPanel extends JPanel implements ActionListener {
	
	private OperationFrame operationFrame;
	private ATM atm;
	
	public TransferPanel(OperationFrame operationFrame, ATM atm) {
		this.operationFrame = operationFrame;
		this.atm = atm;
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/4;
		int increment = 50;
		
		setLayout(null);
		
		JButton transferBetweenYourAccounts = new JButton("Transfer between your accounts");
		transferBetweenYourAccounts.setBounds(x, y+increment, 300, 25);
        add(transferBetweenYourAccounts);
        transferBetweenYourAccounts.addActionListener(this);
        
        JButton transferToOthers = new JButton("Transfer to others");
        transferToOthers.setBounds(x, y+increment*2, 300, 25);
        add(transferToOthers);
        transferToOthers.addActionListener(this);
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*3, 300, 25);
        add(returnButton);
        returnButton.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().contentEquals("Return") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setCustomerMenu();
		}
		
		if( e.getActionCommand().contentEquals("Transfer between your accounts") ) {
			
			// Option 1: transfer from checking account to saving account
			// Option 2: transfer from saving account to checking account
			Object[] possibleValues = { "from checking to saving", "from saving to checking" };
			Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
			"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
			possibleValues[0]);
			String choice = (String)selectedValue;
			System.out.println("You choose " + choice );
			
			Customer cus = operationFrame.getCustomer();
			
			// Transfer from checking account to saving account
			try {
				if( choice.equals("from checking to saving") ) {
					String inputValue = JOptionPane.showInputDialog("Amount:");
					double balance = cus.getCheckingAccount().getBalance();
					double amount = Double.parseDouble(inputValue);
					if( balance < amount ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						// Transfer successfully.
						JOptionPane.showMessageDialog(null, "Transfer " + amount + " to saving account!");
						atm.getCustomerDAOImp().transferBetweenYourAccount(cus, amount, "CHECKING");	
					}		
				}							
			}
			catch(Exception error ) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}
			
			// Transfer from saving account to checking account
			try {
				if( choice.equals("from saving to checking") ) {
					String inputValue = JOptionPane.showInputDialog("Amount:");
					double balance = cus.getSavingAccount().getBalance();
					double amount = Double.parseDouble(inputValue);
					if( balance < amount ) {
						JOptionPane.showMessageDialog(null, "Not enough money!");
					}
					else {
						// Transfer successfully.
						JOptionPane.showMessageDialog(null, "Transfer " + amount + " to checking account!");
						atm.getCustomerDAOImp().transferBetweenYourAccount(cus, amount, "SAVING");	
					}		
				}							
			}
			catch(Exception error ) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Please input a number!");
			}
		}
		
		if( e.getActionCommand().contentEquals("Transfer to others") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setTransferWindow();
		}
		
		
	}

}
