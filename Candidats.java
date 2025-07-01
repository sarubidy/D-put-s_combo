package pack;
public class Candidats
{
    String nom;   
    String nom2; 
    Groupe groupe; 

    public Candidats(String nom, String nom2)
    {
        this.nom = nom;
        this.nom2 = nom2;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Candidats c = (Candidats) obj;
        return nom.equals(c.nom) && nom2.equals(c.nom2);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(nom, nom2);
    }
}