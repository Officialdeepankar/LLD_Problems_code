package SingleResponsiblity01.FollowingSingleResponsibilty;

public class InvoicePrinter {
    public void printInvoice(ShoppingCart cart) {
        System.out.println("Printing invoice = " + cart.TotalCost());
    }
}
