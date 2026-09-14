package LISKOV03.following;

import java.util.ArrayList;
import java.util.List;

public class BankClient {

    public static void main(String[] args) {

        NonWithdrawableAccount fixedDeposit=new FixedDeposit(1000);
        WithdrawableAccount savingaccount=new SavingAccount(2000);
        WithdrawableAccount currentAccount=new CurrentAccount(4000);

        // we will maintain two list here at client side . one with withdrawable account list another with non withdrawable
        // account list


        List<NonWithdrawableAccount>nonWithdrawableAccounts=new ArrayList<>();
        List<WithdrawableAccount>withdrawableAccounts=new ArrayList<>();



    }
}
