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
    private int readersPriority;
    private int writersPriority;
    private int readersRequest;
    private int writersRequest;

    public RWLock(){
        this.lock = new ReentrantLock();
        this.reader = this.lock.newCondition();
        this.writer = this.lock.newCondition();
        this.readers = 0;
        this.writing = false;
    }

    public void readLock(){
        this.lock.lock();
        this.readersPriority++;
        try{
            while(this.writing || this.writersRequest > 0 && this.readersPriority >= MAX)
                this.reader.await();
        }catch(InterruptedException ignored){
        }
        this.readersRequest--;
        this.readers++;

        this.writersPriority = 0;
        this.readersPriority++;

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
            this.writersRequest++;
            while(this.writing || this.readers > 0 || this.readersRequest > 0 && writersPriority >= MAX)
                this.writer.await();

            this.writersRequest--;
            this.writing = true;

            this.readersPriority = 0;
            this.writersPriority++;
        }catch(InterruptedException ignored){
        }
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
