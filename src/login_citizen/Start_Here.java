package login_citizen;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Start_Here implements ActionListener {

	JFrame logFm;
	JLabel wellb = new JLabel("WELCOME TO E-Lo system");

	JButton citbtn = new JButton("Citizen");
	JButton sbtn = new JButton("Staff");

	public Start_Here() {
		createform();
		setlocationandsize();
		addcomponent();
		ActionEvent();
	}

	private void createform() {
		logFm = new JFrame();
		logFm.setTitle("E-LO System");
		logFm.setBounds(600,140,500,350);
		logFm.getContentPane().setLayout(null);
		logFm.getContentPane().setBackground(Color.white);
		logFm.setVisible(true);
		logFm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		logFm.setResizable(false);

	}
	private void setlocationandsize() {
		wellb.setBounds(170, 60, 200, 30);
		citbtn.setBounds(195, 140, 80, 30);
		sbtn.setBounds(195, 190, 80, 30);
	}
	private void addcomponent() {
		logFm.add(wellb);
		logFm.add(citbtn);
		logFm.add(sbtn);
	}
	private void ActionEvent() {
		citbtn.addActionListener(this);
		sbtn.addActionListener(this);
	}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (e.getSource() == citbtn) {
            // Open CitizenForm when Citizen button is clicked
            logFm.dispose(); // Close the Start_Here frame
            new CitizenForm(); // Open CitizenForm
        }
		if (e.getSource() == sbtn) {
            // Open CitizenForm when Citizen button is clicked
            logFm.dispose(); // Close the Start_Here frame
            new StaffForm(); // open StaffForm
        }
	}

	public static void main(String[] args) {
        new Start_Here();

	}

}

