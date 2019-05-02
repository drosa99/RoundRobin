/* Sistemas Operacionais
 * Autores: Daniela Amaral e Vinicius Lima
 * Problema: simular o funcionameto do algoritmo de escalamento de processos Round Robin com entrada de dados por arquivo txt
 * Operacao de e/s: 3 unidades de tempo
 * Troca de contexto: 1 unidade de tempo
 */
import java.util.ArrayList;
import java.util.List;

public class Processo {
    private int id;
    private int tempoChegada;
    private int tempoExecucao;
    private int contadorTempoExecutado;
    private List<Integer> acessosES;
    private int fatiaAtual;
    public int fatiaPadrao;

    public Processo(int idProcesso, int fatia, int chegada, int execucao) {
        id = idProcesso;
        acessosES = new ArrayList<>();
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

    public void addAcessoES(int acessoES) {
        this.acessosES.add(acessoES);
    }

    public int getContadorTempoExecutado() {
        return contadorTempoExecutado;
    }

    public int getFatiaAtual() {
        return fatiaAtual;
    }

    /**
     * Calcula a fatia de tempo dada ao processo de acordo com suas operações de entrada e Saída,
     * levando em considereação a fatia de tempo informada no arquivo.
     * @return fatia de tempo dada ao processo naquele contexto específico.
     */
    public int getFatiaTempo() {
        int fatia  = !acessosES.isEmpty() ? this.acessosES.remove(0) : tempoExecucao-contadorTempoExecutado;
        if(fatia > fatiaAtual) {
            fatia = fatiaAtual;
            fatiaAtual = fatiaPadrao;
            contadorTempoExecutado += fatia;
            return fatia;
        }
        fatiaAtual = fatiaAtual - fatia;
        if(fatiaAtual <= 0) {
            fatiaAtual = fatiaPadrao;
        }
        contadorTempoExecutado += fatia;
        return fatia;
    }

    @Override
    public String toString() {
        return "Processo [id=P" + id + ", tempoChegada=" + tempoChegada + ", tempoExecucao=" + tempoExecucao
                + ", contatorTempoExecutado=" + contadorTempoExecutado + ", acessosES=" + acessosES + "]";
    }
}
