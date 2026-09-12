package SingleResponsiblity01.NotfollowingSingleResponsiblity;

import java.util.ArrayList;
import java.util.List;

/*

One Shopping cart contains many products right , so we can say shopping cart and product has many to one relationship

i.e --->   Product (1)---------*(shopping cart)
// shopping cart class ka main kaam kiya hota hein think ?
---> Product ko hold karna
----> Product ka total dena
-----> Give final price with after discounts and offer

What it should not be responsible for ?

Handling payments
Saving Information to database
Printing invoices.

But here we will try to break this SingleResponsiblity principle .



 */
public class ShoppingCart {


    public ShoppingCart()
    {

    }


   private List<Product>ShoppingCart=new ArrayList<>();

     public void  AddProduct(Product p)
       {
           ShoppingCart.add(p);

       }


       public void RemoveProduct(Product p)
       {
           ShoppingCart.remove(p);
       }


       public Integer TotalCost()
       {
           Integer total=0;
           for (Product p : ShoppingCart)
           {
               total+=p.getPrice();

           }
           return total;
       }




    public void InvoicePrint()
       {
           System.out.println("Printing invoice = "+ TotalCost());
       }


       public void handlePayment()
       {
           System.out.println("Handling payments");
       }

}
