import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class CS extends JDialog  {
    private JButton cancelButton1;
    private JButton confirmButton;
    private JPanel CS;
    private JLabel shit;
    private String email;

    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public CS(JFrame frame, String email) {
        super(frame);
        this.email = email;
        setTitle("CS");
        setContentPane(CS);
        setMinimumSize(new Dimension(450, 350));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        shit.setText(convertToMultiline("Computer Science (CS):\nFocuses on theoretical and practical aspects of:-\n computation, algorithms, programming languages, and software development.\n\nand you will study the following:-\n-Software Engineering-1\n-Database Systems-1\n-Computer Language-1\n-Operating Systems-2\n\n"));
        pack();
        cancelButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Student s2 = new Student(CS.this, email);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final String DBURL = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";

                try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
                    String sql = "UPDATE users SET dep = ? WHERE email = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, "CS");
                    preparedStatement.setString(2, email);

                    int updatedRows = preparedStatement.executeUpdate();
                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Welcome to the CS Department");
                        dispose();
                        Student s1 = new Student(null, email);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Email not found");
                    }
                } catch (Exception f) {
                    f.printStackTrace();
                }
                dispose();
                Student s2 = new Student(CS.this, email);
            }
        });
    }
}
