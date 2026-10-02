package SingleResponsiblity01.FollowingSingleResponsibilty;

import SingleResponsiblity01.NotfollowingSingleResponsiblity.Product;

public class PaymentProcessor {
    public void handlePayment(ShoppingCart cart) {
        System.out.println("Handling payments for total: " + cart.TotalCost());
    }
}
