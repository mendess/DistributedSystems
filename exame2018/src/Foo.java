import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Foo implements Jogo {
    private List<Jog> jogadores;
    private ReentrantLock lock = new ReentrantLock();
    private Condition waiting;

    // TODO: missing reset

    public Foo() {
        this.jogadores = new ArrayList<>(30);
        this.waiting = lock.newCondition();
    }

    public List<String> inscrever(String nome) {
        lock.lock();
        try {
            jogadores.add(new Jog(nome));
            while (!(jogadores.size() >= 30
                    || (jogadores.size() >= 20
                        && jogadores.size() % 2 == 0
                        && jogadores.get(0).time.until(LocalDateTime.now(), ChronoUnit.MINUTES) >= 1)))
            {
                this.waiting.await();
            }
            this.waiting.signalAll();
            return jogadores.stream().map(j -> j.name).collect(Collectors.toList());
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
        return null;
    }

    private class Jog {
        private String name;
        private LocalDateTime time;

        Jog(String name) {
            this.name = name;
            this.time = LocalDateTime.now();
        }
    }
}
