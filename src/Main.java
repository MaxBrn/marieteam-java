import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 * ============================================================================
 * CLASSE PRINCIPALE - POINT D'ENTRÉE DE L'APPLICATION
 * ============================================================================
 *
 * Cette classe contient la méthode main() qui lance l'application de gestion
 * des bateaux voyageurs. Elle initialise les données depuis la base de données
 * et démarre l'interface graphique Swing.
 */
public class Main {

    /**
     * Point d'entrée principal de l'application
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {

        // === INITIALISATION DE L'ACCÈS AUX DONNÉES ===
        // Création de l'objet qui gère les requêtes vers la base de données
        DatabaseQuery databaseQuery = new DatabaseQuery();

        // === CHARGEMENT DES BATEAUX DEPUIS LA BASE DE DONNÉES ===
        // HashMap pour stocker les bateaux avec leur ID comme clé
        HashMap<Integer, BateauVoyageur> bateaux = new HashMap<>();

        // Récupération de tous les bateaux depuis la base de données
        bateaux = databaseQuery.SelectAllBateau();

        // Création d'une référence finale pour utilisation dans la classe anonyme
        // (nécessaire car les variables locales utilisées dans les classes anonymes
        // doivent être finales ou effectivement finales)
        HashMap<Integer, BateauVoyageur> finalBateaux = bateaux;

        // === CHARGEMENT DES ÉQUIPEMENTS DISPONIBLES ===
        // Récupération de la liste de tous les équipements depuis la base de données
        // Méthode statique, pas besoin d'instance de DatabaseQuery
        List<Equipement> equipementDispo = DatabaseQuery.SelectAllEquipement();

        // === LANCEMENT DE L'INTERFACE GRAPHIQUE ===
        // SwingUtilities.invokeLater() garantit que l'interface graphique sera créée
        // sur l'Event Dispatch Thread (EDT), ce qui est obligatoire pour Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Création et affichage de la fenêtre principale de l'application
                // Passage des données chargées (bateaux et équipements) au constructeur
                new Interface(finalBateaux, equipementDispo);
            }
        });
    }
}