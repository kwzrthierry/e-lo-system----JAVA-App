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
import javax.swing.WindowConstants;

public class Logged_citizen implements ActionListener {
    JFrame loggedFM;
    static final String JDBC_DRIVER = "your_jdbc_driver";
   	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
   	String UserN = "222003408";
   	String PassD = "222003408";

    JLabel usernameLabel = new JLabel("Username: ");
    JLabel citizenIdLabel = new JLabel("Citizen ID: ");
    JLabel welllb = new JLabel("E-lo System Citizen Portal");
    JButton makeREQ = new JButton(" Make a request ");
    JButton viewREQ = new JButton(" View request Status/response ");
    JButton viewSTAFFass = new JButton("View staff assigned");
    JButton LogOUT = new JButton("Logout");
    JButton delete = new JButton("Delete Account");
    String Username;

    public Logged_citizen(String Username) {
        this.Username = Username;
        createform();
        setlocationandsize();
        addcomponent();
        setupActionListeners();
        setUserDataLabels();
    }

    private void createform() {
        loggedFM = new JFrame();
        loggedFM.setTitle("logged in citizen portal");
        loggedFM.setBounds(600, 140, 500, 350);
        loggedFM.getContentPane().setLayout(null);
        loggedFM.getContentPane().setBackground(Color.white);
        loggedFM.setVisible(true);
        loggedFM.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loggedFM.setResizable(false);
    }

    private void setlocationandsize() {
        usernameLabel.setBounds(20, 20, 200, 20);
        citizenIdLabel.setBounds(20, 40, 200, 20);
        welllb.setBounds(165, 70, 200, 35);
        makeREQ.setBounds(150, 120, 175, 35);
        viewREQ.setBounds(130, 170, 220, 35);
        viewSTAFFass.setBounds(130, 220, 220, 35);
        LogOUT.setBounds(120, 270, 110, 35);
        delete.setBounds(240, 270, 120, 35);
    }

    private void addcomponent() {
        loggedFM.add(LogOUT);
        loggedFM.add(makeREQ);
        loggedFM.add(viewREQ);
        loggedFM.add(viewSTAFFass);
        loggedFM.add(welllb);
        loggedFM.add(delete);
        loggedFM.add(usernameLabel);
        loggedFM.add(citizenIdLabel);
    }

    private void setupActionListeners() {
        LogOUT.addActionListener(this);
        makeREQ.addActionListener(this);
        viewREQ.addActionListener(this);
        viewSTAFFass.addActionListener(this);
        delete.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == makeREQ) {
            int citizenId = getCitizenIdFromDatabase(Username);
            loggedFM.dispose();
            new loggedcitizenmakerequestform(citizenId);
        }
        if (e.getSource() == viewREQ) {
        	loggedFM.dispose();
            new viewreqform();

        }
        if (e.getSource() == viewSTAFFass) {
            int citizenId = getCitizenIdFromDatabase(Username);
            StaffInfo staffInfo = getStaffInfoAssignedToCitizen(citizenId);

            if (staffInfo != null) {
                String staffNameAndEmail = staffInfo.getFullNameAndEmail();
                JOptionPane.showMessageDialog(loggedFM, "Staff Assigned: " + staffNameAndEmail,
                        "View Staff Assigned", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loggedFM, "No staff assigned to you",
                        "View Staff Assigned", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == delete) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                int citizenId = getCitizenIdFromDatabase(Username);
                if (deleteAccountFromDatabase(citizenId)) {
                    JOptionPane.showMessageDialog(null, "Your account has been deleted successfully. Goodbye!");
                    loggedFM.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete your account. Please try again.");
                }
            }
        }
        if (e.getSource() == LogOUT) {
            loggedFM.dispose();
            JOptionPane.showMessageDialog(null, "you were logged out. bye!");
        }
    }

    private int getCitizenIdFromDatabase(String username) {
        int citizenId = -1;
        try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
            String sql = "SELECT Citizen_id FROM login_citizen WHERE Username=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    citizenId = resultSet.getInt("citizen_id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizenId;
    }
    private boolean deleteAccountFromDatabase(int citizenId) {
        try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
            // Delete from login_citizen table
            String deleteLoginCitizenSQL = "DELETE FROM login_citizen WHERE Citizen_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteLoginCitizenSQL)) {
                pstmt.setInt(1, citizenId);
                pstmt.executeUpdate();
            }

            // Delete from citizen table
            String deleteCitizenSQL = "DELETE FROM citizen WHERE Citizen_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteCitizenSQL)) {
                pstmt.setInt(1, citizenId);
                pstmt.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void setUserDataLabels() {
        usernameLabel.setText("Username: " + Username);
        int citizenId = getCitizenIdFromDatabase(Username);
        citizenIdLabel.setText("Citizen ID: " + citizenId);
    }

    private StaffInfo getStaffInfoAssignedToCitizen(int citizenId) {
        StaffInfo staffInfo = null;
        try (Connection conn = DriverManager.getConnection(url, UserN, PassD)) {
            String sql = "SELECT s.First_name, s.Last_name, s.Email " +
                         "FROM requests r " +
                         "JOIN staff s ON r.Staff_id = s.Staff_id " +
                         "WHERE r.Citizen_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, citizenId);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String firstName = resultSet.getString("First_name");
                    String lastName = resultSet.getString("Last_name");
                    String email = resultSet.getString("Email");
                    staffInfo = new StaffInfo(firstName, lastName, email);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return staffInfo;
    }

    private static class StaffInfo {
        private final String firstName;
        private final String lastName;
        private final String email;

        public StaffInfo(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public String getFullNameAndEmail() {
            return firstName + " " + lastName + " (" + email + ")";
        }
    }

    public static void main(String[] args) {
        new Logged_citizen("Username");
    }
}
