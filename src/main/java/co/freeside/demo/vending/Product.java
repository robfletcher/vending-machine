package co.freeside.demo.vending;

public enum Product {

    ApolloBar(65), MolokoPlus(135), ChocolateSaltyBalls(90), BootySweat(60), EverlastingGobstopper(120), Slurm(75);
    
    private final int price;

    private Product(int price) {
        this.price = price;
    }
    
    int getPrice() {
        return price;
    }
    
}
