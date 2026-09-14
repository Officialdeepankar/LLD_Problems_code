package LISKOV03.following;

public abstract  class NonWithdrawableAccount {

    private Integer balance=0;
    public abstract void deposit(Integer amount);

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public NonWithdrawableAccount(Integer balance) {
        this.balance = balance;
    }
}
