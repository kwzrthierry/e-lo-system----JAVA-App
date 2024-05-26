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

public class CitizenForm implements ActionListener{

	static final String JDBC_DRIVER = "your_jdbc_driver";
	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
	String UserN = "222003408";
	String PassD = "222003408";
	JFrame staffF;
	int loggedID;
	JLabel wellb = new JLabel("Citizen Portal");
	JLabel userName = new JLabel("Username: ");
	JLabel passW = new JLabel("Password: ");

	JTextField usertxf = new JTextField();
	JPasswordField passWtxf = new JPasswordField();

	JButton login = new JButton("LogIn");
	JButton signUp = new JButton("SignUp");
	JButton reset = new JButton("Reset");
	public CitizenForm () {
		createform();
		setlocationandsize();
		addcomponent();
		setupActionListeners();
	}
	private void createform() {
		staffF = new JFrame();
		staffF.setTitle("Citizen Portal");
		staffF.setBounds(600,140,500,350);
		staffF.getContentPane().setLayout(null);
		staffF.getContentPane().setBackground(Color.white);
		staffF.setVisible(true);
		staffF.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		staffF.setResizable(false);

	}
	private void setlocationandsize() {
		wellb.setBounds(195, 60, 200, 30);

		userName.setBounds(50, 110, 80, 30);
		usertxf.setBounds(140, 110, 250, 30);

		passW.setBounds(50, 150, 80, 30);
		passWtxf.setBounds(140, 150, 250, 30);

		login.setBounds(100, 250, 80, 30);
		reset.setBounds(200, 250, 80, 30);
		signUp.setBounds(300, 250, 80, 30);
	}
	private void addcomponent() {
		staffF.add(wellb);
		staffF.add(userName);
		staffF.add(login);
		staffF.add(passW);
		staffF.add(reset);
		staffF.add(signUp);
		staffF.add(passWtxf);
		staffF.add(usertxf);

	}
	private void setupActionListeners() {
	    login.addActionListener(this);
	    signUp.addActionListener(this);
	    reset.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String Username = usertxf.getText();
            String Password = passWtxf.getText();

            // Validate credentials by querying the database
            try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
                String sql = "SELECT * FROM login_citizen WHERE Username=? AND Password=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, Username);
                    pstmt.setNString(2, Password);
                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                    	staffF.dispose(); // Close the citizen frame
                        new Logged_citizen(Username); // Open loggedForm
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
            // Code to clear text fields
            usertxf.setText(""); // Clear username field
            passWtxf.setText(""); // Clear password field
        }
        if (e.getSource() == signUp) {
        	staffF.dispose(); // Close the citizen frame
            new SignupForm(); // Open CitizenForm
        }
        // Handle other button clicks if needed
    }
}
