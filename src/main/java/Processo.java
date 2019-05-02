 /* Sistemas Operacionais
  * Data: 02/05/2019
  * Autores: Daniela Amaral e Vinicius Lima
  * Problema: simular o funcionameto do algoritmo de escalamento de processos Round Robin com entrada de dados por arquivo txt e gerar grafico de resultado
  * Operacao de e/s: 3 unidades de tempo
  * Troca de contexto: 1 unidade de tempo
  */

import java.util.*;

public class Processo {
    private int id;
    private int tempoChegada;
    private int tempoExecucao;
    private int contTempoExecutado;
    private List<Integer> operacaoES = new ArrayList<>();
    private int fatiaAtual;
    public int fatiaPadrao;

    public Processo(int idProcesso, int fatia, int chegada, int execucao) {
        id = idProcesso;
        fatiaPadrao = fatia;
        fatiaAtual = fatiaPadrao;
        tempoChegada = chegada;
        tempoExecucao = execucao;
    }

    public int getId() {
        return id;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public int getTempoExecucao() {
        return tempoExecucao;
    }

    public void addOperacaoES(int opES) {
        this.operacaoES.add(opES);
    }

    public int getContTempoExecutado() {
        return contTempoExecutado;
    }

    public int getFatiaAtual() {
        return fatiaAtual;
    }


    //calcula a fatia de tempo dada ao processo de acordo com suas operacoes de entrada e saida
    public int getFatiaTempo() {
        int fatia;
        if(operacaoES.size() > 0){
            fatia = this.operacaoES.remove(0);
        } else{
            fatia = tempoExecucao-contTempoExecutado;
        }
        if(fatia > fatiaAtual) {
            fatia = fatiaAtual;
            fatiaAtual = fatiaPadrao;
        }
        else{
            fatiaAtual = fatiaAtual - fatia;
            if(fatiaAtual <= 0) {
                fatiaAtual = fatiaPadrao;
            }
        }
        contTempoExecutado += fatia;
        return fatia;
    }
}
