import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
Author: Ziqi Tan
*/
public class ManagerPanel extends JPanel implements ActionListener {
	
	private OperationFrame operationFrame;
	private ATM atm;
	
	private JTextArea report;
	private JScrollPane jsp;
	
	public ManagerPanel(OperationFrame operationFrame, ATM atm) {
		this.operationFrame = operationFrame;
		this.atm = atm;
		
		JButton submit = new JButton("Get Report");
		add(submit);
		submit.addActionListener(this);
		
		JButton returnButton = new JButton("Logout");
		add(returnButton);
		returnButton.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getActionCommand().equals("Logout") ) {
			remove(report);
			remove(jsp);
			setEnabled(false);
			setVisible(false);
			operationFrame.setLoginPanel();
			JOptionPane.showMessageDialog(null,"Logout successfully!");
			
		}
		
		if( e.getActionCommand().equals("Get Report") ) {
			try { 
				remove(report);
				remove(jsp);
			}
			catch(Exception error) {
				System.out.println(error);
			}
			
			report = new JTextArea();
			String str = "";
			List<Customer> customers = this.atm.getCustomerDAOImp().getAllCustomers();
			ListIterator iter = customers.listIterator();
			report.setLineWrap(true);
			report.setFont(new Font("", Font.BOLD, 13));
		
			report.append("Get all transaction history:\n");
			while( iter.hasNext() ) {
				Customer cus = (Customer) iter.next();
				String name = cus.getName();				
				report.append(name+"\n");
				
				report.append("Checking Account:\n");
				List<List<String>> transHis = cus.getCheckingAccount().getTransHis();
				ListIterator iter_transHis = transHis.listIterator();
				while( iter_transHis.hasNext() ) {
					List<String> trans = (List<String>) iter_transHis.next();
					report.append(trans.toString()+"\n");
				}
				report.append("\n");
				
				report.append("Saving Account:\n");
				transHis = cus.getSavingAccount().getTransHis();
				iter_transHis = transHis.listIterator();
				while( iter_transHis.hasNext() ) {
					List<String> trans = (List<String>) iter_transHis.next();
					report.append(trans.toString()+"\n");
				}
				report.append("\n");
				
				report.append("Euro Account:\n");
				transHis = cus.getEuroAccount().getTransHis();
				iter_transHis = transHis.listIterator();
				while( iter_transHis.hasNext() ) {
					List<String> trans = (List<String>) iter_transHis.next();
					report.append(trans.toString()+"\n");
				}
				report.append("\n");
				
				report.append("CNY Account:\n");
				transHis = cus.getCNYAccount().getTransHis();
				iter_transHis = transHis.listIterator();
				while( iter_transHis.hasNext() ) {
					List<String> trans = (List<String>) iter_transHis.next();
					report.append(trans.toString()+"\n");
				}
				report.append("\n");
				
				// System.out.println(name);
			}
	         
	        report.setEditable(false);
	        report.setBounds(10, 200, 450, 250);
	        jsp = new JScrollPane(report);
	        jsp.setBounds(15, 35, 450, 400);
	        add(jsp);
		}
		
	}

}
