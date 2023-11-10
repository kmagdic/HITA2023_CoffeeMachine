package _karlo_dragan.banksystem;

import java.util.List;
import java.util.Scanner;

public class ConsoleMainBank {
    static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {

        Bank adiko = new Bank("Adiko", "Banfica");
        System.out.println("Bank " + adiko.getName() + " is generated\n");

        int accountType;
        int choice = 0;

        while (choice != 7){
            System.out.println("1 - Add the new client");
            System.out.println("2 - Print all clients");
            System.out.println("3 - Add account to the client");
            System.out.println("4 - Find the client by first name");
            System.out.println("5 - Transactions");
            System.out.println("6 - Delete the client");
            System.out.println("7 - Exit");
            System.out.print("Enter:");

            choice = s.nextInt();
            System.out.println();

            switch (choice){
                case(1):
                    addClient(adiko);
                    break;

                case(2):
                    printAllClients(adiko);
                    break;

                case(3):
                    printAllClients(adiko);

                    System.out.print("Enter oib of the client: ");
                    String clientOib = s.next();

                    Client c =  adiko.findClientByOib(clientOib);
                    System.out.println("Which account do you want do add for " + c.getFirstName() + " " + c.getLastName() + "\n1 - Saving\n2 - Current");
                    System.out.print("Enter:" );
                    accountType = s.nextInt();
                    if (accountType == 1) {
                        c.addAccount(new SavingAccount("Saving", 0, 3));
                        System.out.println(c.getAccountsList().get(0));
                    }
                    else if (accountType == 2) {
                        c.addAccount(new CurrentAccount("Current", 0, 2000));
                    }
                    else {
                        System.out.println("Wrong choice");
                    }
                    break;

                case(4):
                    System.out.print("Client name:");
                    String name = s.next();
                    List<Client> clientsByName = adiko.findClientByName(name);

                    if (clientsByName.size() != 0) {
                        for (Client cbn: clientsByName) {
                            System.out.println(cbn.getFirstName() + " " + cbn.getLastName() + ", " + cbn.getAddress() + ", OIB: " + cbn.getOib());
                        }
                    }
                    else{
                        System.out.println("Client doesnt exist\n");
                    }
                    break;

                case(5):
                    printAllClients(adiko);

                    System.out.print("Enter oib of the client: ");
                    clientOib = s.next();
                    c =  adiko.findClientByOib(clientOib);

                    if (c != null) {
                        System.out.println("1 - Whitdraw \n2 - Deposit\n3 - Transactions within the account");
                        System.out.print("Enter:");
                        int ch = s.nextInt();

                        if (ch == 1) {
                            System.out.println("Which account? Saving | Current");
                            String accType = s.next();
                            System.out.print("How much money: ");
                            int money = s.nextInt();

                            for (Account a : c.getAccountsList()) {
                                if (a.getAccountName().equals(accType)){  // ako je accType = Saving
                                   // kako sad pozvati current account ako je accType = Saving
                                    SavingAccount sa = (SavingAccount) c.getAccountsList().get(0);
                                    sa.whitdrawMoney(money);

                                } else if (a.getAccountName().equals(accType)) {  // ako je accType = Current
                                    CurrentAccount ca = (CurrentAccount) c.getAccountsList().get(1);
                                    ca.whitdrawMoney(money);
                                }
                            }
                        }
                        else if(ch == 2) {
                            System.out.println("Which account? Saving | Current");
                            String accType = s.next();
                            System.out.print("How much money: ");
                            int money = s.nextInt();

                            for (Account a : c.getAccountsList()) {
                                if (a.getAccountName().equals(accType)){ // Saving
                                    SavingAccount sa = (SavingAccount) c.getAccountsList().get(0);
                                    sa.depositMoney(money);
                                    System.out.println("It has been paid on account " + money + " kn");
                                } else if (a.getAccountName().equals(accType)) {  //Current
                                    CurrentAccount ca = (CurrentAccount) c.getAccountsList().get(1);
                                    ca.depositMoney(money);
                                   // System.out.println("It has been paid on account " + money + " kn");
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("Client doesnt exists\n");
                    }
                    break;

                case (6):
                    printAllClients(adiko);
                    System.out.print("Client oib: ");
                    String oib = s.next();
                    Client client = adiko.deleteClient(oib);
                    if ( client == null){
                        System.out.println("Client doesnt exist\n");
                    }
                    else {
                        System.out.println("Client " + client.getFirstName() + " " + client.getLastName() + " is succesfully deleted\n");
                    }
                    break;
            }
        }
    }

    public static void addClient(Bank adiko){
        System.out.println("*** Adding client to the bank ***");
        System.out.print("Client first name:");
        String clientFirstName = s.next();
        System.out.print("Client last name:");
        String clientLastName = s.next();
        System.out.print("Client address:");
        String address = s.next();
        System.out.print("Client oib:");
        String oib = s.next();
        System.out.println("*** Client " + clientFirstName + " is created ***\n");
        adiko.addNewClient(clientFirstName, clientLastName, address, oib);
    }

    public static void printAllClients(Bank adiko){
        for (Client c: adiko.getClients()) {
            System.out.println(c.getFirstName() + " " + c.getLastName() + ", " + c.getAddress() + ", OIB: " + c.getOib());
            System.out.println(c.getAccountsList() + "\n");
        }
    }
}
