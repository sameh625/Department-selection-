import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Registration extends JDialog {
    private JLabel registerLabel;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JPasswordField Confirm;
    private JLabel confirmationLabel;
    private JButton registerButton;
    private JButton resetButton;
    private JPanel registerPanel;
    private JButton loginButton;
    private JTextField GpaField;

    public Registration(JFrame frame) {
        super(frame);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                registerUser();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                Confirm.setText("");
                GpaField.setText("");
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login(null);
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = usernameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmation = String.valueOf(Confirm.getPassword());
        String gpa=String.valueOf(GpaField.getText());



        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmation.isEmpty()||gpa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter all fields", "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmation)) {
            JOptionPane.showMessageDialog(this, "Confirmation password doesn't match", "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double test=Double.parseDouble(gpa);

        if(test<0.0||test>4.0)
        {
            JOptionPane.showMessageDialog(this,  " please enter valid gpa", " try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUsertoDatabase(name, email, password,test);
        if (user != null) {
            JOptionPane.showMessageDialog(null, "User " + user.name + " Successfully added", " Successful", JOptionPane.INFORMATION_MESSAGE);
            usernameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            Confirm.setText("");
            GpaField.setText("");
        }



    }

    public User user;

    private User addUsertoDatabase(String name, String email, String password,double gpa) {
        User user = null;
        final String DBURL = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

            String sql = "INSERT INTO users(name, email, password,gpa)" + "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setDouble(4, gpa);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.password = password;
                user.gpa= gpa;
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return user;

    }


}
