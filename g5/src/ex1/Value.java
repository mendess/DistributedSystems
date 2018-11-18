package utils;

public class Value {

    private int values;

    public Value(int values){
        this.values = values;
    }

    public boolean hasValues(){
        return this.values > 0;
    }

    public int getValues(){
        return this.values;
    }

    public void decrement(){
        this.values--;
    }

    @Override
    public String toString(){
        return values + "";
    }
}
