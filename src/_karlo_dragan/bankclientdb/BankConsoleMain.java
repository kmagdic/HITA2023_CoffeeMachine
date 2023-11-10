package _karlo_dragan.bankclientdb;

import _karlo_dragan.bankclientdb.repositories.AccountRepository;
import _karlo_dragan.bankclientdb.repositories.ClientRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BankConsoleMain {
    static Connection conn = null;
    static Scanner s = new Scanner(System.in);
    static ClientRepository clientRepository;
    static AccountRepository accountRepository;


    public static void main(String[] args) {

        conn = makeDBConnection("bank_db");

        clientRepository = new ClientRepository(conn);
        clientRepository.createTable();

        accountRepository = new AccountRepository(conn);
        accountRepository.createTable();


        Bank adiko = new Bank("Adiko", "Banfica");
        System.out.println("Bank " + adiko.getName() + " is generated\n");

        int choice = 0;

        while (choice != 8) {
            System.out.println("1 - Create - add the new client");
            System.out.println("2 - Read - print all clients");
            System.out.println("3 - Update - update the client by OIB");
            System.out.println("4 - List accounts by client");
            System.out.println("5 - Add account for client");
            System.out.println("6 - Add money on account of client");
            System.out.println("7 - Delete -  delete the client");
            System.out.println("8 - Exit");
            System.out.print("Enter:");

            choice = s.nextInt();
            System.out.println();

            switch (choice){

                case 1:
                    Client newClient = new Client();

                    System.out.println("First name:");
                    newClient.setFirstName(s.next());
                    System.out.println("Last name:");
                    newClient.setLastName(s.next());
                    System.out.println("Address:");
                    newClient.setAddress(s.next());
                    System.out.println("OIB:");
                    newClient.setOib(s.next());

                    clientRepository.insertClient(newClient);
                    break;

                case 2:
                    List<Client> clientList = clientRepository.getListOfALlClients();
                    for (Client c: clientList) {
                        System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getAddress()+" " + c.getOib());
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.println("OIB: ");
                    String oib = s.next();
                    Client c = findClient(oib);


                    if (c == null){
                        System.out.println("Client doesn't exists");
                    }
                    else {
                        System.out.println("Client: " + c);

                        System.out.println("First name:");
                        c.setFirstName(s.nextLine());
                        System.out.println("Last name:");
                        c.setLastName(s.nextLine());
                        System.out.println("Address:");
                        c.setAddress(s.nextLine());
                        System.out.println("OIB:");
                        c.setOib(s.nextLine());


                        clientRepository.updateClient(c);
                        System.out.println("Updated");
                    }
                    break;
                case 4: // list account for client
                    System.out.println("OIB: ");
                    String oib1 = s.next();
                    Client client = findClient(oib1);
                    if(client == null) {
                        System.out.println("Client is not found");
                        break;
                    }

                    System.out.println("Client: " + client);
                    List<Account> accountList = accountRepository.getListAccountsForClient(client);
                    for(Account a : accountList) {
                        System.out.println(a);
                    }
                    break;

                case 5: // 5 - Add accounts for client
                    System.out.println("OIB: ");
                    oib = s.next();
                    client = findClient(oib);
                    if(client == null) {
                        System.out.println("Client is not found");
                        break;
                    }

                    System.out.println("Client: " + client);
                    Account newAccount = new Account();
                    newAccount.setClient(client);
                    newAccount.setBalance(0);
                    newAccount.setAccountName("New");

                    accountRepository.insert(newAccount);
                    System.out.println("New account " + newAccount + " is added.");
                    break;
                case 6: // 5 - Add amount to account
                    System.out.println("OIB: ");
                    oib = s.next();
                    client = findClient(oib);
                    if(client == null) {
                        System.out.println("Client is not found");
                        break;
                    }

                    System.out.println("Client: " + client);
                    List<Account> accountList2 = accountRepository.getListAccountsForClient(client);
                    for(Account a : accountList2) {
                        System.out.println(a);
                    }

                    System.out.println("Enter account id: ");
                    int accountId = s.nextInt();
                    Account a = accountRepository.getListAsMapAccountsForClient(client).get(accountId);


                    System.out.println("Enter amount to add: ");
                    double amount = s.nextDouble();
                    a.setBalance(a.getBalance() + amount);
                    accountRepository.updateAccount(a);
                    System.out.println("Account is updated.");

                    break;
                case 7:
                    System.out.println("OIB: ");
                    String o = s.next();
                    Client clientToDelete = findClient(o);

                    if (clientToDelete == null){
                        System.out.println("Client doesn't exists");
                    }
                    else {
                        clientRepository.delete(clientToDelete);
                    }

                    break;
            }
        }
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Client findClient(String oib){
        List<Client> clientList = clientRepository.getListOfALlClients();
        for (Client c : clientList){
            if (c.getOib().equals(oib)){
                return c;
            }
        }
        return null;
    }
}
