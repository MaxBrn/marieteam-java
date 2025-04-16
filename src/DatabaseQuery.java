import java.sql.*;
import java.util.HashMap;

public class DatabaseQuery {

    public static HashMap<Integer,BateauVoyageur> SelectAllBateau() {
        HashMap<Integer,BateauVoyageur> bateaux = new HashMap<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Connexion réussie à Supabase !");
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM bateau";
            ResultSet resultSet = statement.executeQuery(query);

            try {
                while (resultSet != null && resultSet.next()) {
                    BateauVoyageur bateauVoyageur = new BateauVoyageur(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getDouble("largeur"),
                            resultSet.getDouble("longueur"),
                            resultSet.getInt("vitesse"),
                            null,
                            resultSet.getString("ImageUrl")
                    );
                    bateaux.put(resultSet.getInt("id"),bateauVoyageur);
                }
            }
            catch (SQLException e) {
                System.out.println("❌ Erreur de connexion ou d'exécution de la requête : " + e.getMessage());
                e.printStackTrace();
            }
            conn.close(); // facultatif, sauf si tu veux réutiliser plus tard
        } catch (
                SQLException e) {
                System.out.println("❌ Erreur de connexion : " + e.getMessage());
                 e.printStackTrace();
        }
        return bateaux;
    }

    public static Boolean updateBateau(int id, String nom, double largeur, double longueur, double vitesse, String image) {

        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Connexion réussie à Supabase !");
            System.out.println(id);
            String query = "UPDATE bateau SET nom = '" + nom + "', largeur = " + largeur +
                    ", longueur = " + longueur + ", vitesse = " + vitesse + ", ImageUrl = '" + image + "'" +
                    " WHERE id = " + id;

            try {
                // Utilisation de Statement à la place de PreparedStatement
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
                conn.close(); // Facultatif sauf si vous voulez réutiliser plus tard
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("❌ Erreur lors de la mise à jour : " + e.getMessage());
                e.printStackTrace();
                return false; // Si une erreur survient
            }




        } catch (
                SQLException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
            return false;
        }


    }


}



