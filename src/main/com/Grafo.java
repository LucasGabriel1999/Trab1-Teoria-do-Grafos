package main.com;

//classe que contem as tres estruturas e os metodos principais do trabalho

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Grafo {

    public ArrayList<Usuario> listaUsuarios;//lista que armazena todos os usuarioss
    public int[][] matrizDP;
    public ArrayList<ArrayList<UsuarioSegue>> listaAD;
    public ArrayList<TreeSet<UsuarioSegue>> listaAVL;

    //metodos utilizados para teste
    public void insereUsuariosTeste() {

        this.inserirUsuario(new Usuario("a", 12));
        this.inserirUsuario(new Usuario("b", 13));
        this.inserirUsuario(new Usuario("c", 14));
        this.inserirUsuario(new Usuario("d", 15));
        this.inserirUsuario(new Usuario("e", 16));
        this.inserirUsuario(new Usuario("f", 17));
    }


    //metodos pedidos no problema
    public void inicializarGrafos() {
        //Lista geral
        this.listaUsuarios = new ArrayList<Usuario>();
        //Matriz
        this.matrizDP = new int[50][50];
        //Lista
        this.listaAD = new ArrayList<>();
        //AVL
        this.listaAVL = new ArrayList<>();

    }

    public void inserirUsuario(Usuario usuario) {//esta funcionando!
        int i;
        boolean b = true;

        //Verifica se ha o usuario na lista principal, se nao existir emite uma mensagem de erro

        for (i = 0; i < this.listaUsuarios.size(); i++) {
            if (usuario.getNome().equals(this.listaUsuarios.get(i).getNome())) {
                b = false;
                break;
            }

        }
        if (b) { // Se chegar até esse ponto significa que esse usuario não existe na lista ainda.
            this.listaUsuarios.add(usuario);//Insere o usuario na lista
            this.listaAD.add(new ArrayList<>());//Cria uma lista dentro da lista AD
            this.listaAVL.add(new TreeSet<>());//Cria uma arvore dentro da lista AVL
        } else {
            System.out.println("ERRO! O usuario já existe na lista!");
        }

    }

    public void inserirRelacao() {
        Scanner scanner = new Scanner(System.in);
        int tempoAmizade;
        int indice, indice2;//representam os indices Usuarios da relação
        boolean b = true;


        System.out.println("Digite o usuario 1:");
        String nome = scanner.next();
        System.out.println("Digite o usuario 2:");
        String nome2 = scanner.next();
        System.out.println("Informe o tempo de amizade:");
        tempoAmizade = scanner.nextInt();

        indice2 = this.retornaIndice(nome2);//pega o indice do uduario 2 para recuperar sua idade e inserir na classe UsuarioSegue
        UsuarioSegue usuarioSegue = new UsuarioSegue();
        usuarioSegue.setIndiceUsuario(indice2);
        usuarioSegue.setTempo(tempoAmizade);
        indice = this.retornaIndice(nome);

        if (indice >= 0) {
            for (int i = 0; i < this.listaAD.get(indice).size(); i++) {
                if (this.listaAD.get(indice).get(i).getIndiceUsuario() == this.retornaIndice(nome2)) {//Comparando se já existe uma relação
                    b = true;
                    System.out.println("Essa relação já existe, deseja atualiza-la? (s / n)");
                    String resposta = scanner.next();

                    if (resposta.equals("s")) {
                        System.out.println("Informe o novo tempo de amizade: ");
                        usuarioSegue.setTempo(scanner.nextInt());
                        b = false;
                        //Agora vamos inserir nas listas
                        this.listaAD.get(indice).add(usuarioSegue);//add na lista de adjacencias
                        this.listaAVL.get(indice).add(usuarioSegue);//add na lista de AVL
                        this.matrizDP[this.retornaIndice(nome)][this.retornaIndice(nome2)] = usuarioSegue.getTempo();//add o tempo na matriz de pesos
                    } else {
                        System.out.println("Relação não alterada!");
                    }
                    break;
                }
            }
            if (b) {//caso nao exista uma relação b se mantem verdadeiro
                this.listaAD.get(indice).add(usuarioSegue);//add na lista de adjacencias
                this.listaAVL.get(indice).add(usuarioSegue);//add na lista de AVL
                this.matrizDP[this.retornaIndice(nome)][this.retornaIndice(nome2)] = usuarioSegue.getTempo();//add o tempo na matriz de pesos
            }
        } else {
            System.out.println("Ocorreu algum erro, provavelmente o usuario nao esta na lista!");
        }
    }

    public void listarSeguidores(String nome) {
        int indice, i, idade;
        String aux;

        indice=this.retornaIndice(nome);
        //exibindo da listaAD e da Matriz DP

        //é seguido por:
        if(listaAD.get(indice).size()==0){
            System.out.println("Seguidores: ");
            for(i=0;i<listaAD.get(indice).size();i++){

                //pega o nome e a idade dos usuarios que seguem o usuario pedido
                aux=listaUsuarios.get(this.listaAD.get(indice).get(i).getIndiceUsuario()).getNome();
                idade=listaUsuarios.get(this.listaAD.get(indice).get(i).getIndiceUsuario()).getIdade();
                System.out.printf("Nome: %s, idade: %d\n", aux, idade);
            }
        }else{
            System.out.println("Este usuario não possui nenhum seguidor!");
        }
        //segue:



    }

    public void atualizarRelacao() {

    }

    public void removerUsuario() {

    }

    public void removerRelacao() {

    }

    public int retornaIndice(String nome) {//Função que retorna o indice do uisuario OBS: caso não ache ele retorna -1
        int i, indice = -1;
        for (i = 0; i < this.listaUsuarios.size(); i++) {
            if (nome.equals(this.listaUsuarios.get(i).getNome())) {
                indice = i;
            }
        }
        return indice;
    }


}