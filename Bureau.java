package pack;
public class Bureau
{
    String nom;
    Candidats[] list_candidats;

    public  Bureau(String nom, Candidats[] list_candidats)
    {
        this.nom = nom;
        this.list_candidats = list_candidats;
    }
    @Override
    public String toString()
    {
        return this.nom; 
    }
}