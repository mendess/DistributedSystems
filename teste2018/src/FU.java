import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class FU implements Controlador {

    private ReentrantLock lock = new ReentrantLock();
    private List<Condition> whereWeAt;
    private int posicao;
    private boolean traveling;
    private int capacidade;
    private final int MAX_CAP;

    public FU() {
        lock = new ReentrantLock();
        whereWeAt = new ArrayList<>();
        for (int i = 0; i < 4; i++) whereWeAt.add(lock.newCondition());
        posicao = 0;
        capacidade = 0;
        MAX_CAP = 2;
        traveling = false;
    }

    public void quero_viajar(int origem, int destino) {
        lock.lock();
        while (posicao != origem || traveling || capacidade >= MAX_CAP) {
            try {
                whereWeAt.get(origem).await();
            } catch (InterruptedException ignored) {
            }
        }
        capacidade++;
        lock.unlock();
    }

    public void quero_sair(int destino) {
        lock.lock();
        while(posicao != destino || traveling) {
            try {
                whereWeAt.get(destino).await();
            } catch (InterruptedException ignored) {
            }
        }
        capacidade--;
        lock.unlock();
    }

    public void partida() {
        lock.lock();
        traveling = true;
        lock.unlock();
    }

    public void chegada() {
        lock.lock();
        posicao = (posicao + 1) % 4;
        whereWeAt.get(posicao).signalAll();
        traveling = false;
        lock.unlock();
    }
}

