package login_citizen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class loggedcitizenmakerequestform implements ActionListener {
    JFrame loggedcREQ;
    static final String JDBC_DRIVER = "your_jdbc_driver";
   	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
   	String UserN = "222003408";
   	String PassD = "222003408";
    private static int citizenId; // New field to store the citizen_id

    JLabel yourREQ = new JLabel("Enter Your Request:");
    JTextArea ytxf = new JTextArea();
    JButton sendbtn = new JButton("Send");

    public loggedcitizenmakerequestform(int citizenId) {
        loggedcitizenmakerequestform.citizenId = citizenId; // Set the citizen_id
        createform();
        setlocationandsize();
        addcomponent();
        setupActionListeners();
    }



	private void createform() {
        loggedcREQ = new JFrame();
        loggedcREQ.setTitle("Request");
        loggedcREQ.setBounds(600, 140, 430, 380);
        loggedcREQ.getContentPane().setLayout(null);
        loggedcREQ.getContentPane().setBackground(Color.white);
        loggedcREQ.setVisible(true);
        loggedcREQ.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loggedcREQ.setResizable(false);
    }

    private void setlocationandsize() {
        yourREQ.setBounds(50, 50, 150, 30);
        ytxf.setBounds(50, 90, 300, 180);
        ytxf.setLineWrap(true);
        sendbtn.setBounds(150, 280, 100, 30);
    }

    private void addcomponent() {
        loggedcREQ.add(yourREQ);
        loggedcREQ.add(ytxf);
        loggedcREQ.add(sendbtn);
    }

    private void setupActionListeners() {
        sendbtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendbtn) {
            String requestText = ytxf.getText();

            // Validate and send the request to the database
            if (!requestText.isEmpty()) {
                sendRequestToDatabase(requestText);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid request.");
            }
        }
    }

    private void sendRequestToDatabase(String requestText) {
        try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
            String sql = "INSERT INTO requests (Citizen_id, Request, Date, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, citizenId);
                pstmt.setString(2, requestText);
                pstmt.setTimestamp(3, getCurrentTimestamp());
                pstmt.setString(4, "processing");
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Request sent successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error sending request. Please try again.");
        }
    }

    private Timestamp getCurrentTimestamp() {
        // Get the current date and time
        return new Timestamp(new Date().getTime());
    }
    private static int retrieveCitizenId() {
        return citizenId;
    }

    public static void main(String[] args) {
    	int retrievedCitizenId = retrieveCitizenId();
        new loggedcitizenmakerequestform(retrievedCitizenId);
    }
}
