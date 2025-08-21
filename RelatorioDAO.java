import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioDAO {

    public void listarImoveisDisponiveis() {
        String sql = """
            SELECT i.id_imovel, i.endereco, i.tipo, i.area_m2, i.quartos
            FROM IMOVEL i
            WHERE i.id_imovel NOT IN (
                SELECT id_imovel FROM CONTRATO_ALUGUEL WHERE data_fim IS NULL OR data_fim > CURDATE()
            )
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Imóveis disponíveis para aluguel:");
            while (rs.next()) {
                System.out.printf("ID: %d | Endereço: %s | Tipo: %s | Área: %.2f m² | Quartos: %d\n",
                        rs.getInt("id_imovel"),
                        rs.getString("endereco"),
                        rs.getString("tipo"),
                        rs.getDouble("area_m2"),
                        rs.getInt("quartos"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarContratosAtivos() {
        String sql = """
            SELECT c.id_contrato, cli.nome, i.endereco, c.valor_aluguel, c.data_inicio, c.data_fim
            FROM CONTRATO_ALUGUEL c
            JOIN CLIENTE cli ON c.id_cliente = cli.id_cliente
            JOIN IMOVEL i ON c.id_imovel = i.id_imovel
            WHERE c.data_fim IS NULL OR c.data_fim > CURDATE()
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Contratos ativos:");
            while (rs.next()) {
                System.out.printf("Contrato: %d | Cliente: %s | Imóvel: %s | Valor: %.2f | Início: %s | Fim: %s\n",
                        rs.getInt("id_contrato"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getDouble("valor_aluguel"),
                        rs.getDate("data_inicio"),
                        rs.getDate("data_fim"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clientesComMaisContratos() {
        String sql = """
            SELECT cli.nome, COUNT(c.id_contrato) AS total_contratos
            FROM CLIENTE cli
            JOIN CONTRATO_ALUGUEL c ON cli.id_cliente = c.id_cliente
            GROUP BY cli.id_cliente, cli.nome
            ORDER BY total_contratos DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Clientes com mais contratos:");
            while (rs.next()) {
                System.out.printf("Cliente: %s | Total de contratos: %d\n",
                        rs.getString("nome"),
                        rs.getInt("total_contratos"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contratosExpirando() {
        String sql = """
            SELECT c.id_contrato, cli.nome, i.endereco, c.data_fim
            FROM CONTRATO_ALUGUEL c
            JOIN CLIENTE cli ON c.id_cliente = cli.id_cliente
            JOIN IMOVEL i ON c.id_imovel = i.id_imovel
            WHERE c.data_fim BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Contratos expirando nos próximos 30 dias");
            while (rs.next()) {
                System.out.printf("Contrato: %d | Cliente: %s | Imóvel: %s | Fim: %s\n",
                        rs.getInt("id_contrato"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getDate("data_fim"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
