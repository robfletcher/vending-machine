package co.freeside.demo.vending;

import java.util.*;

/**
 * Represents a vending machine that has various products.
 */
public class VendingMachine {
    
    private int credit = 0;
    
    Map<String, Product> showProducts() {
        return Collections.emptyMap();
    }
    
    int readCredit() {
        return credit;
    }
    
    void insertCoin(Coin coin) {
        credit += coin.getValue();
    }
    
    void purchase(String productCode) {

    }

    Collection<Coin> returnCoins() {
        return Collections.emptyList();
    }
    
}
