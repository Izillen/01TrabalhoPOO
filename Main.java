import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RelatorioDAO relatorioDAO = new RelatorioDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistema de Relatórios ===");
            System.out.println("1 - Listar imóveis disponíveis para aluguel");
            System.out.println("2 - Listar contratos ativos");
            System.out.println("3 - Clientes com mais contratos");
            System.out.println("4 - Contratos expirando nos próximos 30 dias");
            System.out.println("0 - Sair");
            System.out.print("Selecione uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> relatorioDAO.listarImoveisDisponiveis();
                case 2 -> relatorioDAO.listarContratosAtivos();
                case 3 -> relatorioDAO.clientesComMaisContratos();
                case 4 -> relatorioDAO.contratosExpirando();
                case 0 -> {
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}
