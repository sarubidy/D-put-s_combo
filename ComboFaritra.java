package pack;
import javax.swing.*;

public class ComboFaritra extends JComboBox<Faritra> {
    public ComboFaritra() {
        super();
    }
    public ComboFaritra(Faritra[] items) {
        super(items);
    }
    public void updateItems(Faritra[] items) {
        removeAllItems();
        if (items != null) {
            for (Faritra f : items) addItem(f);
        }
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}