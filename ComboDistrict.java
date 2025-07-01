package pack;
import javax.swing.*;

public class ComboDistrict extends JComboBox<District> {
    public ComboDistrict() {
        super();
    }
    public ComboDistrict(District[] items) {
        super(items);
    }
    public void updateItems(District[] items) {
        removeAllItems();
        if (items != null) {
            for (District d : items) addItem(d);
        }
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}