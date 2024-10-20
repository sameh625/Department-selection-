import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class IT extends JDialog  {
    private JPanel IT;
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel shit;

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public IT(JFrame frame,String email){
        super(frame);
        setTitle("IT");
        setContentPane(IT);
        setMinimumSize(new Dimension(340, 350));
        setModal(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        shit.setText(convertToMultiline("IT departments are responsible for maintaining\n the hardware and software systems within an organization.\nThis includes configuring and updating software applications\n employee devices, servers, databases, and other IT infrastructure\n\nand you will study the following:-\n-Computer Networks-1\n-Network Operating Systems\n-Network Programming\n-Network Security\n"));
        pack();

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
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
                    preparedStatement.setString(1, "IT");
                    preparedStatement.setString(2, email);

                    int updatedRows = preparedStatement.executeUpdate();
                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Welcome to the IT Department");
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
