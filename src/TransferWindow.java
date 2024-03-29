import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
Author: Ziqi Tan
*/
public class TransferWindow extends JPanel implements ActionListener {
	
	private OperationFrame operationFrame;
	private ATM atm;
	
	private JTextField recipientText;
	private JTextField amountText;
	
	public TransferWindow(OperationFrame operationFrame, ATM atm) {
		
		this.operationFrame = operationFrame;
		this.atm = atm;
		
		setLayout(null);
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/7;
		int increment = 50;
		
		JLabel recipient = new JLabel("Recipient");
		recipient.setBounds(x, y, 80, 25);
		add(recipient);
		
		recipientText = new JTextField();
		recipientText.setBounds(x+100, y, 200, 25);
		add(recipientText);
		
		JLabel amount = new JLabel("Amount");
		amount.setBounds(x, y+increment*1, 80, 25);
		add(amount);
		
		amountText = new JTextField();
		amountText.setBounds(x+100, y+increment*1, 80, 25);
		add(amountText);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(x, y+increment*2, 80, 25);
		add(submit);
		submit.addActionListener(this);
		
		JButton returnButton = new JButton("Return");
		returnButton.setBounds(x+100, y+increment*2, 80, 25);
		add(returnButton);
		returnButton.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().equals("Return") ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setTransferPanel();
		}
		
		if( e.getActionCommand().equals("Submit") ) {
			// Check whether there is enough money and whether there is such a customer
			// Check the input format
			try {				
				Customer sender = operationFrame.getCustomer();
				Customer recipient = this.atm.getCustomerDAOImp().getCustomer(recipientText.getText());
				double amount = Double.parseDouble(amountText.getText());
				if( amount > sender.getCheckingAccount().getBalance() ) {
					JOptionPane.showMessageDialog(null,"Not enough money!");
				}
				else {
					this.atm.getCustomerDAOImp().transfer(sender, recipient, amount);
					JOptionPane.showMessageDialog(null,"Transfer successfully!");
					setEnabled(false);
					setVisible(false);
					operationFrame.setTransferPanel();
				}				
			}
			catch(NullPointerException error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"No such a customer!");
			}
			catch(NumberFormatException error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null,"Input a number!");
			}
					
		}
		
	}
}
