package LISKOV03.Notfollowing;

import javax.swing.plaf.IconUIResource;

public class CurrentAccount extends  Account{
    public CurrentAccount(Integer balance) {
        super(balance);
    }

    @Override
    public void deposit(Integer amount) {
        if(amount>0)
        {
            Integer finalamount=amount+ this.getBalance();
            this.setBalance(finalamount);
        }else{
           throw  new IllegalArgumentException("The given amount is less than 0");
        }

    }

    @Override
    public void withdrawl(Integer amount) {
       if(amount<0|| amount>this.getBalance())
       {
           throw  new IllegalArgumentException("Please check the amount value");
       }
        Integer finalbalance= this.getBalance()-amount;
         this.setBalance(finalbalance);
        System.out.println("Deducted "+amount+" from you account "+" final balance is "+finalbalance);
    }

    @Override
    public void getDetails() {
        System.out.println("Current balance : "+ this.getBalance());
    }


}

