import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Copie ta string JDBC ici (trouvée dans Supabase > Settings > Database)
    private static final String JDBC_URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:6543/postgres?user=postgres.vlqfkkxckflqiqdhjaur&password=dobubqXoU5wLUXkn";


    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL);
        }
        return connection;
    }
}
