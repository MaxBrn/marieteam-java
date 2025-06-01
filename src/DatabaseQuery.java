import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilitaire pour les opérations de base de données
 *
 * Cette classe contient toutes les méthodes statiques pour interagir avec la base de données
 * concernant les bateaux et leurs équipements. Elle implémente le pattern DAO (Data Access Object)
 * en fournissant une interface entre l'application et la couche de données.
 *
 * PATTERN DAO : Sépare la logique d'accès aux données de la logique métier
 *
 */
public class DatabaseQuery {

    // === MÉTHODES DE LECTURE (SELECT) ===

    /**
     * Récupère tous les bateaux de la base de données avec leurs équipements
     *
     * Cette méthode effectue une requête complexe qui :
     * 1. Sélectionne tous les bateaux dans la table "bateau"
     * 2. Pour chaque bateau, récupère ses équipements via SelectBateauEquipment()
     * 3. Construit des objets BateauVoyageur complets
     * 4. Les stocke dans une HashMap avec l'ID comme clé
     *
     * @return HashMap<Integer,BateauVoyageur> Collection de bateaux indexée par ID
     *         Retourne une HashMap vide en cas d'erreur ou si aucun bateau n'existe
     */
    public static HashMap<Integer, BateauVoyageur> SelectAllBateau() {
        // Initialisation de la collection de résultats
        HashMap<Integer, BateauVoyageur> bateaux = new HashMap<>();

        try {
            // Obtention de la connexion via la classe DatabaseConnection
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Connexion réussie à Supabase !");

            // Création d'un Statement pour exécuter la requête
            Statement statement = conn.createStatement();

            // Requête SQL simple pour récupérer tous les bateaux
            String query = "SELECT * FROM bateau";

            // Exécution de la requête et récupération des résultats
            ResultSet resultSet = statement.executeQuery(query);

            try {
                // Parcours de tous les résultats
                // Vérification défensive : resultSet != null (bonne pratique)
                while (resultSet != null && resultSet.next()) {

                    // Pour chaque bateau, récupération de ses équipements
                    // Appel à une autre méthode de cette classe
                    List<Equipement> equipements = SelectBateauEquipment(resultSet.getInt("id"));

                    // Construction de l'objet BateauVoyageur avec toutes ses données
                    BateauVoyageur bateauVoyageur = new BateauVoyageur(
                            resultSet.getInt("id"),           // ID du bateau
                            resultSet.getString("nom"),       // Nom du bateau
                            resultSet.getDouble("largeur"),   // Largeur en mètres
                            resultSet.getDouble("longueur"),  // Longueur en mètres
                            resultSet.getInt("vitesse"),      // Vitesse (conversion int vers double automatique)
                            equipements,                      // Liste des équipements
                            resultSet.getString("ImageUrl")   // URL de l'image
                    );

                    // Ajout du bateau à la HashMap avec son ID comme clé
                    bateaux.put(resultSet.getInt("id"), bateauVoyageur);
                }
            } catch (SQLException e) {
                // Gestion des erreurs lors du traitement des résultats
                System.out.println("❌ Erreur de connexion ou d'exécution de la requête : " + e.getMessage());
                e.printStackTrace();
            }

            // Fermeture de la connexion
            // Note : Peut poser problème avec le pattern Singleton de DatabaseConnection
            conn.close();

        } catch (SQLException e) {
            // Gestion des erreurs de connexion initiale
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }

        return bateaux;
    }

    // === MÉTHODES DE MODIFICATION (UPDATE) ===

    /**
     * Met à jour les informations d'un bateau existant
     *
     * @param id Identifiant du bateau à modifier
     * @param nom Nouveau nom du bateau
     * @param largeur Nouvelle largeur du bateau
     * @param longueur Nouvelle longueur du bateau
     * @param vitesse Nouvelle vitesse du bateau
     * @param image Nouvelle URL d'image du bateau
     * @return Boolean true si la mise à jour a réussi (au moins une ligne modifiée)
     *                 false en cas d'erreur ou si aucune ligne n'a été modifiée
     */
    public static Boolean updateBateau(int id, String nom, double largeur, double longueur, double vitesse, String image) {

        try {
            // Obtention de la connexion
            Connection conn = DatabaseConnection.getConnection();

            String query = "UPDATE bateau SET nom = '" + nom + "', largeur = " + largeur +
                    ", longueur = " + longueur + ", vitesse = " + vitesse + ", ImageUrl = '" + image + "'" +
                    " WHERE id = " + id;

            try {
                // Utilisation de Statement au lieu de PreparedStatement (non sécurisé)
                Statement statement = conn.createStatement();

                // Exécution de la requête de mise à jour
                // executeUpdate() retourne le nombre de lignes affectées
                int rowsAffected = statement.executeUpdate(query);

                conn.close(); // Fermeture de la connexion

                // Retourne true si au moins une ligne a été modifiée
                return rowsAffected > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false; // Échec de l'opération
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === MÉTHODES DE LECTURE DES ÉQUIPEMENTS ===

    /**
     * Récupère tous les équipements disponibles dans la base de données
     *
     * Cette méthode est utilisée pour obtenir la liste complète des équipements,
     * probablement pour des listes déroulantes ou des formulaires de sélection.
     *
     * @return List<Equipement> Liste de tous les équipements disponibles
     *         Retourne une liste vide en cas d'erreur
     */
    public static List<Equipement> SelectAllEquipement() {
        // Initialisation de la liste de résultats
        List<Equipement> equipements = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT * FROM equipement";
            ResultSet resultSet = statement.executeQuery(query);

            try {
                // Parcours de tous les équipements
                while (resultSet != null && resultSet.next()) {
                    // Construction de chaque objet Equipement
                    Equipement equipement = new Equipement(
                            resultSet.getInt("id"),        // ID de l'équipement
                            resultSet.getString("nom")     // Nom de l'équipement
                    );
                    // Ajout à la liste
                    equipements.add(equipement);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipements;
    }

    /**
     * Récupère tous les équipements associés à un bateau spécifique
     *
     * Cette méthode effectue une jointure entre les tables :
     * - equipement : contient les informations des équipements
     * - equipBateau : table de liaison (many-to-many) entre bateaux et équipements
     *
     * @param idBateau Identifiant du bateau dont on veut les équipements
     * @return List<Equipement> Liste des équipements du bateau spécifié
     */
    public static List<Equipement> SelectBateauEquipment(Integer idBateau) {
        List<Equipement> equipements = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            Statement statement = conn.createStatement();

            // Requête avec jointure pour récupérer les équipements d'un bateau
            String query = "SELECT * FROM equipement e JOIN \"equipBateau\" eb ON e.id = eb.\"idEquipement\" WHERE eb.\"idBateau\" = " + idBateau;

            ResultSet resultSet = statement.executeQuery(query);

            try {
                while (resultSet != null && resultSet.next()) {
                    Equipement equipement = new Equipement(
                            resultSet.getInt("id"),        // ID de l'équipement
                            resultSet.getString("nom")     // Nom de l'équipement
                    );
                    equipements.add(equipement);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipements;
    }

    // === MÉTHODES DE GESTION DES ASSOCIATIONS BATEAU-ÉQUIPEMENT ===

    /**
     * Supprime tous les équipements associés à un bateau
     *
     * Cette méthode supprime les associations dans la table de liaison "equipBateau"
     * mais ne supprime pas les équipements eux-mêmes de la table "equipement".
     *
     * @param idBateau ID du bateau dont on veut supprimer les équipements
     * @return true si l'opération a réussi (au moins une association supprimée)
     */
    public boolean deleteAllEquipements(int idBateau) {

        // Utilisation du try-with-resources pour fermeture automatique
        // try-with-resources se charge déjà de la fermeture
        try (Connection conn = DatabaseConnection.getConnection()) {

            String query = "DELETE FROM \"equipBateau\" WHERE \"idBateau\" =" + idBateau;
            Statement statement = conn.createStatement();

            // executeUpdate() pour les opérations de modification
            int rowsAffected = statement.executeUpdate(query);

            conn.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ajoute un équipement à un bateau (crée une association)
     *
     * Cette méthode insère une nouvelle ligne dans la table de liaison "equipBateau"
     * pour associer un équipement existant à un bateau existant.
     *
     * @param idBateau ID du bateau auquel ajouter l'équipement
     * @param idEquipement ID de l'équipement à ajouter
     * @return true si l'opération a réussi (une association créée)
     */
    public boolean addEquipementToBateau(int idBateau, int idEquipement) {

        try (Connection conn = DatabaseConnection.getConnection()) {


            String query = "INSERT INTO \"equipBateau\" (\"idBateau\", \"idEquipement\") VALUES (" + idBateau + ", " + idEquipement + ")";
            Statement statement = conn.createStatement();

            int rowsAffected = statement.executeUpdate(query);

            conn.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}