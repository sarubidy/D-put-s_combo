package pack;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Candidats C1 = new Candidats("C1","C1");
        Candidats C2 = new Candidats("C2","C2");
        Candidats C3 = new Candidats("C3","C3");
        Candidats C4 = new Candidats("C4","C4");
        Candidats C5 = new Candidats("C5","C5");
        Candidats C6 = new Candidats("C6","C6");
        Candidats C7 = new Candidats("C7","C7");
        Candidats C8 = new Candidats("C8","C8");

        Candidats[] c_atsimondrano = new Candidats[2];
        c_atsimondrano[0] = C1;
        c_atsimondrano[1] = C2;

        Bureau bv1 = new Bureau("BV101",c_atsimondrano);
        Bureau bv2 = new Bureau("Bv102",c_atsimondrano);
        
        Bureau[] b_atsimondrano = new Bureau[2];
        b_atsimondrano[0] = bv1;
        b_atsimondrano[1] = bv2;

        District atsimondrano = new District("Atsimondrano",2, b_atsimondrano);

        District[] d_ampitatafika =  new District[1];
        d_ampitatafika[0] = atsimondrano;

        Faritany ampitatafika = new Faritany("Ampitatafika",d_ampitatafika);

        Faritany[] f_analamanga = new Faritany[1];
        f_analamanga[0] = ampitatafika;

        Faritra analamanga = new Faritra("Analamanga",f_analamanga);

        Candidats[] c_antsirabe = new Candidats[2];
        c_antsirabe[0] = C3;
        c_antsirabe[1] = C4;

        Bureau bv3 = new Bureau("BV201",c_antsirabe);
        Bureau bv4 = new Bureau("Bv202",c_antsirabe);
        
        Bureau[] b_antsirabe = new Bureau[2];
        b_antsirabe[0] = bv3;
        b_antsirabe[1] = bv4;

        District antsirabe = new District("Antsirabe I",2, b_antsirabe);

        District[] d_ambohimena =  new District[1];
        d_ambohimena[0] = antsirabe;

        Faritany ambohimena = new Faritany("Ambohimena",d_ambohimena);

        Faritany[] f_vakinakaratra = new Faritany[1];
        f_vakinakaratra[0] = ambohimena;

        Faritra vakinakaratra = new Faritra("Vakinakaratra",f_vakinakaratra);

        Candidats[] c_toamasina = new Candidats[2];
        c_toamasina[0] = C5;
        c_toamasina[1] = C6;

        Bureau bv5 = new Bureau("BV301",c_toamasina);
        Bureau bv6 = new Bureau("Bv302",c_toamasina);
        
        Bureau[] b_toamasina = new Bureau[2];
        b_toamasina[0] = bv5;
        b_toamasina[1] = bv6;

        District toamasina = new District("Toamasina I",2, b_toamasina);

        District[] d_tanambao_verery =  new District[1];
        d_tanambao_verery[0] = toamasina;

        Faritany tanambao_verery = new Faritany("Tanambao Verery",d_tanambao_verery);

        Faritany[] f_antsinanana = new Faritany[1];
        f_antsinanana[0] = tanambao_verery;

        Faritra antsinanana = new Faritra("Antsinanana",f_antsinanana);

        Candidats[] c_itasy = new Candidats[2];
        c_itasy[0] = C7;
        c_itasy[1] = C8;

        Bureau bv7 = new Bureau("BV401",c_itasy);
        Bureau bv8 = new Bureau("BV402",c_itasy);
        Bureau bv9 = new Bureau("BV403",c_itasy);
        
        Bureau[] b_itasy_1 = new Bureau[1];
        b_itasy_1[0] = bv7;

        Bureau[] b_itasy_2 = new Bureau[2];
        b_itasy_2[0] = bv8;
        b_itasy_2[1] = bv9;

        District arivonimamo = new District("Arivonimamo",2,b_itasy_1);
        District soavinandriana = new District("Soavandriana",2,b_itasy_2);
        District miarinarivo = new District("Miarinarivo",2,b_itasy_2);

        District[] d_itasy_1 = new District[1];
        d_itasy_1[0] = arivonimamo;
        
        District[] d_itasy_2 = new District[2];
        d_itasy_2[0] = soavinandriana;
        d_itasy_2[1] = miarinarivo;

        Faritany ambatomanga = new Faritany("Ambatomanga", d_itasy_1);
        
        Faritany talata_dondona = new Faritany("Talata-Dondona", d_itasy_2);

        Faritany[] f_itasy = new Faritany[2];
        f_itasy[0] = ambatomanga;
        f_itasy[1] = talata_dondona;

        Faritra itasy = new Faritra("Itasy",f_itasy);

        Faritra [] faritraArr = new Faritra[4];
        faritraArr[0] = analamanga;
        faritraArr[1] = vakinakaratra;
        faritraArr[2] = antsinanana;
        faritraArr[3] = itasy;
        
        Candidats[] list_candidats_1 = new Candidats[3];
        list_candidats_1[0] = C1;
        list_candidats_1[1] = C5;
        list_candidats_1[2] = C8;

        Candidats[] list_candidats_2 = new Candidats[2];
        list_candidats_2[0] = C2;
        list_candidats_2[1] = C3;
        
        Candidats[] list_candidats_3 = new Candidats[3];
        list_candidats_3[0] = C4;
        list_candidats_3[1] = C6;
        list_candidats_3[2] = C7;

        Groupe g1 = new Groupe("G1",1988,list_candidats_1);
        Groupe g2 = new Groupe("G2",1994,list_candidats_3);
        Groupe g3 = new Groupe("G3",1997,list_candidats_3);

        for (Candidats c : list_candidats_1) c.groupe = g1;
        for (Candidats c : list_candidats_2) c.groupe = g2;
        for (Candidats c : list_candidats_3) c.groupe = g3;

    new Frame(faritraArr);
    }
}