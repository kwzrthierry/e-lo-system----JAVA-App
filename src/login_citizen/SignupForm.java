package login_citizen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SignupForm  implements ActionListener{

	JFrame signupframe;
	 static final String JDBC_DRIVER = "your_jdbc_driver";
	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
	String UserN = "222003408";
	String PassD = "222003408";

	JLabel signup = new JLabel("SignUp-Portal");

	JLabel fnamelb = new JLabel("First Name");
	JLabel lnamelb = new JLabel("Last Name");
	JLabel pnumberlb = new JLabel("Phone-number");
	JLabel emaillb = new JLabel("Email");
	JLabel doblb = new JLabel("Date of Birth");
	JLabel nidlb = new JLabel("National ID");
	JLabel addresslb = new JLabel("Address");
	JLabel maritalstatuslb = new JLabel("Marital Status");
	JLabel userNamelb = new JLabel("Username");
	JLabel passwordlb = new JLabel("Password");

	JTextField fnametxf = new JTextField();
	JTextField lnametxf = new JTextField();
	JTextField pnumbertxf = new JTextField();
	JTextField emailtxf = new JTextField();
	JTextField dobtxf = new JTextField();
	JTextField nidtxf = new JTextField();
	JTextField addresstxf = new JTextField();
	JTextField maritalstatustxf = new JTextField();
	JTextField userNametxf = new JTextField();
	JPasswordField passwordtxf = new JPasswordField();

	JButton SignUpbtn = new JButton("SignUp");
	JLabel format = new JLabel("Date of birth format: [YY-MM-DD]");

	public SignupForm () {
		createform();
		setlocationandsize();
		addcomponent();
		setupActionListeners();
	}

	private void createform() {
		signupframe = new JFrame();
		signupframe.setTitle("Sign-Up Portal");
		signupframe.setBounds(550,130,500,650);
		signupframe.getContentPane().setLayout(null);
		signupframe.getContentPane().setBackground(Color.white);
		signupframe.setVisible(true);
		signupframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		signupframe.setResizable(false);


	}

	private void setlocationandsize() {

		signup.setBounds(180, 50, 90, 30);
		fnamelb.setBounds(50, 100, 90, 30);
		lnamelb.setBounds(50, 140, 90, 30);
		pnumberlb.setBounds(50, 180, 90, 30);
		emaillb.setBounds(50, 220, 90, 30);
		doblb.setBounds(50, 260, 90, 30);
		nidlb.setBounds(50, 300, 90, 30);
		addresslb.setBounds(50, 340, 90, 30);
		maritalstatuslb.setBounds(50, 380, 90, 30);
		userNamelb.setBounds(50, 420, 90, 30);
		passwordlb.setBounds(50, 460, 90, 30);

		fnametxf.setBounds(170, 100, 250, 30);
		lnametxf.setBounds(170, 140, 250, 30);
		pnumbertxf.setBounds(170, 180, 250, 30);
		emailtxf.setBounds(170, 220, 250, 30);
		dobtxf.setBounds(170, 260, 250, 30);
		nidtxf.setBounds(170, 300, 250, 30);
		addresstxf.setBounds(170, 340, 250, 30);
		maritalstatustxf.setBounds(170, 380, 250, 30);
		userNametxf.setBounds(170, 420, 250, 30);
		passwordtxf.setBounds(170, 460, 250, 30);
		format.setBounds(140, 500, 260, 30);

		SignUpbtn.setBounds(190, 540, 90, 30);

	}

	private void addcomponent() {

		signupframe.add(signup);

		signupframe.add(addresslb);
		signupframe.add(fnamelb);
		signupframe.add(doblb);
		signupframe.add(lnamelb);
		signupframe.add(maritalstatuslb);
		signupframe.add(nidlb);
		signupframe.add(passwordlb);
		signupframe.add(pnumberlb);
		signupframe.add(userNamelb);
		signupframe.add(emaillb);

		signupframe.add(addresstxf);
		signupframe.add(fnametxf);
		signupframe.add(dobtxf);
		signupframe.add(lnametxf);
		signupframe.add(maritalstatustxf);
		signupframe.add(nidtxf);
		signupframe.add(passwordtxf);
		signupframe.add(pnumbertxf);
		signupframe.add(userNametxf);
		signupframe.add(emailtxf);
		signupframe.add(format);
		signupframe.add(SignUpbtn);

	}

	private void setupActionListeners() {
		SignUpbtn.addActionListener(this);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SignUpbtn) {
            String firstName = fnametxf.getText();
            String lastName = lnametxf.getText();
            String phoneNumber = pnumbertxf.getText();
            String email = emailtxf.getText();
            String dob = dobtxf.getText();
            String nationalID = nidtxf.getText();
            String address = addresstxf.getText();
            String maritalStatus = maritalstatustxf.getText();
            String username = userNametxf.getText();
            String password = passwordtxf.getText();

            try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
                String checkIdQuery = "SELECT * FROM citizen WHERE National_ID = ?";
                try (PreparedStatement checkIdStmt = conn.prepareStatement(checkIdQuery)) {
                    checkIdStmt.setString(1, nationalID);
                    ResultSet idResult = checkIdStmt.executeQuery();

                    if (idResult.next()) {
                        JOptionPane.showMessageDialog(null, "National ID already in use!");
                    }
                    else{
                        String checkUsernameQuery = "SELECT * FROM login_citizen WHERE Username = ?";
                        try (PreparedStatement checkUsernameStmt = conn.prepareStatement(checkUsernameQuery)) {
                            checkUsernameStmt.setString(1, username);
                            ResultSet usernameResult = checkUsernameStmt.executeQuery();

                            if (usernameResult.next()) {
                                JOptionPane.showMessageDialog(null, "Username already in use!");
                            }
                            else {
                                String citizenSql = "INSERT INTO citizen (first_name, last_name, phone_number, email, birth_date, national_id, address, marital_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                try (PreparedStatement citizenStmt = conn.prepareStatement(citizenSql, Statement.RETURN_GENERATED_KEYS)) {
                                    citizenStmt.setString(1, firstName);
                                    citizenStmt.setString(2, lastName);
                                    citizenStmt.setString(3, phoneNumber);
                                    citizenStmt.setString(4, email);
                                    citizenStmt.setString(5, dob);
                                    citizenStmt.setString(6, nationalID);
                                    citizenStmt.setString(7, address);
                                    citizenStmt.setString(8, maritalStatus);

                                    int affectedRows = citizenStmt.executeUpdate();

                                    if (affectedRows > 0) {
                                        ResultSet generatedKeys = citizenStmt.getGeneratedKeys();
                                        if (generatedKeys.next()) {
                                            int citizenID = generatedKeys.getInt(1);

                                            String loginSql = "INSERT INTO login_citizen (Citizen_id, Username, Password) VALUES (?, ?, ?)";
                                            try (PreparedStatement loginStmt = conn.prepareStatement(loginSql)) {
                                                loginStmt.setInt(1, citizenID);
                                                loginStmt.setString(2, username);
                                                loginStmt.setString(3, password);

                                                int inserted = loginStmt.executeUpdate();

                                                if (inserted > 0) {
                                                    JOptionPane.showMessageDialog(null, "Signup Successful!");
                                                    // Optionally, you can close the signup frame or perform other actions
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Failed to insert login credentials!");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
            	JOptionPane.showMessageDialog(null, "Enter all fields");
                //ex.printStackTrace();
            }
        }
    }

	public static void main(String[] args) {
        new SignupForm();

	}

}
