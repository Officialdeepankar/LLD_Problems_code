package WithStrategy;

import WithoutStrategy.PaymentClass;

public class Shoppingcart {

    Paymentclass payment;

    void checkout(int amount)
    {
        payment.pay(amount);
    }

    void setPaymentStrategy(Paymentclass payment)
     {
         this.payment=payment;
     }
}
