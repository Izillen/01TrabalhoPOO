import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory {

    private static ConnectionFactory instance;

    private static final String URL = "jdbc:mysql://localhost:3306/imobiliaria_familia";
    private static final String USUARIO = "root";
    private static final String SENHA = "univille";

    private ConnectionFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega o driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado!", e);
        }
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
