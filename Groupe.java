public class Groupe
{
    String nom;
    int date;
    Candidats[] list_candidats;

    public Groupe(String nom ,int date, Candidats[] list_candidats)
    {
        this.nom = nom;
        this.date = date;
        this.list_candidats = list_candidats;
    }
    @Override
    public String toString()
    {
        return this.nom;
    }
}