package pack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Formulaire extends JPanel
{
    ComboFaritra comboFaritra;
    ComboFaritany comboFaritany;
    ComboDistrict comboDistrict;
    ComboBureau comboBureau;
    ComboCandidats comboCandidat;
    JTextField txtVotes;
    JTextField txtColistier;

    public Formulaire(Faritra[] faritraArr, JFrame parentFrame)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        comboFaritra = new ComboFaritra(faritraArr);
        comboFaritany = new ComboFaritany();
        comboDistrict = new ComboDistrict();
        comboBureau = new ComboBureau();
        comboCandidat = new ComboCandidats();
        txtVotes = new JTextField(6);
        txtColistier = new JTextField(18);
        txtColistier.setVisible(false);

        comboFaritra.addActionListener(e -> updateFaritany());
        comboFaritany.addActionListener(e -> updateDistrict());
        comboDistrict.addActionListener(e -> updateBureau());
        comboBureau.addActionListener(e -> updateCandidat());
        comboCandidat.addActionListener(e -> updateColistier());

        updateFaritany();

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Faritra:"), gbc);
        gbc.gridx = 1; add(comboFaritra, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Faritany:"), gbc);
        gbc.gridx = 1; add(comboFaritany, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("District:"), gbc);
        gbc.gridx = 1; add(comboDistrict, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Bureau:"), gbc);
        gbc.gridx = 1; add(comboBureau, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Candidats:"), gbc);
        gbc.gridx = 1; add(comboCandidat, gbc); y++;
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Voix:"), gbc);
        gbc.gridx = 1; add(txtVotes, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        JButton btnOk = new JButton("OK");
        add(btnOk, gbc);
        gbc.gridx = 1;
        JButton btnSupprimer = new JButton("Supprimer les sauvegardes");
        add(btnSupprimer, gbc);
        gbc.gridx = 2;
        JButton btnConfirmer = new JButton("Confirmer");
        add(btnConfirmer, gbc);
        y++;
        JButton btnTableau = new JButton("Tableau");
        gbc.gridx = 3;
        add(btnTableau, gbc);

        btnOk.addActionListener(e -> {
            Bureau bureau = (Bureau) comboBureau.getSelectedItem();
            Candidats candidat = (Candidats) comboCandidat.getSelectedItem();
            String votesStr = txtVotes.getText().trim();
            if (bureau == null || candidat == null || votesStr.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Champs manquants!", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int votes;
            try {
                votes = Integer.parseInt(votesStr);
            } catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Nombre de voix invalide!", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ligne = candidat.nom + ";;" + candidat.nom2 + ";;" + bureau.nom + ";;" + votes;
            try (FileWriter fw = new FileWriter("votes.txt", true))
            {
                fw.write(ligne + "\n");
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "Erreur écriture votes.txt", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            txtVotes.setText("");
        });

        btnSupprimer.addActionListener(e -> {
            File f = new File("votes.txt");
            if (f.exists()) f.delete();
            JOptionPane.showMessageDialog(this, "Toutes les sauvegardes supprimées !");
        });

        btnConfirmer.addActionListener(e -> {
            JFrame resFrame = new JFrame("Résultats");
            resFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resFrame.setSize(1000, 700);
            resFrame.setLocationRelativeTo(parentFrame);
            resFrame.setContentPane(new ResultatPanel(faritraArr));
            resFrame.setVisible(true);
        });
        
        btnTableau.addActionListener(e -> {
        JFrame tableFrame = new JFrame("Résultats (Tableau)");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(1000, 700);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setContentPane(new TableResultats(faritraArr));
        tableFrame.setVisible(true);
});
    }

    public void updateFaritany()
    {
        comboFaritany.removeAllItems();
        Faritra f = (Faritra) comboFaritra.getSelectedItem();
        if (f != null)
        {
            for (Faritany ft : f.list_faritany) comboFaritany.addItem(ft);
        }
        updateDistrict();
    }
    public void updateDistrict()
    {
        comboDistrict.removeAllItems();
        Faritany ft = (Faritany) comboFaritany.getSelectedItem();
        if (ft != null)
        {
            for (District d : ft.list_district) comboDistrict.addItem(d);
        }
        updateBureau();
    }
    public void updateBureau()
    {
        comboBureau.removeAllItems();
        District d = (District) comboDistrict.getSelectedItem();
        if (d != null)
        {
            for (Bureau b : d.list_bureau) comboBureau.addItem(b);
        }
        updateCandidat();
    }
    public void updateCandidat()
    {
        comboCandidat.removeAllItems();
        District d = (District) comboDistrict.getSelectedItem();
        if (d != null)
        {
            java.util.Set<Candidats> candidatsDistrict = new java.util.LinkedHashSet<>();
            for (Bureau b : d.list_bureau)
            {
                for (Candidats c : b.list_candidats)
                {
                    candidatsDistrict.add(c);
                }
            }
            for (Candidats c : candidatsDistrict) comboCandidat.addItem(c);
        }
        updateColistier();
    }
    public void updateColistier()
    {
        Candidats candidat = (Candidats) comboCandidat.getSelectedItem();
        if (candidat != null)
        {
            txtColistier.setText(candidat.nom2);
        } else {
            txtColistier.setText("");
        }
    }
}