import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Methods metodos = new Methods();
        Scanner ler = new Scanner(System.in);

        // UI
        while (true) {
            System.out.println(
                    "\n1 - Adicionar Registros nas árvores\n2 - Print Tree\n3 - Buscar CPF\n4 - Busca Nome\n5 - Busca por Data\n6 - Exit");
            int answer = ler.nextInt();
            switch (answer) {
                case 1:
                    metodos.imp.imprimeArrayListNodes();
                    metodos.puxaRegistros();
                    break;

                case 2:
                    System.out.println("\nRepresentacao da árvore CPF por identacao");
                    System.out.println("=========================================");
                    metodos.printArvoreCPF(metodos.getRootCPF(), "");
                    System.out.println("\n\nRepresentacao da árvore NOME por identacao");
                    System.out.println("==========================================");
                    metodos.printArvoreNome(metodos.getRootNome(), "");
                    System.out.println("\n\nRepresentacao da árvore DATA por identacao");
                    System.out.println("==========================================");
                    metodos.printArvoreData(metodos.getRootData(), "");
                    System.out.println("\n");

                    break;

                case 3:
                    System.out.println("\nQual CPF você deseja buscar?");
                    Long buscaCPF = ler.nextLong();
                    metodos.buscaCPF(buscaCPF, metodos.getRootCPF());
                    break;

                case 4:
                    System.out.println("\nDigite as letras pelas quais você deseja procurar um nome:");
                    String string = ler.nextLine();
                    string = ler.nextLine();

                    metodos.buscaNome(string, metodos.getRootNome());

                    break;
                case 5:
                    System.out.println("\nQual a data incial que você deseja buscar? Use o formato DD/MM/YYYY");
                    String startDateString = ler.nextLine();
                    startDateString = ler.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Usar um objeto
                                                                                             // DateTimeFormatter para
                                                                                             // analisar a data
                    LocalDate startDate = Methods.converterDataInvert(startDateString); ;

                    System.out.println("\nQual a data final que você deseja buscar? Use o formato DD/MM/YYYY");
                    String endDateString = ler.nextLine();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Usar um objeto DateTimeFormatter para
                                                                           // analisar a data
                    LocalDate endDate = Methods.converterDataInvert(endDateString);
                    metodos.buscaData(startDate, endDate, metodos.getRootData());
                    break;

                case 6:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Valor invalido");
                    break;
            }
        }
    }
}