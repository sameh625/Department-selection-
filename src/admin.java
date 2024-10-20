import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class admin extends JDialog {

    private JTextField EmailField;
    private JTextField NameField;
    private JTextField PassField;
    private JButton addButton;
    private JPanel AdminPanel;
    private JTextField GpaField;
    private JButton searchButton;
    private JButton logoutButton;

    public admin(JFrame frame) {
        super(frame);
        setTitle("admin");
        setContentPane(AdminPanel);
        setMinimumSize(new Dimension(700, 700));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(null);
            }
        });
    }
    private void registerUser () {
        String name = NameField.getText();
        String email = EmailField.getText();
        String password = PassField.getText();
        String gpa = String.valueOf(GpaField.getText());


        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || gpa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter all fields", "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }


        double test = Double.parseDouble(gpa);

        if (test < 0.0 || test > 4.0) {
            JOptionPane.showMessageDialog(this, " please enter valid gpa", " try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUsertoDatabase(name, email, password, test);
        if (user != null) {
            JOptionPane.showMessageDialog(null, "User " + user.name + " Successfully added", " Successful", JOptionPane.INFORMATION_MESSAGE);
            NameField.setText("");
            EmailField.setText("");
            PassField.setText("");
            GpaField.setText("");
        }


    }
    public User user;
    private User addUsertoDatabase (String name, String email, String password,double gpa){
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
                user.gpa = gpa;
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return user;

    }
   void Search() {
        dispose();
        Search search = new Search(null) ;
        search.setVisible(true);
   }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}