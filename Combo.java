import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.function.Function;
public class Combo<T> extends JComboBox<T> {
    public Combo() { super(); }
    public Combo(T[] items) {
        super();
        updateItems(java.util.Arrays.asList(items));
    }
    public Combo(java.util.List<T> items) {
        super();
        updateItems(items);
    }
    public void updateItems(java.util.List<T> items) {
        removeAllItems();
        if (items != null) for (T i : items) addItem(i);
        if (getItemCount() > 0) setSelectedIndex(0);
    }
}
