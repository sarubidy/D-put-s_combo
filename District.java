public class District
{
    String nom;
    Bureau[] list_bureau;
    int nombre_deputes;

    public District(String nom, int nombre_deputes, Bureau[] list_bureau) {
        this.nom = nom;
        this.nombre_deputes = nombre_deputes; 
        this.list_bureau = list_bureau;
    }
    @Override
    public String toString()
    {
        return this.nom;
    }
}