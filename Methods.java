import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Methods {

    // Inicializador das raizes
    Node rootCPF = new Node(null, 0, null, false, null);
    Node rootNome = new Node(null, 0, 0, false, null);
    Node rootData = new Node(null, 0, false, null);

    Import imp = new Import();
    int registros = imp.quantidadeRegistros();

    // Puxa os registros do arrayList e insere nas árvores
    public boolean puxaRegistros() {
        for (int i = 0; i < registros; i++) {
            triggaInsert(imp.getNode(i), imp.nodes.get(i).getCpf(), imp.nodes.get(i).getNome(),
                    imp.nodes.get(i).getData(), i);
        }
        return true;
    }
    
    // GETTERS do root de cada árvore. Usado na "Main.java" para chamar
    public Node getRootCPF() {
        return rootCPF;
    }
    public Node getRootNome() {
        return rootNome;
    }
    public Node getRootData() {
        return rootData;
    }

    /* ============= <INSERT> ============= */
    public void triggaInsert(Node nodeInserir, long cpf, String nome, LocalDate data, int code) {
        // RAIZ
        if (rootCPF.isRaiz() == false && rootNome.isRaiz() == false && rootData.isRaiz() == false) {
            insertRaiz(cpf, nome, data);
            return;
        // NÃO RAIZ
        } else {        
            checkaCPF(cpf, rootCPF); //Verifica se já existe algum node com o CPF que está sendo inserido. Setta verdade == true em caso positivo.
            if(verdade == true){ //Caso exista, ele não é inserido em nenhuma árvore.
                System.out.println("\nErro na insercao do node com index " + code + " nas árvores de CPF, Nome e Data." +"\nO CPF deste node já foi inserido anteriormente. Nao é permitido inserir nodes com o mesmo CPF, mesmo que o nome e data sejam diferentes." + "\nCPF: " + imp.getCpf(code) + "\nNome: " + nome + "\nData Nascimento: " + data);
                zeraVerdade(); //Setta o valor de boolean verdade para false, para que uma nova pesquisa possa ser feita;
            }else{
                insertRecursivoCPF(rootCPF, rootCPF.getPai(), cpf, code);
                fatorDeBalanceamento(rootCPF);
                rootCPF = verificarBalanceamento(rootCPF);

                insertRecursivoNOME(rootNome, rootNome.getPai(), nodeInserir, nome, code);
                fatorDeBalanceamento(rootNome);
                rootNome = verificarBalanceamento(rootNome);

                insertRecursivoDATA(rootData, rootData.getPai(), nodeInserir, data, nome, code);
                fatorDeBalanceamento(rootData);
                rootData = verificarBalanceamento(rootData);
                return;
            }
        }
    }

    // INSERE RAIZ
    public void insertRaiz(long cpf, String nome, LocalDate data) {
        // RAIZ CPF
        rootCPF.setCode(0);
        rootCPF.setFilhoD(null);
        rootCPF.setFilhoE(null);
        rootCPF.setRaiz(true);
        rootCPF.setPai(null);
        rootCPF.setCpf(cpf);
        // RAIZ NOME
        rootNome.setCode(0);
        rootNome.setFilhoD(null);
        rootNome.setFilhoE(null);
        rootNome.setRaiz(true);
        rootNome.setPai(null);
        rootNome.setNome(nome);
        // RAIZ DATA
        rootData.setCode(0);
        rootData.setFilhoD(null);
        rootData.setFilhoE(null);
        rootData.setRaiz(true);
        rootData.setPai(null);
        rootData.setData(data);
        return;
    }

    // INSERT CPF
    public void insertRecursivoCPF(Node nodeAtual, Node paiNodeAtual, Long cpf, int code) {
            /* DIREITA */
        if (cpf > nodeAtual.getCpf()) {
            if (nodeAtual.getFilhoD() != null) { // JÁ EXISTE FILHO A DIREITA, ENTÃO NAVEGA PARA A DIREITA
                paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOD COMO O NODEATUAL
                nodeAtual = nodeAtual.getFilhoD();
                insertRecursivoCPF(nodeAtual, paiNodeAtual, cpf, code);
                return;

            } else if (nodeAtual.getFilhoD() == null && nodeAtual != rootCPF) { // NÃO EXISTE FILHO A DIREITA E PAI !=
                                                                                // RAIZ, ENTÃO INSERE
                Node inserir = new Node(cpf, code, "idCPF", false, nodeAtual);
                nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;

            } else if (nodeAtual.getFilhoD() == null && nodeAtual == rootCPF) { // NÃO EXISTE FILHO A DIREITA E PAI ==
                                                                                // RAIZ, ENTÃO INSERE
                Node inserir = new Node(cpf, code, "idCPF", false, rootCPF);
                nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;
            }

            /* ESQUERDA */
        } else if (cpf < nodeAtual.getCpf()) {
            if (nodeAtual.getFilhoE() != null) { // JÁ EXISTE FILHO A ESQUERDA, ENTÃO NAVEGA PARA A ESQUERDA
                paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOE COMO O NODEATUAL
                nodeAtual = nodeAtual.getFilhoE();
                insertRecursivoCPF(nodeAtual, paiNodeAtual, cpf, code);
                return;

            } else if (nodeAtual.getFilhoE() == null && nodeAtual != rootCPF) { // NÃO EXISTE FILHO A ESQUERDA E PAI !=
                                                                                // RAIZ, ENTÃO
                Node inserir = new Node(cpf, code, "idCPF", false, nodeAtual); // CRIA NOVO NODE
                nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO

                return;

            } else if (nodeAtual.getFilhoE() == null && nodeAtual == rootCPF) { // NÃO EXISTE FILHO A ESQUERDA E PAI ==
                                                                                // RAIZ, ENTÃO INSERE
                Node inserir = new Node(cpf, code, "idCPF", false, nodeAtual);
                nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO

                return;
            }
        }
    }

    // INSERT NOME
    public void insertRecursivoNOME(Node nodeAtual, Node paiNodeAtual, Node inserirNode, String nome, int code) {        
        if (nome.compareTo(nodeAtual.getNome()) == 0) { // NOME JÁ EXISTE Node NODE ATUAL, ELE INSERE O NODE COMO FILHO A DIREITA DO ATUAL
            if (nodeAtual.getFilhoD() == null) { // SE O NODE ATUAL NÃO TEM FILHO A DIREITA, INSERE NA DIREITA
                Node inserir = new Node(nome, code, 0, false, nodeAtual);
                nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO

            } else if (nodeAtual.getFilhoD() != null) { // SE O NODE ATUAL TEM FILHO A DIREITA. NODE A INSERIR VIRA
                                                            // FILHO DO PAI, NODE ANTIGO VIRA FILHO DO NOVO NODE
                Node pai = nodeAtual;
                Node filhoAntigo = nodeAtual.getFilhoD();
                Node filhoNovo = inserirNode;

                filhoNovo.setFilhoD(filhoAntigo);
                filhoNovo.setPai(pai);
                filhoAntigo.setPai(filhoNovo);
                pai.setFilhoD(filhoNovo);
                nodeAtual = pai;
            }
            return;

            /* DIREITA */
        } else if (nome.compareTo(nodeAtual.getNome()) > 0) {
            if (nodeAtual.getFilhoD() != null) { // JÁ EXISTE FILHO A DIREITA, ENTÃO NAVEGA PARA A DIREITA
                paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOD COMO O NODEATUAL
                nodeAtual = nodeAtual.getFilhoD();
                insertRecursivoNOME(nodeAtual, paiNodeAtual, inserirNode, nome, code);
                return;

            } else if (nodeAtual.getFilhoD() == null && nodeAtual != rootNome) { // NÃO EXISTE FILHO A DIREITA E PAI != RAIZ, ENTÃO INSERE
                Node inserir = new Node(nome, code, 0, false, nodeAtual);
                nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;

            } else if (nodeAtual.getFilhoD() == null && nodeAtual == rootNome) { // NÃO EXISTE FILHO A DIREITA E PAI == RAIZ, ENTÃO INSERE
                Node inserir = new Node(nome, code, 0, false, nodeAtual);
                nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;
            }


            /* ESQUERDA */
        } else if (nome.compareTo(nodeAtual.getNome()) < 0) {
            if (nodeAtual.getFilhoE() != null) { // JÁ EXISTE FILHO A ESQUERDA, ENTÃO NAVEGA PARA A ESQUERDA
                paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOE COMO O NODEATUAL
                nodeAtual = nodeAtual.getFilhoE();
                insertRecursivoNOME(nodeAtual, paiNodeAtual, inserirNode, nome, code);
                return;

            } else if (nodeAtual.getFilhoE() == null && nodeAtual != rootNome) { // NÃO EXISTE FILHO A ESQUERDA E PAI != RAIZ, ENTÃO
                Node inserir = new Node(nome, code, 0, false, nodeAtual); // CRIA NOVO NODE
                nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;

            } else if (nodeAtual.getFilhoE() == null && nodeAtual == rootNome) { // NÃO EXISTE FILHO A ESQUERDA E PAI == RAIZ, ENTÃO INSERE
                Node inserir = new Node(nome, code, 0, false, nodeAtual);
                nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                return;
            }
        }
    }

    // INSERT DATA
    public void insertRecursivoDATA(Node nodeAtual, Node paiNodeAtual, Node inserirNode, LocalDate data, String nome,
            int code) {
        if (data.equals(nodeAtual.getData())) { // DATA JÁ EXISTE Node NODE ATUAL, ELE INSERE O NODE COMO FILHO A DIREITA DO ATUAL
            /* SE O NODE, COM MESMA DATA, SENDO INSERIDO É IGUAL AO NODE ATUAL */
            if (imp.getCpf(code) == imp.getCpf(nodeAtual.getCode())) {
                System.out.println("\nErro na insercao da data " + data + ": Node com CPF " + imp.getCpf(code) + " já existe.\nNao é permitido inserir nodes com o mesmo CPF, independente de se a data for a mesma ou nao");

                /* SE O NODE, COM MESMO NOME, SENDO INSERIDO NÃO É IGUAL AO NODE ATUAL */
            } else if (!(imp.getCpf(code) == imp.getCpf(nodeAtual.getCode())
                    && imp.getRg(code) == imp.getRg(nodeAtual.getCode())
                    && imp.getCidade(code).compareTo(imp.getCidade(nodeAtual.getCode())) == 0
                    && nome.compareTo(imp.getNome(nodeAtual.getCode())) == 0)) {

                if (nodeAtual.getFilhoD() == null) { // SE O NODE ATUAL NÃO TEM FILHO A DIREITA, INSERE NA DIREITA
                    Node inserir = new Node(data, code, false, nodeAtual);
                    nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                    nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO

                } else if (nodeAtual.getFilhoD() != null) { // SE O NODE ATUAL TEM FILHO A DIREITA. NODE A INSERIR VIRA FILHO DO PAI, NODE ANTIGO VIRA FILHO DO NOVO NODE
                    Node pai = nodeAtual;
                    Node filhoAntigo = nodeAtual.getFilhoD();
                    Node filhoNovo = inserirNode;

                    filhoNovo.setFilhoD(filhoAntigo);
                    filhoNovo.setPai(pai);
                    filhoAntigo.setPai(filhoNovo);
                    pai.setFilhoD(filhoNovo);
                    nodeAtual = pai;

                    System.out.println("\nData " + nodeAtual.getData() + " adicionado como filho de " + nodeAtual.getFilhoD().getData());
                }
            }
            return;

            /* DIREITA */
        } else if (data.isAfter(nodeAtual.getData())) {
            if (imp.getCpf(code) == imp.getCpf(nodeAtual.getCode())) {
                System.out.println("\nErro na insercao da data " + data + ": Node com CPF " + imp.getCpf(code) + " já existe.\nNao é permitido inserir nodes com o mesmo CPF, independente de se a data for a mesma ou nao");

                /* SE O NODE, COM MESMO NOME, SENDO INSERIDO NÃO É IGUAL AO NODE ATUAL */
            }else{
                if (nodeAtual.getFilhoD() != null) { // JÁ EXISTE FILHO A DIREITA, ENTÃO NAVEGA PARA A DIREITA
                paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOD COMO O NODEATUAL
                nodeAtual = nodeAtual.getFilhoD();
                insertRecursivoDATA(nodeAtual, paiNodeAtual, inserirNode, data, nome, code);
                return;

                } else if (nodeAtual.getFilhoD() == null && nodeAtual != rootData) { // NÃO EXISTE FILHO A DIREITA E PAI != RAIZ, ENTÃO INSERE
                    Node inserir = new Node(data, code, false, nodeAtual);
                    nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                    nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                    return;

                } else if (nodeAtual.getFilhoD() == null && nodeAtual == rootData) { // NÃO EXISTE FILHO A DIREITA E PAI == RAIZ, ENTÃO INSERE
                    Node inserir = new Node(data, code, false, nodeAtual);
                    nodeAtual.setFilhoD(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                    nodeAtual = nodeAtual.getFilhoD(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                    return;
                }
            }

            /* ESQUERDA */
        } else if (data.isBefore(nodeAtual.getData())) {
            if (imp.getCpf(code) == imp.getCpf(nodeAtual.getCode())) {
                System.out.println("\nErro na insercao da data " + data + ": Node com CPF " + imp.getCpf(code) + " já existe.\nNao é permitido inserir nodes com o mesmo CPF, independente de se a data for a mesma ou nao");

                /* SE O NODE, COM MESMO NOME, SENDO INSERIDO NÃO É IGUAL AO NODE ATUAL */
            }else{
                if (nodeAtual.getFilhoE() != null) { // JÁ EXISTE FILHO A ESQUERDA, ENTÃO NAVEGA PARA A ESQUERDA
                    paiNodeAtual = nodeAtual; // SALVA PAI DO FILHOE COMO O NODEATUAL
                    nodeAtual = nodeAtual.getFilhoE();
                    insertRecursivoDATA(nodeAtual, paiNodeAtual, inserirNode, data, nome, code);
                    return;

                } else if (nodeAtual.getFilhoE() == null && nodeAtual != rootData) { // NÃO EXISTE FILHO A ESQUERDA E PAI != RAIZ, ENTÃO
                    Node inserir = new Node(data, code, false, nodeAtual); // CRIA NOVO NODE
                    nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                    nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                    return;

                } else if (nodeAtual.getFilhoE() == null && nodeAtual == rootData) { // NÃO EXISTE FILHO A ESQUERDA E PAI == RAIZ, ENTÃO INSERE
                    Node inserir = new Node(data, code, false, nodeAtual);
                    nodeAtual.setFilhoE(inserir); // INSERE NOVO NODE COMO FILHO DO NODE ATUAL
                    nodeAtual = nodeAtual.getFilhoE(); // NOVO NODE VIRA FILHO RECÉM ADICIONADO
                    return;
                }
            }
        }
    }

    /* ============= </INSERT> ============= */
    /* ============ <BALANCEAMENTO> ============= */

    //CALCULA A ALTURA, USADO NO fatorDeBalanceamento
    public int calcularAltura(Node node) {
        if (node == null) {
            return 0; // Se o node for nulo, a altura é 0w
        } else {
            // Calcula a altura das subárvores esquerda e direita
            int alturaEsquerda = calcularAltura(node.getFilhoE());
            int alturaDireita = calcularAltura(node.getFilhoD());

            // A altura do node é o máximo entre as alturas das subárvores mais 1 para
            // incluir o próprio node
            return Math.max(alturaEsquerda, alturaDireita) + 1;
        }
    }

    //CALCULA O FB DE CADA NODE
    public void fatorDeBalanceamento(Node nodeAtual) {// Calcula o fator de balanceamento de cada nó já inserido na árvore
        nodeAtual.setBalanceamento(calcularAltura(nodeAtual.getFilhoE()) - calcularAltura(nodeAtual.getFilhoD()));
        if (nodeAtual.getFilhoD() != null) {
            fatorDeBalanceamento(nodeAtual.getFilhoD()); //verifica/setta o fb de todos os filhos do filho a direita, recursivamente
        }
        if (nodeAtual.getFilhoE() != null) {
            fatorDeBalanceamento(nodeAtual.getFilhoE()); //verifica/setta o fb de todos os filhos do filho a esquerda, recursivamente
        }
    }

    //CHAMA AS ROTAÇÕES
    public Node verificarBalanceamento(Node nodeAtual) {
        String id = "";

        if(nodeAtual.getCpf() != null){
            id = "cpf";
        }else if(nodeAtual.getNome() != null){
            id = "nome";
        }else if(nodeAtual.getData() != null){
            id = "data";
        }

        if (nodeAtual.getBalanceamento() == 2 && nodeAtual.getFilhoE().getBalanceamento() >= 0) {
            nodeAtual = rotacaoSimplesDireita(nodeAtual);

        } else if (nodeAtual.getBalanceamento() == 2 && nodeAtual.getFilhoE().getBalanceamento() < 0) {
            nodeAtual = rotacaoDuplaDireita(nodeAtual);

        } else if (nodeAtual.getBalanceamento() == -2 && nodeAtual.getFilhoD().getBalanceamento() <= 0 ) {
            nodeAtual = rotacaoSimplesEsquerda(nodeAtual);

        } else if (nodeAtual.getBalanceamento() == -2 && nodeAtual.getFilhoD().getBalanceamento() > 0) {
            nodeAtual = rotacaoDuplaEsquerda(nodeAtual);
        }

        //Após o balanceamento do nodeAtual, ou caso ele já esteja balanceado, ele vai procurar recursivamente nos filhos
        if (nodeAtual.getFilhoE() != null) { //esquerda
            verificarBalanceamento(nodeAtual.getFilhoE());
        }
        if (nodeAtual.getFilhoD() != null) { //direita
            verificarBalanceamento(nodeAtual.getFilhoD());
        }
        return nodeAtual; //A raiz anterior é substituida pela nova raiz, atualizada e balanceada
    }

    /* ============ </BALANCEAMENTO> ============= */
    /* ============ <ROTAÇÕES> ============= */

    //SIMPLES DIREITA
    public Node rotacaoSimplesDireita(Node node) {
        Node filhoE = node.getFilhoE();
        Node filhoExtra = filhoE.getFilhoD(); //filho a direita do filho a esquerda

        filhoE.setFilhoD(node); //o node a ser balanceado vira filho do filho a direita do seu filho a esquerda
        node.setFilhoE(filhoExtra); //o filho a direita do filho a esquerda vira filho a esquerda do node a ser balanceado

        if(filhoExtra != null){ //se existir filho extra (filho a direita do filho a esquerda)
            filhoExtra.setPai(node); //setta o node a ser balanceado como pai do filho extra
        }

        Node pai = node.getPai(); //criação de um novo node, tendo como referência o node pai do node a ser balanceado.
        filhoE.setPai(pai); //o novo pai do filho a esquerda vira o pai do node a ser balanceado

        if(pai != null){
            if(pai.getFilhoD() == node){
                pai.setFilhoD(filhoE); //setta o filho a esquerda do node a ser balanceado como sendo filho a direita do pai
            }else{
                pai.setFilhoE(filhoE); //setta o filho a esquerda do node a ser balanceado como sendo filho a esquerda do pai
            }

        }else{
            filhoE.setRaiz(true); //setta o parâmetro isRaiz para true caso o node esteja sendo balanceado e receba o valor da raiz.
        }

        node.setPai(filhoE); //o pai do node a ser balanceado vira seu antigo filho a esquerda
        fatorDeBalanceamento(node); //chama o fatorDeBalanceamento para checar se mais balanceamento necessita ser feito
        fatorDeBalanceamento(filhoE); //chama o fatorDeBalanceamento para checar se mais balanceamento necessita ser feito no seu filho

        return filhoE;
    }
    

    //SIMPLES ESQUERDA
    public Node rotacaoSimplesEsquerda(Node node) {
        Node filhoD = node.getFilhoD();
        Node filhoExtra = filhoD.getFilhoE(); //filho a esquerda do filho a direita
        
        filhoD.setFilhoE(node); //o node a ser balanceado vira filho do filho a esquerda do seu filho a direita
        node.setFilhoD(filhoExtra); //o filho a esquerda do filho a direita vira filho a direita do node a ser balanceado

        if(filhoExtra != null){ //se existir filho extra (filho a esquerda do filho a direita)
            filhoExtra.setPai(node); //setta o node a ser balanceado como pai do filho extra
        }

        Node pai = node.getPai(); //criação de um novo node, tendo como referência o node pai do node a ser balanceado.
        filhoD.setPai(pai); //o novo pai do filho a direita vira o pai do node a ser balanceado

        if(pai != null){
            if(pai.getFilhoE() == node){
                pai.setFilhoE(filhoD); //setta o filho a direita do node a ser balanceado como sendo filho a esquerda do pai
            }else{
                pai.setFilhoD(filhoD); //setta o filho a direita do node a ser balanceado como sendo filho a direita do pai
            }
        }else{
            filhoD.setRaiz(true); //setta o parâmetro isRaiz para true caso o node esteja sendo balanceado e receba o valor da raiz.
        }

        node.setPai(filhoD); //o pai do node a ser balanceado vira seu antigo filho a esquerda
        fatorDeBalanceamento(node); //chama o fatorDeBalanceamento para checar se mais balanceamento necessita ser feito
        fatorDeBalanceamento(filhoD); //chama o fatorDeBalanceamento para checar se mais balanceamento necessita ser feito no seu filho
        
        return filhoD;
    }

    // DUPLA DIREITA
    public Node rotacaoDuplaDireita(Node node) {
        Node filhoE = node.getFilhoE();
        node.setFilhoE(rotacaoSimplesEsquerda(filhoE));
        Node filhoRotacionado = rotacaoSimplesDireita(node);
        return filhoRotacionado;
    }

    // DUPLA ESQUERDA
    public Node rotacaoDuplaEsquerda(Node node) {
        Node filhoD = node.getFilhoD();
        node.setFilhoD(rotacaoSimplesDireita(filhoD));
        Node filhoRotacionado = rotacaoSimplesEsquerda(node);
        return filhoRotacionado;
    }
    
    /* ============ </ROTAÇÕES> ============= */
    /* =============== <SHOW> ============== */

    public void printArvoreCPF(Node nodeAtual, String identacao) {
        if (nodeAtual != null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + nodeAtual.getCpf());

            // Chama a função recursivamente para os filhos
            printArvoreCPF(nodeAtual.getFilhoE(), identacao + "  "); // Para o filho esquerdo, aumenta a indentação
            printArvoreCPF(nodeAtual.getFilhoD(), identacao + "  "); // Para o filho direito, aumenta a indentação
        } else if (nodeAtual == null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + "-");
        }
    }

    public void printArvoreNome(Node nodeAtual, String identacao) {
        if (nodeAtual != null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + nodeAtual.getNome());

            // Chama a função recursivamente para os filhos
            printArvoreNome(nodeAtual.getFilhoE(), identacao + "  "); // Para o filho esquerdo, aumenta a indentação
            printArvoreNome(nodeAtual.getFilhoD(), identacao + "  "); // Para o filho direito, aumenta a indentação
        } else if (nodeAtual == null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + "-");
        }
    }

    public void printArvoreData(Node nodeAtual, String identacao) {
        if (nodeAtual != null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + converterData(nodeAtual.getData()));

            // Chama a função recursivamente para os filhos
            printArvoreData(nodeAtual.getFilhoE(), identacao + "  "); // Para o filho esquerdo, aumenta a indentação
            printArvoreData(nodeAtual.getFilhoD(), identacao + "  "); // Para o filho direito, aumenta a indentação
        } else if (nodeAtual == null) {
            // Imprime o valor do node atual com a indentação apropriada
            System.out.println(identacao + "-");
        }
    }

    /* ============= </SHOW> ============= */
    /* ============ <SEARCH> ============= */

    /**************** CONSULTA UMA PESSOA DO CPF INDICADO ****************/
    String nome, cidade;
    long cpf, rg;
    LocalDate data;

    private void getRegistroLista(int code) { // PUXA VALORES DO NODE QUE QUER PROCURAR PARA VARIÁVEIS LOCAIS
        nome = imp.getNome(code);
        cidade = imp.getCidade(code);
        cpf = imp.getCpf(code);
        rg = imp.getRg(code);
        data = imp.getData(code);
    }

    // FAZ A PROCURA NA ÁRVORE CPF
    public void buscaCPF(long cpf, Node currentNode) {
        if (rootCPF == null) { // NÃO EXISTE NODES NA ÁRVORE
            System.out.println("Nenhum CPF foi inserido. Tente adicionar um CPF antes ;)");

        } else if (cpf == currentNode.getCpf()) { // CPF = AO NODE ATUAL SENDO VERIFICADO
            getRegistroLista(currentNode.getCode());
            System.out.println("\nNome: " + nome + "\nCidade de Nascimento: " + cidade + "\nCPF: " + cpf +
                    "\nRG: " + rg + "\nData de Nascimento: " + converterData(data));

        } else if (cpf < currentNode.getCpf()) {// VERIFICA ESQUERDA
            if (currentNode.getFilhoE() != null) {
                buscaCPF(cpf, currentNode.getFilhoE());
            } else if (currentNode.getFilhoE() == null) {
                System.out.println("CPF ainda nao foi inserido na árvore");
            }

        } else if (cpf > currentNode.getCpf()) {// VERIFICA DIREITA
            if (currentNode.getFilhoD() != null) {
                buscaCPF(cpf, currentNode.getFilhoD());
            } else if (currentNode.getFilhoD() == null) {
                System.out.println("CPF ainda nao foi inserido na árvore");
            }
        }
    }

    //CHECKA SE UM CPF JÁ EXISTE
    boolean verdade;
    public void checkaCPF(long cpf, Node currentNode) {
        if (rootCPF == null) { // NÃO EXISTE NODES NA ÁRVORE
            verdade = false;
            return;

        } else if (cpf == currentNode.getCpf()) { // CPF = AO NODE ATUAL SENDO VERIFICADO
            verdade = true;
            return;

        } else if (cpf < currentNode.getCpf()) {// VERIFICA ESQUERDA
            if (currentNode.getFilhoE() != null) {
                checkaCPF(cpf, currentNode.getFilhoE());
            } else if (currentNode.getFilhoE() == null) {
                verdade = false;
                return;
            }

        } else if (cpf > currentNode.getCpf()) {// VERIFICA DIREITA
            if (currentNode.getFilhoD() != null) {
                checkaCPF(cpf, currentNode.getFilhoD());
            } else if (currentNode.getFilhoD() == null) {
                verdade = false;
                return;
            }
        }
    }

    public void zeraVerdade(){
        verdade = false;
    }


    public void buscaNome(String string, Node nodeAtual) {
            Node filhoE = nodeAtual.getFilhoE();
            Node filhoD = nodeAtual.getFilhoD();

        if(rootNome != null){
            if(nodeAtual.getNome().startsWith(string)){
                getRegistroLista(nodeAtual.getCode());
                System.out.println("\nNome: " + nome + "\nCidade de Nascimento: " + cidade + "\nCPF: " + cpf + "\nRG: " + rg + "\nData de Nascimento: " + converterData(data));
                System.out.println("**********");
              
                if(filhoE != null ){
                    buscaNome(string, filhoE); // CONTINUA PELA ESQUERDA
                }
                if(filhoD != null) {
                    buscaNome(string, filhoD); // CONTINUA PELA DIREITA
                }

            }else if (nodeAtual.getNome().startsWith(string) == false) {
                if(filhoE != null ){
                    buscaNome(string, filhoE); // CONTINUA PELA ESQUERDA
                }
                if(filhoD != null) {
                    buscaNome(string, filhoD); // CONTINUA PELA DIREITA
                }
            }
            else {
                System.out.println("Nenhum nome foi encontrado...");
            }
        }
        else{
            System.out.println("Nenhum nome foi inserido. Tente adicionar um nome antes...");
        }
    }

    public void buscaData(LocalDate startDate, LocalDate endDate, Node nodeAtual) {
        Node filhoE = nodeAtual.getFilhoE();
        Node filhoD = nodeAtual.getFilhoD(); 

        if(rootNome != null){
            if(nodeAtual.getData().isAfter(startDate.minusDays(1))
                && nodeAtual.getData().isBefore(endDate.plusDays(1))) { 

                getRegistroLista(nodeAtual.getCode());
                System.out.println("\nNome: " + nome + "\nCidade de Nascimento: " + cidade + "\nCPF: " + cpf +
                                 "\nRG: " + rg + "\nData de Nascimento: " + converterData(data));
                System.out.println("**********");
            
                if(filhoE != null ){
                    buscaData(startDate, endDate, filhoE); // CONTINUA PELA ESQUERDA
                }
                if(filhoD != null ){
                    buscaData(startDate, endDate, filhoD); // CONTINUA PELA DIREITA
                }

            }else if(nodeAtual.getData().isAfter(startDate.minusDays(1)) == false
                    || nodeAtual.getData().isBefore(endDate.plusDays(1)) == false ) {
            
                if(filhoE != null ){
                    buscaData(startDate, endDate, filhoE); // CONTINUA PELA ESQUERDA
                }
                if(filhoD != null ){
                    buscaData(startDate, endDate, filhoD); // CONTINUA PELA DIREITA
                }

            }else {
                System.out.println("Nenhum registro foi encontrado...");
            }

        }else {
            System.out.println("Nenhum registro foi inserido. Tente adicionar alguns registros antes...");
        }
    } 

    /* ============ </SEARCH> ============= */
    /* ============ <CONVERSÃO DE DATAS> ============= */

    public static String converterData(LocalDate data) {   
        DateTimeFormatter formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = data.format(formatoDesejado);
        return dataFormatada;
    }

    public static LocalDate converterDataInvert(String data) {   
        DateTimeFormatter formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formatoDesejado);
    }
    /* ============ </CONVERSÃO DE DATAS> ============= */
}