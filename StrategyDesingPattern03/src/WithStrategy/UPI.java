package WithStrategy;



public class UPI extends Paymentclass {

    public UPI() {
    }

    @Override
    public void pay(int amount) {
        System.out.println("UPI payment logic processing ");
        System.out.println("UPI payment completed");
    }
}
