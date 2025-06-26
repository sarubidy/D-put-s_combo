public class Faritra
{
    String nom;
    Faritany[] list_faritany;

    public  Faritra(String nom, Faritany[] list_faritany)
    {
        this.nom = nom;
        this.list_faritany = list_faritany;
    }
    @Override
    public String toString()
    {
        return this.nom;
    }
}