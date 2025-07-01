package pack;
import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel(String faritra, String faritany, String district, String bureau, String candidat, int voix) {
        setLayout(new GridLayout(0,1,5,5));
        add(new JLabel("Faritra : " + faritra));
        add(new JLabel("Faritany : " + faritany));
        add(new JLabel("District : " + district));
        add(new JLabel("Bureau : " + bureau));
        add(new JLabel("Candidat : " + candidat));
        add(new JLabel("Voix : " + voix));
    }
}