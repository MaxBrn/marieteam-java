import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour gérer la connexion à la base de données
 *
 * Cette classe implémente le pattern SINGLETON pour la gestion de connexion
 * à une base de données PostgreSQL hébergée sur Supabase.
 *
 * PATTERN SINGLETON : Assure qu'une seule instance de connexion existe
 * dans toute l'application, évitant ainsi la création multiple de connexions
 * coûteuses en ressources.
 */
public class DatabaseConnection {

    // === CONFIGURATION DE LA BASE DE DONNÉES ===

    /**
     * URL de connexion JDBC vers la base de données PostgreSQL
     *
     * Structure de l'URL :
     * - jdbc:postgresql:// : Protocole JDBC pour PostgreSQL
     * - aws-0-eu-west-2.pooler.supabase.com:6543 : Serveur et port Supabase
     * - postgres : Nom de la base de données
     * - user=postgres.vlqfkkxckflqiqdhjaur : Nom d'utilisateur
     * - password=MarieteamEpreuve : Mot de passe
     *
     */
    private static final String JDBC_URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:6543/postgres?user=postgres.vlqfkkxckflqiqdhjaur&password=MarieteamEpreuve";

    // === INSTANCE SINGLETON ===

    /**
     * Instance unique de la connexion à la base de données
     *
     * - static : Appartient à la classe, pas aux instances
     * - Connection : Type d'objet représentant une connexion JDBC
     * - Initialement null, sera créée lors du premier appel à getConnection()
     */
    private static Connection connection;

    // === MÉTHODE D'ACCÈS À LA CONNEXION ===

    /**
     * Méthode statique pour obtenir la connexion à la base de données
     *
     * Implémente le pattern LAZY INITIALIZATION :
     * - La connexion n'est créée que lors du premier appel
     * - Les appels suivants réutilisent la même connexion
     * - Vérifie si la connexion existe et est toujours active
     *
     * @return Connection L'objet de connexion à la base de données
     * @throws SQLException Si une erreur survient lors de la connexion
     *         (serveur inaccessible, identifiants incorrects, etc.)
     */
    public static Connection getConnection() throws SQLException {

        // Vérification de l'état de la connexion
        // Deux conditions pour créer/recréer une connexion :
        // 1. connection == null : Aucune connexion n'a encore été créée
        // 2. connection.isClosed() : La connexion existante a été fermée
        if (connection == null || connection.isClosed()) {

            // Création d'une nouvelle connexion via DriverManager
            // DriverManager.getConnection() :
            // - Parse l'URL JDBC
            // - Charge le driver PostgreSQL automatiquement (JDBC 4.0+)
            // - Établit la connexion avec les paramètres fournis
            // - Peut lever une SQLException en cas d'échec
            connection = DriverManager.getConnection(JDBC_URL);
        }

        // Retourne la connexion (nouvelle ou existante)
        return connection;
    }
}