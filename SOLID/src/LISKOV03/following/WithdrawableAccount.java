package LISKOV03.following;

public abstract class WithdrawableAccount extends NonWithdrawableAccount{
    public WithdrawableAccount(Integer balance) {
        super(balance);
    }
    public abstract void withdrawl(Integer amount);

}
