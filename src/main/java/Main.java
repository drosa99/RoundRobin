 /* Sistemas Operacionais
 * Data: 02/05/2019
 * Autores: Daniela Amaral e Vinicius Lima
 * Problema: simular o funcionameto do algoritmo de escalamento de processos Round Robin com entrada de dados por arquivo txt e gerar grafico de resultado
 * Operacao de e/s: 3 unidades de tempo
 * Troca de contexto: 1 unidade de tempo
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static List<Processo> processos; // lista dos objetos processos
    private static List<Integer> filaDeExecucao = new LinkedList<>();; // lista com a fila de processos para serem executados

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String arquivo;
        while(true) {
            System.out.println("Digite nome do arquivo \nArquivos disponiveis,: teste, teste1, teste2, teste3, teste4");
            arquivo = in.nextLine();

            try {
                recebeDados(arquivo); //chamada para leitura:
                roundRobin(); // chamada do metodo q printa no console o resultado
            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado.");
            } catch (NumberFormatException e) {
                System.out.println("Formato de dados incorreto!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void roundRobin() {
        String grafico = "";
        int tempoCorrente = 1;

        while(temProcessosAguardando()) {
            checaNovoProcesso(tempoCorrente);
            if(filaDeExecucao.size() == 0){
                //se nao ha nada na fila coloca -
                grafico += "-";
            }
            else {
                // se tem processo na fila, puxa o primeiro
                int idProcessoParaExecutar = filaDeExecucao.remove(0);
                Processo processo = buscarProcessoPorId(idProcessoParaExecutar);
                int fatiaTempo = processo.getFatiaTempo();
                grafico += "C";  //troca de contexto
                for(int i=0; i<fatiaTempo; i++){
                    //escreve o id do processo no grafico tantas vezes quanto a fatia de tempo dele
                    grafico += processo.getId();
                }
                int proxTempo = tempoCorrente + fatiaTempo + 1; //passa o tempo da execucao do processo
                for(int i = tempoCorrente + 1; i < proxTempo; i++) {
                    checaNovoProcesso(i);
                }
                tempoCorrente = proxTempo; //atualiza o tempo para a troca de contexto
                //se processo ainda tem tempo para ser executado, coloca no final da fila de execucao
                if(processo.getContTempoExecutado() < processo.getTempoExecucao()) {
                    if(filaDeExecucao.isEmpty() && processo.getFatiaAtual() < processo.fatiaPadrao)  grafico += "C-";
                    filaDeExecucao.add(processo.getId());
                }
            }
            checaNovoProcesso(tempoCorrente); //ve se tem processo chegando
            tempoCorrente++;
        }
        System.out.println("Grafico de execução dos processos: \n" + grafico);
    }

    public static Processo buscarProcessoPorId(int id) {
        for(Processo processo: processos)
            if (processo.getId() == id) return processo;
        return null;
    }

    //verifica algum processo chegou, e adiciona seu id na fila de execução
    public static void checaNovoProcesso(int tempoCorrente) {
        for(Processo processo : processos){
            if (processo.getTempoChegada() == tempoCorrente) {
                filaDeExecucao.add(processo.getId());
            }
        }
    }
    
    public static boolean temProcessosAguardando() {
        for(Processo processo: processos)
            if (processo.getContTempoExecutado() < processo.getTempoExecucao()) {
                return true;
            }
        return false;
    }

    // le dados, cria objetos processo e bota na lista 
    public static void recebeDados(String arquivo) throws IOException {
        Scanner scanner = new Scanner(new FileReader("./testes/"+arquivo+".txt"));
        scanner.nextInt();
        int fatiaTempo = scanner.nextInt();
        if(scanner.hasNextLine()) scanner.nextLine();
        processos = new ArrayList<>();
        int idProcesso = 1;
        while(scanner.hasNextLine()) {
            String dados [];
            String linha = scanner.nextLine();
            if(linha.equals("")){ return; }
            dados = linha.split(" ");
            int tempoChegada = Integer.parseInt(dados[0]);
            int tempoExecucao = Integer.parseInt(dados[1]);
            Processo processo = new Processo(idProcesso++, fatiaTempo, tempoChegada, tempoExecucao);
            for(int i = 2; i < dados.length; i++) {
                int operacaoES = Integer.parseInt(dados[i]);
                if(operacaoES !=0 ) {
                    processo.addOperacaoES(operacaoES);
                }
            }
            processos.add(processo);
        }
    }
}
