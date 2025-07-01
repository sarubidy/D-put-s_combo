package pack;
import javax.swing.*;

public class ComboFaritany extends JComboBox<Faritany> {
    public ComboFaritany() {
        super();
    }
    public ComboFaritany(Faritany[] items) {
        super(items);
    }
    public void updateItems(Faritany[] items) {
        removeAllItems();
        if (items != null) {
            for (Faritany ft : items) addItem(ft);
        }
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}