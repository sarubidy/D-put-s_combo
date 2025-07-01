package pack;
public class Faritany
{
    String nom;
    District[] list_district;

    public  Faritany(String nom, District[] list_district)
    {
        this.nom = nom;
        this.list_district = list_district;
    }
    @Override
    public String toString()
    {
        return this.nom;
    }
}