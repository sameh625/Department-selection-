import javax.swing.*;
import java.awt.*;

public abstract class Department extends JDialog {
    protected String email;

    public Department(CS frame, String email) {
        this.email = email;
    }

    protected abstract double getRequiredGpa();
    protected abstract void showDepartment();
}

class CS1 extends Department {
    public CS1(CS frame, String email) {
        super(frame, email);
    }

    @Override
    protected double getRequiredGpa() {
        return 3.0;
    }

    @Override
    protected void showDepartment() {
        dispose();
        CS cs1 = new CS(null, email);
        cs1.setVisible(true);
    }
}

class IT1 extends Department {
    public IT1(CS frame, String email) {
        super(frame, email);
    }

    @Override
    protected double getRequiredGpa() {
        return 2.8;
    }

    @Override
    protected void showDepartment() {
        dispose();
        IT it1 = new IT(null, email);
        it1.setVisible(true);
    }
}

class IS1 extends Department {
    public IS1(CS frame, String email) {
        super(frame, email);
    }

    @Override
    protected double getRequiredGpa() {
        return 2.5;
    }

    @Override
    protected void showDepartment() {
        dispose();
        IS is1 = new IS(null, email);
        is1.setVisible(true);
    }
}