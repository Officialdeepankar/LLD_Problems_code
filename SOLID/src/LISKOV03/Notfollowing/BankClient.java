package LISKOV03.Notfollowing;

import java.awt.datatransfer.FlavorListener;
import java.util.ArrayList;
import java.util.List;

public class BankClient {
    public static void main(String args[]) {
        /*
         Bank will have list of accounts for the account holder
         suppose i have a list of 3 account
         1) Current Account
         2) Savings Account
         3) Fixed deposit account

         */


//        //1) Make a list of accounts
//        Account savingsAccount=new SavingsAccount(1000);
//        Account fixedDepositAccount=new FixedDepositAccount(2000);
//        Account currentAccount=new CurrentAccount(3000);

        List<Account>listofaccounts=new ArrayList<>();
//        listofaccounts.add(savingsAccount);
//        listofaccounts.add(fixedDepositAccount);
//        listofaccounts.add(currentAccount);
//
//        savingsAccount.withdrawl(200);//1000-200=800
//        currentAccount.withdrawl(400);//3000-400=2600
//        savingsAccount.getDetails();


        /*
        // This setup breaks the liskov principle as fixed deposit account cannot have withdrawl functionality.
        but its parent Account has withdrawl function.

        The child narrow downs the Parent functionality , which should not be the case .


         */




    }
}
