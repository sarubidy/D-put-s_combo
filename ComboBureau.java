import javax.swing.*;

public class ComboBureau extends JComboBox<Bureau> {
    public ComboBureau() {
        super();
    }
    public ComboBureau(Bureau[] items) {
        super(items);
    }
    public void updateItems(Bureau[] items) {
        removeAllItems();
        if (items != null) {
            for (Bureau b : items) addItem(b);
        }
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}