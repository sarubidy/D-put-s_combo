import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.LinkedHashMap;

public class ResultatPanel extends JPanel
{
    ComboFaritra comboFaritra;
    ComboFaritany comboFaritany;
    ComboDistrict comboDistrict;
    ComboBureau comboBureau;
    JTextArea resultArea;

    Faritra[] faritraArr;
    List<Vote> votes;

    public ResultatPanel(Faritra[] faritraArr)
    {
        this.faritraArr = faritraArr;
        this.votes = chargerVotes();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        comboFaritra = new ComboFaritra(faritraArr);
        comboFaritany = new ComboFaritany();
        comboDistrict = new ComboDistrict();
        comboBureau = new ComboBureau();
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        JScrollPane scrollResults = new JScrollPane(resultArea);

        comboFaritra.addActionListener(e -> updateFaritany());
        comboFaritany.addActionListener(e -> updateDistrict());
        comboDistrict.addActionListener(e -> updateBureau());

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

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        JButton btnAfficher = new JButton("Valider");
        add(btnAfficher, gbc); y++;

        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = y+1;
        add(scrollResults, gbc);

        btnAfficher.addActionListener(e -> afficherResultats());
    }

    public void updateFaritany()
    {
        comboFaritany.removeAllItems();
        comboFaritany.addItem(null);
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
        comboDistrict.addItem(null);
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
        comboBureau.addItem(null);
        District d = (District) comboDistrict.getSelectedItem();
        if (d != null)
        {
            for (Bureau b : d.list_bureau) comboBureau.addItem(b);
        }
    }

    public List<Vote> chargerVotes()
    {
        List<Vote> votes = new ArrayList<>();
        File f = new File("votes.txt");
        if (f.exists())
        {
            try (BufferedReader br = new BufferedReader(new FileReader(f)))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    String[] parts = line.split(";;");
                    if (parts.length == 4)
                    {
                        votes.add(new Vote(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                    }
                }
            } catch (Exception ex)
            {
                // Ignorer les exceptions de lecture
            }
        }
        return votes;
    }

    public void afficherResultats()
    {
        resultArea.setText("");
        Bureau bureau = (Bureau) comboBureau.getSelectedItem();
        District district = (District) comboDistrict.getSelectedItem();
        Faritany faritany = (Faritany) comboFaritany.getSelectedItem();
        Faritra faritra = (Faritra) comboFaritra.getSelectedItem();

        if (bureau != null)
        {
            resultArea.setText(afficherResultatsBureau(bureau, district));
            return;
        }
        if (district != null)
        {
            resultArea.setText(afficherResultatsDistrict(district, true));
            return;
        }
        if (faritany != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Résultats pour Faritany ").append(faritany.nom).append(" :\n");
            for (District d : faritany.list_district)
            {
                sb.append("  District ").append(d.nom)
                  .append(" (Nb élus : ").append(d.nombre_deputes).append(")\n");
                sb.append(afficherResultatsDistrict(d, false)).append("\n");
            }
            resultArea.setText(sb.toString());
            return;
        }
        if (faritra != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Résultats pour Faritra ").append(faritra.nom).append(" :\n");
            for (Faritany ft : faritra.list_faritany)
            {
                sb.append(" Faritany ").append(ft.nom).append(" :\n");
                for (District d : ft.list_district)
                {
                    sb.append("   District ").append(d.nom)
                      .append(" (Nb élus : ").append(d.nombre_deputes).append(")\n");
                    sb.append(afficherResultatsDistrict(d, false)).append("\n");
                }
            }
            resultArea.setText(sb.toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Faritra f : faritraArr)
        {
            sb.append("Faritra ").append(f.nom).append(" :\n");
            for (Faritany ft : f.list_faritany)
            {
                sb.append(" Faritany ").append(ft.nom).append(" :\n");
                for (District d : ft.list_district)
                {
                    sb.append("   District ").append(d.nom)
                      .append(" (Nb élus : ").append(d.nombre_deputes).append(")\n");
                    sb.append(afficherResultatsDistrict(d, false)).append("\n");
                }
            }
        }
        resultArea.setText(sb.toString());
    }

    public String afficherResultatsBureau(Bureau bureau, District district)
    {
        Set<String> nomsCandidats = new LinkedHashSet<>();
        for (Bureau b : district.list_bureau)
        {
            for (Candidats c : b.list_candidats)
            {
                nomsCandidats.add(c.nom);
            }
        }
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String c : nomsCandidats) scores.put(c, 0);
        for (Vote v : votes)
        {
            if (v.bureau.equals(bureau.nom))
            {
                scores.put(v.candidat, scores.getOrDefault(v.candidat, 0) + v.voix);
            }
        }
        int maxVoix = 0;
        for (int voix : scores.values()) if (voix > maxVoix) maxVoix = voix;

        StringBuilder sb = new StringBuilder();
        if (maxVoix == 0)
        {
            sb.append("Aucun candidat n'a de voix.");
        } else {
            for (Map.Entry<String,Integer> e : scores.entrySet())
            {
                if (e.getValue() == maxVoix)
                {
                    sb.append(e.getKey()).append(" : ").append(e.getValue()).append(" voix\n");
                }
            }
        }
        return sb.toString();
    }

    public String afficherResultatsDistrict(District district, boolean affichernombre_deputes)
    {
        Set<String> nomsCandidats = new LinkedHashSet<>();
        Map<String, Candidats> mapNomCandidat = new LinkedHashMap<>();
        for (Bureau b : district.list_bureau)
        {
            for (Candidats c : b.list_candidats)
            {
                mapNomCandidat.put(c.nom, c);
                nomsCandidats.add(c.nom);
            }
        }
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String nom : nomsCandidats) scores.put(nom, 0);
        for (Bureau b : district.list_bureau)
        {
            for (Vote v : votes)
            {
                if (v.bureau.equals(b.nom)) scores.put(v.candidat, scores.getOrDefault(v.candidat, 0) + v.voix);
            }
        }
        List<Map.Entry<String, Integer>> classement = new ArrayList<>(scores.entrySet());
        classement.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        StringBuilder sb = new StringBuilder();
        if (affichernombre_deputes) sb.append("Nombre d'élus pour ce district : ").append(district.nombre_deputes).append("\n");

        if (classement.isEmpty() || (classement.size() == 1 && classement.get(0).getValue() == 0))
        {
            sb.append("Aucun candidat n'a de voix.");
        } else if (district.nombre_deputes == 1)
        {
            int maxVoix = classement.get(0).getValue();
            for (Map.Entry<String, Integer> e : classement)
            {
                if (e.getValue() == maxVoix && maxVoix >= 0) sb.append(e.getKey()).append(" : ").append(e.getValue()).append(" voix\n");
            }
        } else if (district.nombre_deputes == 2)
        {
            if (classement.size() == 1)
            {
                Candidats c = mapNomCandidat.get(classement.get(0).getKey());
                sb.append(c.nom).append(" : ").append(classement.get(0).getValue()).append(" voix\n");
                sb.append(c.nom2).append("\n");
            } else {
                int voixA = classement.get(0).getValue();
                int voixB = classement.get(1).getValue();
                String nomA = classement.get(0).getKey();
                Candidats candA = mapNomCandidat.get(nomA);
                String nomB = classement.get(1).getKey();
                Candidats candB = mapNomCandidat.get(nomB);
                if (voixA == voixB )
                {
                    if (candA.groupe != null && candB.groupe != null) {
                        if (candA.groupe.date <= candB.groupe.date) {
                            sb.append(nomA).append(" (groupe ").append(candA.groupe.nom).append(" - ").append(candA.groupe.date).append(")").append(" : ").append(voixA).append(" voix\n");
                        } else {
                            sb.append(nomB).append(" (groupe ").append(candB.groupe.nom).append(" - ").append(candB.groupe.date).append(")").append(" : ").append(voixB).append(" voix\n");
                        }
                    } else {
                        sb.append(nomA).append(" : ").append(voixA).append(" voix\n");
                        sb.append(nomB).append(" : ").append(voixB).append(" voix\n");
                    }
                } else if (voixA >= 2 * voixB) {
                    sb.append(candA.nom).append(" : ").append(voixA).append(" voix\n");
                    sb.append(candA.nom2).append("\n");
                } else {
                    if (voixA > 0) sb.append(nomA).append(" : ").append(voixA).append(" voix\n");
                    if (voixB > 0) sb.append(nomB).append(" : ").append(voixB).append(" voix\n");
                }
            }
        }
        return sb.toString();
    }

    static class Vote
    {
        String candidat;
        String colistier;
        String bureau;
        int voix;
        public Vote(String candidat, String colistier, String bureau, int voix)
        {
            this.candidat = candidat;
            this.colistier = colistier;
            this.bureau = bureau;
            this.voix = voix;
        }
    }
}