package _karlo_dragan.banksystem;

public class CurrentAccount extends Account {

    private int allowedMinus;

    public CurrentAccount (String accountName, double balance, int allowedMinus) {
        super(accountName, balance);
        this.allowedMinus = allowedMinus;
    }

    public int getAllowedMinus() {
        return allowedMinus;
    }

    public void setAllowedMinus(int allowedMinus) {
        this.allowedMinus = allowedMinus;
    }

    public void whitdrawMoney(double money){
        if(money > (getBalance() + allowedMinus*(-1))) {
            System.out.println("You don't have enough funds, you can withdraw " + (getBalance() + allowedMinus*(-1)));
        }
        else {
            super.whitdrawMoney(money);
        }
    }
    public void depositMoney(double money){
        super.depositMoney(money);
    }

}
