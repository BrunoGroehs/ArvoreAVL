import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Import {
    public static ArrayList<Node> nodes = new ArrayList<Node>();

    public Node getNode(int code) {
        return nodes.get(code);
    }

    public long getCpf(int code) {
        return nodes.get(code).getCpf();
    }

    public long getRg(int code) {
        return nodes.get(code).getRg();
    }

    public String getNome(int code) {
        return nodes.get(code).getNome();
    }

    public String getCidade(int code) {
        return nodes.get(code).getCidade();
    }

    public LocalDate getData(int code) {
        return nodes.get(code).getData();
    }

    public Import() {
        String caminho = "C:\\Users\\theo.rossetto@sap.com\\Documents\\Rossetto, Théo\\Coding\\ARVORE\\arquivo copy.csv";

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(caminho);
            br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                String[] partes = line.split(";");
                Long cpf = Long.parseLong(partes[0]);
                Long rg = Long.parseLong(partes[1]);
                String nome = partes[2];

                // Data
                String[] dataUnica = partes[3].split("/");
                int ano = Integer.parseInt(dataUnica[2]);
                int mes = Integer.parseInt(dataUnica[1]);
                int dia = Integer.parseInt(dataUnica[0]);
                LocalDate data = LocalDate.of(ano, mes, dia);

                String cidade = partes[4];

                // adiciona o Node Criado dentro do ArrayList

                nodes.add(new Node(cpf, rg, nome, data, cidade));

                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean comparaObj(int code1, int code2) {
        return nodes.get(code1).equals(nodes.get(code2));
    }

    public Node retornaNode(int code) {
        return nodes.get(code);
    }

    public void imprimeArrayListNodes() {
        for (Node node : nodes) {
            System.out.println("CPF: " + node.getCpf());
            System.out.println("RG: " + node.getRg());
            System.out.println("Nome: " + node.getNome());
            System.out.println("Data: " + node.getData());
            System.out.println("Cidade: " + node.getCidade());
            System.out.println();
        }
    }

    public static ArrayList<Node> getNodes() {
        return nodes;
    }

    public int quantidadeRegistros() {
        return nodes.size();
    }
}
