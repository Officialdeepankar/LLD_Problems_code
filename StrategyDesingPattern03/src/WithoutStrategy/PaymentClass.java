package WithoutStrategy;

public class PaymentClass {
 // 1) suppose we have only option to pay throughcredit card


    // 2) suppose i can do payment with upi also



    public void pay(int amount,String paymentmethod)
    {
        // logic to do payment for credit card
       // System.out.println("paying throught credit card ");


        if(paymentmethod=="UPI")
        {
            System.out.println("UPI Payment done");
        }else if (paymentmethod=="credit card"){
            System.out.println("credit card payment done");
        }

    }
}
