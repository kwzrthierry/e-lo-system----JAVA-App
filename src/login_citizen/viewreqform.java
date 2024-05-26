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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class viewreqform implements ActionListener {
    JFrame viewReq;
    static final String JDBC_DRIVER = "your_jdbc_driver";
   	String url = "jdbc:mysql://localhost:3306/kwizera_thierry_elms";
   	String UserN = "222003408";
   	String PassD = "222003408";

    JLabel wellbl = new JLabel("View Response");
    JLabel reqID = new JLabel("Enter the request ID: ");
    JTextField entertxf = new JTextField();
    JButton view = new JButton("View Response");

    public viewreqform() {
        createform();
        setlocationandsize();
        addcomponent();
        setupActionListeners();
    }

    private void createform() {
        viewReq = new JFrame();
        viewReq.setTitle("E-lO System");
        viewReq.setBounds(580, 140, 500, 310);
        viewReq.getContentPane().setLayout(null);
        viewReq.getContentPane().setBackground(Color.white);
        viewReq.setVisible(true);
        viewReq.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewReq.setResizable(false);
    }

    private void setlocationandsize() {
        wellbl.setBounds(200, 40, 200, 35);
        reqID.setBounds(55, 90, 125, 35);
        entertxf.setBounds(180, 90, 200, 35);
        view.setBounds(180, 150, 130, 35);
    }

    private void addcomponent() {
        viewReq.add(wellbl);
        viewReq.add(reqID);
        viewReq.add(entertxf);
        viewReq.add(view);
    }

    private void setupActionListeners() {
        view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view) {
            String requestID = entertxf.getText();
            if (!requestID.isEmpty()) {
                fetchDataFromDatabase(requestID);
                entertxf.setText("");
            } else {
                // Handle the case where the request ID is empty
                System.out.println("Please enter a valid request ID");
            }
        }
    }

    private void fetchDataFromDatabase(String requestID) {
        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, UserN, PassD);

            // Prepare the SQL query
            String query = "SELECT Status FROM requests WHERE Request_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, requestID);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the result set
                if (resultSet.next()) {
                    // Retrieve data from the result set
                    String responseData = resultSet.getString("Status");

                    // Display the response data in a popup
                    JOptionPane.showMessageDialog(viewReq, "Status: " + responseData,
                            "View Response", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    // Handle the case where no data is found for the given request ID
                    JOptionPane.showMessageDialog(viewReq, "No data found for the given request ID",
                            "View Response", JOptionPane.WARNING_MESSAGE);
                }
            }

            // Close the connection
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new viewreqform();
    }
}
