package t2_ivan.coffeemachine.bankclientdb;

public class Account {
    private String accountName;
    private double balance;
    private Client client;

    public Account(String accountName, double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void whitdrawMoney(double money){
        if(money > getBalance()){
            System.out.println("You can whitdraw only " + this.balance + " kn");
        }
        else {
            this.balance -= money;
            System.out.println("It has been paid out " + money + " kn");
        }
    }

    public void depositMoney(double money){
        this.balance += money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
