import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;



public class Student extends JDialog {

    private JPanel Student;
    private JButton selectButton;
    private JComboBox<String> comboBox1;
    private JLabel gpa;
    private JTextField StudentName;
    private JTextField StudentGpa;
    private JButton logoutButton;
    private String email;

    public Student(CS frame, String email) {
        super(frame);
        this.email = email;
        setTitle("Student");
        setContentPane(Student);
        setMinimumSize(new Dimension(450, 450));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        final String DBURL = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT name,gpa FROM users WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StudentName.setText(rs.getString("name"));
                StudentGpa.setText(rs.getString("gpa"));
            } else {
                JOptionPane.showMessageDialog(this, "No data found for this email");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String GPA;
                final String DBURL = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";

                Connection conn = null;

                try {
                    conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
                    PreparedStatement ps = conn.prepareStatement("SELECT gpa,name FROM users WHERE email = ?");
                    ps.setString(1, email);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        GPA = rs.getString("gpa");
                        double Sgpa = Double.parseDouble(GPA);
                        Department department = null;
                        if (comboBox1.getSelectedItem().equals("CS")) {
                            department = new CS1(null, email);
                        } else if (comboBox1.getSelectedItem().equals("IT")) {
                            department = new IT1(null, email);
                        } else if (comboBox1.getSelectedItem().equals("IS")) {
                            department = new IS1(null, email);
                        }

                        if (department != null) {
                            double requiredGpa = department.getRequiredGpa();
                            if (Sgpa >= requiredGpa) {
                                department.showDepartment();
                            } else {
                                JOptionPane.showMessageDialog(null, "Sorry, you can't select this Department");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Department selected");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No GPA found for this email");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
}
