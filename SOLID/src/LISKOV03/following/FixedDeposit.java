package LISKOV03.following;

public class FixedDeposit extends NonWithdrawableAccount{
    public FixedDeposit(Integer balance) {
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
}
