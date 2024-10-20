import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class IS extends JDialog{
    private JPanel IS;
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel shit;

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public IS(JFrame frame,String email){
        super(frame);
        setTitle("Student");
        setContentPane(IS);
        setMinimumSize(new Dimension(350, 350));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        shit.setText(convertToMultiline("Concentrates on the study of information systems\nand their use in business and organizations.\nIt includes topics like business processes, data analytics,\nand enterprise resource planning (ERP) systems.\n\nand you will study the following:-\n-Database Systems2\n-Business Intelligence\n-Information Retrieval\n-Intelligent IS\n"));
        pack();
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Student s1=new Student(null,email);
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
                    preparedStatement.setString(1, "IS");
                    preparedStatement.setString(2, email);

                    int updatedRows = preparedStatement.executeUpdate();
                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Welcome to the IS Department");
                        dispose();
                        Student s1=new Student(null,email);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Email not found");
                    }
                } catch (Exception f) {
                    f.printStackTrace();
                }
                dispose();
                Student s1=new Student(null,email);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Student s1=new Student(null,email);
            }
        });
    }
}
