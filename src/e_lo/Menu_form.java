package e_lo;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Menu_form{
	JFrame frame;
	JLabel systemlb = new JLabel("e-lo System");

	JButton Staff = new JButton("Staff");
	JButton Citizen = new JButton("Citizen");
	JButton Report = new JButton("Report");
	JButton Requests = new JButton("Requests");
	JButton Login_data_staff = new JButton("Login_data_staff");
	JButton Login_citizen = new JButton("Start_Here");
	JButton Updates_on_citizen = new JButton("Updates_on_citizen");
	JButton Deleted_citizen = new JButton("Deleted_citizen");
	JButton Deleted_staff = new JButton("Deleted_staff");

	public Menu_form() {
		createform();
		setlocationandsize();
		addcomponent();
	}

	private void createform() {
		frame = new JFrame();
		frame.setTitle("Menu of all tables in E-LO System");
		frame.setBounds(600,140,480,530);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}


	private void addcomponent() {
		frame.add(systemlb);
		frame.add(Citizen);
		frame.add(Deleted_citizen);
		frame.add(Deleted_staff);
		frame.add(Login_citizen);
		frame.add(Login_data_staff);
		frame.add(Report);
		frame.add(Requests);
		frame.add(Staff);
		frame.add(Updates_on_citizen);

	}

	private void setlocationandsize() {
		systemlb.setBounds(195, 20, 80, 40);
		Citizen.setBounds(160, 50, 140, 35);
		Deleted_citizen.setBounds(160, 95, 140, 35);
		Deleted_staff.setBounds(160, 140, 140, 35);
		Login_citizen.setBounds(160, 185, 140, 35);
		Login_data_staff.setBounds(160, 230, 140, 35);
		Report.setBounds(160, 275, 140, 35);
		Requests.setBounds(160, 320, 140, 35);
		Staff.setBounds(160, 365, 140, 35);
		Updates_on_citizen.setBounds(150, 410, 160, 35);

	}
	public static void main(String[] args) {
		new Menu_form();

	}

}
