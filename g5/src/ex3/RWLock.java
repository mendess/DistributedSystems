package ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

    private ReentrantLock lock;
    private Condition reader;
    private Condition writer;
    private int readers;
    private boolean writing;
    private final int MAX = 5;
    private int readsSinceLastWrite;
    private int writesSinceLastRead;
    private int readersRequesting;
    private int writersRequesting;

    public RWLock(){
        this.lock = new ReentrantLock();
        this.reader = this.lock.newCondition();
        this.writer = this.lock.newCondition();
        this.readers = 0;
        this.writing = false;
    }

    public void readLock(){
        this.lock.lock();
        this.readersRequesting++;
        try{
            while(this.writing || (this.writersRequesting > 0 && this.readsSinceLastWrite >= MAX))
                this.reader.await();
        }catch(InterruptedException ignored){ }

        this.readersRequesting--;
        this.readers++;

        this.writesSinceLastRead = 0;
        this.readsSinceLastWrite++;

        this.lock.unlock();
    }

    public void readUnLock(){
        this.lock.lock();
        if(this.readers > 0) this.readers--;
        if(this.readers == 0){
            this.writer.signalAll();
        }
        this.lock.unlock();
    }

    public void writeLock(){
        this.lock.lock();
        try{
            this.writersRequesting++;
            while(this.writing || this.readers > 0 || (this.readersRequesting > 0 && this.writesSinceLastRead >= MAX))
                this.writer.await();
        }catch(InterruptedException ignored){ }

        this.writersRequesting--;
        this.writing = true;

        this.readsSinceLastWrite = 0;
        this.writesSinceLastRead++;

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
