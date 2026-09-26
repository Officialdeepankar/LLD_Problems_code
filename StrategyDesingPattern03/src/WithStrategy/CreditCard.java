package WithStrategy;

public class CreditCard extends Paymentclass{
    @Override
    public void pay(int amount) {
        System.out.println("Credit card  payment logic processing ");
        System.out.println("Credit card payment completed");
    }
}
