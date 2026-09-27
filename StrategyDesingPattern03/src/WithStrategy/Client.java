package WithStrategy;

public class Client {


    public static void main(String[] args) {

        Paymentclass UPIPyament= new UPI();
        Paymentclass CreditCardPayment=new CreditCard();
        Shoppingcart cart=new Shoppingcart();
        cart.setPaymentStrategy(UPIPyament);
       // cart.checkout(1000);
        cart.checkout(1000);


    }
}
