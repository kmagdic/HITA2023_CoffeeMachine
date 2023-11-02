package _karlo_dragan.banksystem;

import java.util.*;

public class Client {
    private String firstName;
    private String lastName;
    private String address;
    private String oib;

    private List<Account> accountsList = new ArrayList<>();
    public Client(String firstName, String lastName, String address, String oib) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.oib = oib;

     /*  Account a = new Account("Current", 0);
       addAccount(a);*/
       // accountsList.add(a)
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public void addAccount(Account account ){
        accountsList.add(account);
    }

    public List<Account> getAccountsList() {
        return accountsList;
    }

    /*    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", oib='" + oib + '\'' +
                ", accountsList=" + accountsList +
                '}';
    }*/
}
