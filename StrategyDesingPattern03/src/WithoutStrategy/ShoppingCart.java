package WithoutStrategy;

public class ShoppingCart {
    // this shopping cart class will have a method called checkout in which we will put the
    // final added amount
    PaymentClass payment=new PaymentClass();

    public void checkout(int amount,String Paymentstragety )
    {

         payment.pay(amount,Paymentstragety);
    }


}
