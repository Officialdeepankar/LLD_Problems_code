package LISKOV03.following;

public class SavingAccount extends WithdrawableAccount{

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

    public SavingAccount(Integer balance) {
        super(balance);
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
}
