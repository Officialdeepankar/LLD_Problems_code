package SingleResponsiblity01.FollowingSingleResponsibilty;

import SingleResponsiblity01.NotfollowingSingleResponsiblity.Product;
import SingleResponsiblity01.NotfollowingSingleResponsiblity.ShoppingCart;

public class Client {


    public static void main(String []args) {
        Product Shampoo=new Product("Clinic Plus",2);
        Product earphones =new Product("earphones",1000);

        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.AddProduct(Shampoo);
        shoppingCart.AddProduct(earphones);

        System.out.println(shoppingCart.TotalCost());
        shoppingCart.handlePayment();
        shoppingCart.InvoicePrint();

    }
}
