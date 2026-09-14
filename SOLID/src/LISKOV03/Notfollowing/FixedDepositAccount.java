package LISKOV03.Notfollowing;

public class FixedDepositAccount extends Account{
    public FixedDepositAccount(Integer balance) {
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
        throw new IllegalArgumentException("You cant withdraw money from the fixed deposit Account");
    }

    @Override
    public void getDetails() {
        System.out.println("Current balance : "+ this.getBalance());
    }
}
