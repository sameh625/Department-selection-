import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JDialog {
    private JPanel loginPanel;
    private JLabel loginLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton registerButton;

    public Login(JFrame frame){
        super(frame);
        setTitle("LOGIN NOW");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if(email.equals("admin")&&password.equals("admin")){
                    dispose();
                    admin a1=new admin(null);
                    a1.setVisible(true);
                }else {
                    getUser();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Registration r1 = new Registration(null);
            }
        });
        setVisible(true);
    }

    private void getUser() {
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter all fields", "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        final String DBURL = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            boolean isLoginSuccessful = resultSet.next();

            resultSet.close();
            statement.close();
            conn.close();

            if (isLoginSuccessful) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                Student s1 = new Student(null,email);
                s1.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again later.");
        }
    }

    public static void main(String[] args) {
        Login login = new Login(null);
    }


}
