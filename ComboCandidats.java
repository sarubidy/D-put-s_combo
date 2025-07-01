package pack;
import javax.swing.*;

public class ComboCandidats extends JComboBox<Candidats> {
    public ComboCandidats() {
        super();
    }
    public ComboCandidats(Candidats[] items) {
        super(items);
    }
    public void updateItems(Candidats[] items) {
        removeAllItems();
        if (items != null) {
            for (Candidats c : items) addItem(c);
        }
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}