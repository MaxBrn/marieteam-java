import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        /*
        // Création des équipements
        Equipement equipement1 = new Equipement("1", "Boué");
        Equipement equipement2 = new Equipement("2", "Echelle");
        Equipement equipement3 = new Equipement("3", "Aviron");
        Equipement equipement4 = new Equipement("4", "Rame");
        Equipement equipement5 = new Equipement("5", "Pompe");

        // Création des équipements pour chaque bateau
        List<Equipement> equipementList1 = new ArrayList<>();
        equipementList1.add(equipement1);
        equipementList1.add(equipement2);

        List<Equipement> equipementList2 = new ArrayList<>();
        equipementList2.add(equipement3);
        equipementList2.add(equipement4);

        List<Equipement> equipementList3 = new ArrayList<>();
        equipementList3.add(equipement5);

        List<Equipement> equipementList4 = new ArrayList<>();
        equipementList4.add(equipement1);
        equipementList4.add(equipement3);

        List<Equipement> equipementList5 = new ArrayList<>();
        equipementList5.add(equipement2);
        equipementList5.add(equipement5);

        // Création des bateaux
        BateauVoyageur bateauVoyageur1 = new BateauVoyageur("1", "Boaty", 5.13, 3.3, 40, equipementList1, "/imageAccueil.jpg");
        BateauVoyageur bateauVoyageur2 = new BateauVoyageur("2", "Goaty", 4.13, 3.13, 50, equipementList2, "/imageAccueil.jpg");
        BateauVoyageur bateauVoyageur3 = new BateauVoyageur("3", "Saily", 6.5, 3.8, 60, equipementList3, "/imageAccueil.jpg");
        BateauVoyageur bateauVoyageur4 = new BateauVoyageur("4", "Wavey", 5.8, 3.5, 55, equipementList4, "/imageAccueil.jpg");
        BateauVoyageur bateauVoyageur5 = new BateauVoyageur("5", "Splashy", 4.5, 3.0, 45, equipementList5, "/imageAccueil.jpg");

        // Liste des bateaux
        List<BateauVoyageur> bateauVoyageurs = new ArrayList<>();
        bateauVoyageurs.add(bateauVoyageur1);
        bateauVoyageurs.add(bateauVoyageur2);
        bateauVoyageurs.add(bateauVoyageur3);
        bateauVoyageurs.add(bateauVoyageur4);
        bateauVoyageurs.add(bateauVoyageur5);*/
        DatabaseQuery databaseQuery = new DatabaseQuery();
        HashMap<Integer, BateauVoyageur> bateaux = new HashMap<>();
        bateaux = databaseQuery.SelectAllBateau();
        HashMap<Integer, BateauVoyageur> finalBateaux = bateaux;
        List<Equipement> equipementDispo = DatabaseQuery.SelectAllEquipement();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface(finalBateaux, equipementDispo);  // Création de la fenêtre graphique
            }
        });
    }
}
