package pack;
import javax.swing.*;

public class Frame extends JFrame {
    public Frame(Faritra[] faritraArr) {
        setTitle("Saisie des votes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1910, 1070);
        setLocationRelativeTo(null);
        setContentPane(new Formulaire(faritraArr, this));
        setVisible(true);
    }
}