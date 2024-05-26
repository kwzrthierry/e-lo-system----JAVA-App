package login_citizen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class staffloggedin implements ActionListener{
	JFrame staffkI;

	 static final String JDBC_DRIVER = "your_jdbc_driver";
	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
	String UserN = "222003408";
	String PassD = "222003408";

	JLabel wellslb = new JLabel("welcome to e-lo system staff portal");

	JButton viewrecReq = new JButton("View Received Request");
	JButton provideRE = new JButton("Provide Response");
	JButton viewDat = new JButton("View Citizen Data");
	JButton logOut = new JButton("LogOUT");
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel citizenIdLabel = new JLabel("Citizen ID: ");
	String Usern;


	public staffloggedin (String Usern) {
		this.Usern = Usern;
		createform();
		setlocationandsize();
		addcomponent();
		setupActionListeners();
		setUserDataLabels();
	}


	private void createform() {
		staffkI = new JFrame();
		staffkI.setTitle("staff portal");
		staffkI.setBounds(550,140,540,450);
		staffkI.getContentPane().setLayout(null);
		staffkI.getContentPane().setBackground(Color.white);
		staffkI.setVisible(true);
		staffkI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		staffkI.setResizable(false);

	}


	private void setlocationandsize() {
		wellslb.setBounds(155, 70, 300, 35);
		viewrecReq.setBounds(130, 120, 250, 35);
		viewDat.setBounds(130, 175, 250, 35);
		provideRE.setBounds(130, 230, 250, 35);
		logOut.setBounds(210, 300, 90, 30);
		usernameLabel.setBounds(20, 20, 200, 20);
        citizenIdLabel.setBounds(20, 40, 200, 20);

	}


	private void addcomponent() {
		staffkI.add(viewrecReq);
		staffkI.add(viewDat);
		staffkI.add(logOut);
		staffkI.add(wellslb);
		staffkI.add(provideRE);
		staffkI.add(usernameLabel);
        staffkI.add(citizenIdLabel);

	}


	private void setupActionListeners() {
        viewrecReq.addActionListener(this);
		viewDat.addActionListener(this);
		provideRE.addActionListener(this);
		logOut.addActionListener(this);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == logOut) {
            staffkI.dispose();
            JOptionPane.showMessageDialog(null, "you were logged out. bye!");
        }
		if (e.getSource() == viewrecReq) {
            viewReceivedRequests();
        }
		if (e.getSource() == viewDat) {
            viewCitizenData();
        }
		if (e.getSource() == provideRE) {
            provideResponse();
        }
    }
	private void provideResponse() {
        // Prompt for Request ID
        String requestIDInput = JOptionPane.showInputDialog(null, "Enter Request ID:");

        // Check if the input is not null (i.e., the user didn't cancel)
        if (requestIDInput != null) {
            try {
                // Parse the input as an integer
                int requestID = Integer.parseInt(requestIDInput);

                // Create a text area for the response
                JTextArea responseTextArea = new JTextArea();
                responseTextArea.setLineWrap(true);
                responseTextArea.setWrapStyleWord(true);

                // Create a scroll pane for the text area
                JScrollPane scrollPane = new JScrollPane(responseTextArea);

                // Show the input dialog with the text area
                int option = JOptionPane.showOptionDialog(
                        null,
                        scrollPane,
                        "Provide Response",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null
                );

                // Check if the user clicked "OK"
                if (option == JOptionPane.OK_OPTION) {
                    // Get the response text from the text area
                    String responseText = responseTextArea.getText();

                    // Insert the response into the requests table
                    try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
                        String sql = "UPDATE requests SET response = ? WHERE Request_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, responseText);
                            pstmt.setInt(2, requestID);

                            int rowsAffected = pstmt.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Response added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to add response. Request ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Request ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	private void viewCitizenData() {
        // Prompt for Citizen ID
        String citizenIdInput = JOptionPane.showInputDialog(null, "Enter Citizen ID:");
        
        // Check if the input is not null (i.e., the user didn't cancel)
        if (citizenIdInput != null) {
            try {
                // Parse the input as an integer
                int citizenId = Integer.parseInt(citizenIdInput);

                // Fetch and display citizen data
                try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
                    String sql = "SELECT * FROM citizen WHERE citizen_id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, citizenId);
                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) {
                            int fetchedCitizenId = resultSet.getInt("citizen_id");
                            String firstName = resultSet.getString("first_name");
                            String lastName = resultSet.getString("last_name");
                            String phoneNumber = resultSet.getString("phone_number");
                            String email = resultSet.getString("email");
                            String birthDate = resultSet.getString("birth_date");
                            String nationalId = resultSet.getString("national_id");
                            String address = resultSet.getString("address");
                            String maritalStatus = resultSet.getString("marital_status");

                            // Display the results in a popup
                            StringBuilder resultText = new StringBuilder();
                            resultText.append("Citizen ID: ").append(fetchedCitizenId).append("\n");
                            resultText.append("First Name: ").append(firstName).append("\n");
                            resultText.append("Last Name: ").append(lastName).append("\n");
                            resultText.append("Phone Number: ").append(phoneNumber).append("\n");
                            resultText.append("Email: ").append(email).append("\n");
                            resultText.append("Birth Date: ").append(birthDate).append("\n");
                            resultText.append("National ID: ").append(nationalId).append("\n");
                            resultText.append("Address: ").append(address).append("\n");
                            resultText.append("Marital Status: ").append(maritalStatus).append("\n");

                            JOptionPane.showMessageDialog(null, resultText.toString(), "Citizen Data", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Citizen ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Citizen ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


	private void viewReceivedRequests() {
        int staffId = getstaffIdFromDatabase(Usern);

        if (staffId != -1) {
            try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
                String sql = "SELECT Request_id, Request, Date, Citizen_id FROM requests WHERE Staff_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, staffId);
                    ResultSet resultSet = pstmt.executeQuery();

                    StringBuilder resultText = new StringBuilder();
                    while (resultSet.next()) {
                        int requestId = resultSet.getInt("Request_id");
                        String requestText = resultSet.getString("Request");
                        String date = resultSet.getString("Date");
                        int citizenId = resultSet.getInt("Citizen_id");

                        // Append the results to the StringBuilder
                        resultText.append("Request ID: ").append(requestId).append("\n");
                        resultText.append("Request: ").append(requestText).append("\n");
                        resultText.append("Date: ").append(date).append("\n");
                        resultText.append("Citizen ID: ").append(citizenId).append("\n");
                        resultText.append("-----------------------").append("\n");
                    }

                    // Display the results in a popup
                    JOptionPane.showMessageDialog(null, resultText.toString(), "Received Requests", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error fetching staff information.");
        }
    }

	
	private int getstaffIdFromDatabase(String username) {
        int staffId = -1;
        try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
            String sql = "SELECT Staff_id FROM login_data_staff WHERE Username=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    staffId = resultSet.getInt("Staff_id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return staffId;
    }
	private void setUserDataLabels() {
        usernameLabel.setText("Username: " + Usern);
        int staffId = getstaffIdFromDatabase(Usern);
        citizenIdLabel.setText("Staff ID: " + staffId);
    }
	public static void main (String[] args) {
		new staffloggedin("Username");
	}
}
