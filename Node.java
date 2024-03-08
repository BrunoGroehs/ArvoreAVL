import java.time.LocalDate;

public class Node {
    private int code;
    private Long cpf, rg;
    private Node filhoD, filhoE, pai;
    private String nome, cidade;
    private LocalDate data;
    private boolean raiz;
    private String idCPF; // criado para identificar construtor do CPF
    private int idNome; // criado para identificar construtor do nome
    private int balanceamento;

    // CONSTRUTOR ARRAY
    public Node(Long cpf, Long rg, String nome, LocalDate data, String cidade) {
        this.cpf = cpf;
        this.rg = rg;
        this.nome = nome;
        this.cidade = cidade;
        this.data = data;
    }

    // CONSTRUTOR CPF
    public Node(Long cpf, int code, String idCPF, boolean raiz, Node pai) {
        this.cpf = cpf;
        this.code = code;
        this.idCPF = idCPF;
        this.raiz = raiz;
        this.pai = pai;
    }

    // CONSTRUTOR NOME
    public Node(String nome, int code, int idNome, boolean raiz, Node pai) {
        this.nome = nome;
        this.code = code;
        this.idNome = idNome;
        this.raiz = raiz;
        this.pai = pai;
    }

    // CONSTRUTOR DATA
    public Node(LocalDate data, int code, boolean raiz, Node pai) {
        this.data = data;
        this.code = code;
        this.raiz = raiz;
        this.pai = pai;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getRg() {
        return rg;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public Node getFilhoD() {
        return filhoD;
    }

    public void setFilhoD(Node filhoD) {
        this.filhoD = filhoD;
    }

    public Node getFilhoE() {
        return filhoE;
    }

    public void setFilhoE(Node filhoE) {
        this.filhoE = filhoE;
    }

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isRaiz() {
        return raiz;
    }

    public void setRaiz(boolean raiz) {
        this.raiz = raiz;
    }

    public int getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    public void imprimeNode(Node node) {
        if (node != null) {
            System.out.printf("\nCPF: " + node.getCpf());
            System.out.printf("\nCode: " + node.getCode());
            if (node.getFilhoD() != null) {
                System.out.printf("\nFD: " + node.getFilhoD().getCpf());
            }
            if (node.getFilhoE() != null) {
                System.out.printf("\nFE: " + node.getFilhoE().getCpf());
            }
            if (node.getPai() != null) {
                System.out.printf("\nPai: " + node.getPai().getCpf());
            }
            System.out.println("");
        }

    }

    public boolean verificaPai(Node node) {
        if (node.getPai() != null) {
            return true;
        } else {
            return false;
        }
    }
}