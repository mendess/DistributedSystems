package ex2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {

    private Map<String,Item> items;
    private ReentrantLock lock;

    public Warehouse(){
        this.lock = new ReentrantLock();
        this.items = new HashMap<>();
    }

    public void supply(String item, int quantity){
        this.lock.lock();
        Item i = this.items.get(item);
        if(i == null)
            this.items.put(item, new Item(quantity, item));
        else
            i.add();
        this.lock.unlock();
    }

    public void consume(String[] items){
        for(String name : items){
            this.lock.lock();
            Item i = this.items.get(name);
            this.lock.unlock();
            if(i != null) i.take();
        }
    }

    @Override
    public String toString(){
        return "Warehouse{" +
               "items=" + items
               + '}';
    }

    private class Item {

        private final String name;
        private int amount;
        Condition isEmpty;
        ReentrantLock lock;

        private Item(int amount, String name){
            this.name = name;
            this.amount = amount;
            this.lock = new ReentrantLock();
            this.isEmpty = this.lock.newCondition();
        }

        private void take(){
            System.out.println("Taking from: " + this.name + ":" + this.amount);
            this.lock.lock();
            while(this.amount == 0){
                try{
                    this.isEmpty.await();
                }catch(InterruptedException ignored){
                }
            }
            this.amount--;
            this.lock.unlock();
            System.out.println("Took from: " + this.name + ":" + this.amount);
        }

        private void add(){
            this.lock.lock();
            this.amount++;
            this.isEmpty.signal();
            this.lock.unlock();
        }

        @Override
        public String toString(){
            return "" +  amount;
        }
    }
}
