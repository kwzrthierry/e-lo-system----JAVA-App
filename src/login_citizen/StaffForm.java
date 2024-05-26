package login_citizen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class StaffForm implements ActionListener{

	 static final String JDBC_DRIVER = "your_jdbc_driver";
	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
	String User = "222003408";
	String Pass = "222003408";
	JFrame staffF;
	JLabel wellb = new JLabel("Staff Portal");
	JLabel userName = new JLabel("Username: ");
	JLabel passW = new JLabel("Password: ");

	JTextField usertxf = new JTextField();
	JPasswordField passWtxf = new JPasswordField();

	JButton login = new JButton("LogIn");
	JButton reset = new JButton("Reset");
	public StaffForm () {
		createform();
		setlocationandsize();
		addcomponent();
		setupActionListeners();
	}
	private void createform() {
		staffF = new JFrame();
		staffF.setTitle("Staff Portal");
		staffF.setBounds(600,140,500,350);
		staffF.getContentPane().setLayout(null);
		staffF.getContentPane().setBackground(Color.white);
		staffF.setVisible(true);
		staffF.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		staffF.setResizable(true);

	}
	private void setlocationandsize() {
		wellb.setBounds(195, 60, 200, 30);

		userName.setBounds(50, 110, 80, 30);
		usertxf.setBounds(140, 110, 250, 30);

		passW.setBounds(50, 150, 80, 30);
		passWtxf.setBounds(140, 150, 250, 30);

		login.setBounds(150, 250, 80, 30);
		reset.setBounds(250, 250, 80, 30);
	}
	private void addcomponent() {
		staffF.add(wellb);
		staffF.add(userName);
		staffF.add(login);
		staffF.add(passW);
		staffF.add(reset);
		staffF.add(passWtxf);
		staffF.add(usertxf);

	}
	private void setupActionListeners() {
	    login.addActionListener(this);
	    reset.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String Usern = usertxf.getText();
            String Passw = passWtxf.getText();

            // Validate credentials by querying the database
            try (Connection conn = DriverManager.getConnection(url, User, Pass)) {
                String sql = "SELECT * FROM login_data_staff WHERE Username=? AND Password=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, Usern);
                    pstmt.setString(2, Passw);
                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                    	staffF.dispose();
                    	new staffloggedin(Usern);
                    	} else {
                        // Login failed
                        JOptionPane.showMessageDialog(null, "Invalid username or password!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == reset) {
        	usertxf.setText("");
        	passWtxf.setText("");
        }
        // Handle other button clicks if needed
	}
	public static void main(String[] args) {
		new StaffForm();

	}
}
