package co.freeside.demo.vending;

public enum Coin {

    Penny(1), Nickel(5), Dime(10), Quarter(25);
    
    private int value;

    private Coin(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
