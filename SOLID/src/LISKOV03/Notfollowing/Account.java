package LISKOV03.Notfollowing;

public abstract class Account {

    private Integer  balance=0;
    public Account(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public abstract void deposit(Integer amount);
    public abstract void withdrawl(Integer amount);
    public abstract void getDetails();

}
