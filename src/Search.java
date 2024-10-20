import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
public class Search extends JDialog{
    private JPanel AdminPanel;
    private JTextField EmailField;
    private JTextField NameField;
    private JTextField GpaField;
    private JPasswordField PassField;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField IDField;
    private JButton searchButton;
    private JPanel searchPanel;
    private JButton backButton;

    public Search(JFrame frame ) {
    super(frame);
    connect();
    setTitle("Search & Modify ");
    setContentPane(searchPanel);
    setMinimumSize(new Dimension(700, 700));
    setModal(true);
    setLocationRelativeTo(frame);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }

        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
update();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
      delete();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                admin a1=new admin(null);
                a1.setVisible(true);
            }
        });

    }
   Connection conn ;
    PreparedStatement ps ;
    ResultSet rs ;
    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/new","root","");
        } catch (Exception e ) {
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again later.");
        }

    }
    public void search()  {
        try {
            int id = Integer.parseInt(IDField.getText());
            ps = conn.prepareStatement("SELECT id, name, email, password, gpa FROM users WHERE id = ?");
            ps.setInt(1, id);
             rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Sorry, Record Is Not Found");
                NameField.setText("");
                EmailField.setText("");
                PassField.setText("");
                GpaField.setText("");
                IDField.requestFocus();
            } else {
                NameField.setText(rs.getString("name"));
                EmailField.setText(rs.getString("email"));
                PassField.setText(rs.getString("password"));
                GpaField.setText(rs.getString("gpa"));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    void update() {
    try{
int id = Integer.parseInt(IDField.getText()) ;
String name = NameField.getText() ;
String email = EmailField.getText() ;
String password = PassField.getText();
double gpa=Double.parseDouble(GpaField.getText());


        if (gpa < 0.0 || gpa > 4.0) {
            JOptionPane.showMessageDialog(this, " please enter valid gpa", " try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
ps = conn.prepareStatement("UPDATE users set name =? , email=? , password =? , gpa=? WHERE id=?");
ps.setString(1,name);
ps.setString(2,email);
ps.setString(3,password);
ps.setDouble(4,gpa);
ps.setString(5, String.valueOf(id));
ps.executeUpdate() ;
JOptionPane.showMessageDialog(this,"Updated Successfully");
    }
    catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    void delete() {
        try{
            int id = Integer.parseInt(IDField.getText()) ;
            String name = NameField.getText() ;
            String email = EmailField.getText() ;
            String password = PassField.getText();
            double gpa=Double.parseDouble(GpaField.getText());
            ps = conn.prepareStatement("DELETE from  users  WHERE id=?");
            ps.setString(1, String.valueOf(id));
            ps.executeUpdate() ;
            JOptionPane.showMessageDialog(this,"Deleted Successfully");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }


