package _karlo_dragan.banksystem;

public class SavingAccount extends Account {
    private double interest;

    public SavingAccount(String accountName, double balance, double interest) {
        super(accountName, balance);
        this.interest = interest;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void whitdrawMoney(double money) {
        super.whitdrawMoney(money);
    }

    public void depositMoney(double money) {
        super.depositMoney(money);
    }

    @Override
    public String toString() {
        super.toString();
        return "SavingAccount{" +
                "interest=" + interest +
                '}';
    }
}
