package ex2;

import ex1.BoundedBuffer;
import ex1.Consumer;
import ex1.Producer;
import utils.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException{
        List<Consumer> consumers = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();
        List<Thread> producerThreads = new ArrayList<>();
        BoundedBuffer buff = new BoundedBuffer(10, false);
        List<Record> records = new ArrayList<>();
        Thread monitor = startMonitor(consumers, producers, buff);
        for(int numP = 1; numP < 10; numP++){
            consumers.clear();
            producers.clear();
            consumerThreads.clear();
            producerThreads.clear();
            int numC = 10 - numP;
            Value[] valuesP = unitsArray(numP);
            Value[] valuesC = unitsArray(numC);
            System.out.println(Arrays.toString(valuesP));
            System.out.println(Arrays.toString(valuesC));
            for(int i = 0; i < numP; i++){
                producers.add(new Producer(buff, valuesP[i]));
                producerThreads.add(new Thread(producers.get(i)));
            }
            for(int i = 0; i < numC; i++){
                consumers.add(new Consumer(buff, valuesC[i]));
                consumerThreads.add(new Thread(consumers.get(i)));
            }
            long time = System.nanoTime();
            consumerThreads.forEach(Thread::start);
            producerThreads.forEach(Thread::start);
            for(Thread consumer : consumerThreads) consumer.join();
            for(Thread producer : producerThreads) producer.join();
            System.out.println();
            records.add(new Record(numP, numC, System.nanoTime() - time));
        }
        monitor.stop();
        records.sort(Comparator.naturalOrder());
        System.out.println("#Records: " + records.size());
        System.out.println("Best Record: " + records.get(0));
    }

    private static Thread startMonitor(List<Consumer> consumers, List<Producer> producers, BoundedBuffer buff){
        Thread clear = new Thread(() -> {
            while(true){
//                System.out.print("\033[H\033[2J");
//                System.out.flush();
                StringBuilder sb = new StringBuilder();
                String ps = producers.stream()
                                     .map(Producer::getValue)
                                     .map(x -> "" + x)
                                     .reduce("", (x, acc) -> acc + ", " + x);
                sb.append("Prod: ").append(ps).append("\n");
                String cs = consumers.stream()
                                     .map(Consumer::getValue)
                                     .map(x -> "" + x)
                                     .reduce("", (x, acc) -> acc + ", " + x);
                sb.append("Cons: ").append(cs).append("\n");
                sb.append("Buff: ").append(buff.toString());
                System.out.println(sb.toString());
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ignored){

                }
            }
        });
        clear.start();
        return clear;
    }

    private static Value[] unitsArray(int size){
        Value[] r = new Value[size];
        if(100 % size == 0){
            for(int i = 0; i < size; i++) r[i] = new Value(100 / size);
            assert Arrays.stream(r).mapToInt(Value::getValues).sum() == 100;
        }else{
            for(int i = 0; i < size - 1; i++) r[i] = new Value(100 / size);
            r[size - 1] = new Value(100 / size + 100 % size);
            assert Arrays.stream(r).mapToInt(Value::getValues).sum() == 100;
        }
        return r;
    }

    private static class Record implements Comparable<Record> {

        int numP;
        int numC;
        long time;

        public Record(int numP, int numC, long time){
            this.numP = numP;
            this.numC = numC;
            this.time = time;
            System.out.println(this.toString());
        }


        @Override
        public int compareTo(Record record){
            return Long.compare(this.time, record.time);
        }

        @Override
        public String toString(){
            return "Record{" +
                   "numP=" + numP
                   + ", numC=" + numC
                   + ", time=" + time / 1000000000
                   + '}';
        }
    }
}
