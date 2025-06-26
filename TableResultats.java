import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TableResultats extends JPanel {
    ComboFaritra comboFaritra;
    ComboFaritany comboFaritany;
    ComboDistrict comboDistrict;
    ComboBureau comboBureau;
    JTable table;
    DefaultTableModel model;
    Faritra[] faritraArr;
    List<ResultatPanel.Vote> votes;

    public TableResultats(Faritra[] faritraArr) {
        this.faritraArr = faritraArr;
        this.votes = chargerVotes();

        setLayout(new BorderLayout());

        JPanel filtrePanel = new JPanel(new FlowLayout());
        comboFaritra = new ComboFaritra();
        comboFaritany = new ComboFaritany();
        comboDistrict = new ComboDistrict();
        comboBureau = new ComboBureau();

        comboFaritra.addItem(null);
        for (Faritra f : faritraArr) comboFaritra.addItem(f);
        comboFaritra.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) value = "Tous";
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        comboFaritany.addItem(null);
        comboFaritany.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) value = "Tous";
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        comboDistrict.addItem(null);
        comboDistrict.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) value = "Tous";
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        comboBureau.addItem(null);
        comboBureau.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) value = "Tous";
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        comboFaritra.addActionListener(e -> updateFaritany());
        comboFaritany.addActionListener(e -> updateDistrict());
        comboDistrict.addActionListener(e -> updateBureau());
        comboBureau.addActionListener(e -> majTableau());

        filtrePanel.add(new JLabel("Faritra:"));
        filtrePanel.add(comboFaritra);
        filtrePanel.add(new JLabel("Faritany:"));
        filtrePanel.add(comboFaritany);
        filtrePanel.add(new JLabel("District:"));
        filtrePanel.add(comboDistrict);
        filtrePanel.add(new JLabel("Bureau:"));
        filtrePanel.add(comboBureau);

        add(filtrePanel, BorderLayout.NORTH);

        // Colonnes : Faritra, Faritany, District, Bureau, Candidat, Voix
        model = new DefaultTableModel(new Object[]{"Faritra", "Faritany", "District", "Bureau", "Candidat", "Voix"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        updateFaritany();
        majTableau();
    }

    public void updateFaritany() {
        comboFaritany.removeAllItems();
        comboFaritany.addItem(null);
        Object selectedFaritra = comboFaritra.getSelectedItem();
        if (selectedFaritra instanceof Faritra) {
            Faritra f = (Faritra) selectedFaritra;
            for (Faritany ft : f.list_faritany) comboFaritany.addItem(ft);
        }
        updateDistrict();
    }

    public void updateDistrict() {
        comboDistrict.removeAllItems();
        comboDistrict.addItem(null);
        Object selectedFaritany = comboFaritany.getSelectedItem();
        if (selectedFaritany instanceof Faritany) {
            Faritany ft = (Faritany) selectedFaritany;
            for (District d : ft.list_district) comboDistrict.addItem(d);
        }
        updateBureau();
    }

    public void updateBureau() {
        comboBureau.removeAllItems();
        comboBureau.addItem(null);
        Object selectedDistrict = comboDistrict.getSelectedItem();
        if (selectedDistrict instanceof District) {
            District d = (District) selectedDistrict;
            for (Bureau b : d.list_bureau) comboBureau.addItem(b);
        }
        majTableau();
    }

    // Recharge le tableau selon les filtres + option Tous
    public void majTableau() {
        model.setRowCount(0);
        Object faritraSel = comboFaritra.getSelectedItem();
        Object faritanySel = comboFaritany.getSelectedItem();
        Object districtSel = comboDistrict.getSelectedItem();
        Object bureauSel = comboBureau.getSelectedItem();

        for (Faritra faritra : faritraArr) {
            if (faritraSel instanceof Faritra && faritraSel != null && faritra != faritraSel) continue;
            for (Faritany faritany : faritra.list_faritany) {
                if (faritanySel instanceof Faritany && faritanySel != null && faritany != faritanySel) continue;
                for (District district : faritany.list_district) {
                    if (districtSel instanceof District && districtSel != null && district != districtSel) continue;
                    for (Bureau bureau : district.list_bureau) {
                        if (bureauSel instanceof Bureau && bureauSel != null && bureau != bureauSel) continue;
                        // Pour chaque vote dans ce bureau
                        for (ResultatPanel.Vote v : votes) {
                            if (v.bureau.equals(bureau.nom)) {
                                model.addRow(new Object[]{
                                    faritra.nom,
                                    faritany.nom,
                                    district.nom,
                                    bureau.nom,
                                    v.candidat,
                                    v.voix
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    public List<ResultatPanel.Vote> chargerVotes() {
        List<ResultatPanel.Vote> votes = new ArrayList<>();
        File f = new File("votes.txt");
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";;");
                    if (parts.length == 4) {
                        votes.add(new ResultatPanel.Vote(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                    }
                }
            } catch (Exception ex) {
                
            }
        }
        return votes;
    }
}