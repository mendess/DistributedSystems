package ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {
    private ReentrantLock lock;
    private Condition reader;
    private Condition writer;
    private int readers;
    private boolean writing;

    public RWLock() {
        this.lock = new ReentrantLock();
        this.reader = this.lock.newCondition();
        this.writer = this.lock.newCondition();
        this.readers = 0;
        this.writing = false;
    }

    public void readLock() {
        this.lock.lock();
        this.readers++;
        try {
            while (this.writing) this.reader.await();
        } catch (InterruptedException ignored) {
        }
        this.lock.unlock();
    }

    public void readUnLock() {
        this.lock.lock();
        if (this.readers > 0) this.readers--;
        if (!this.writing){
            this.reader.signalAll();
            this.writer.signalAll();
        }
        this.lock.unlock();
    }

    public void writeLock() {
        this.lock.lock();
        try {
            while (this.writing || this.readers > 0)
                this.writer.await();
        } catch (InterruptedException ignored) {
        }
        this.writing = true;
        this.lock.unlock();
    }

    public void writeUnlock(){
        this.lock.lock();
        this.writing = false;
        this.reader.signalAll();
        this.writer.signalAll();
        this.lock.unlock();
    }
}