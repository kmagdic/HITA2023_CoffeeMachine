package t4_luka.coffeemachine.bankclientdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankConsoleMain {
    static Scanner s = new Scanner(System.in);
    static List<Client> clientList = new ArrayList<>();
    public static void main(String[] args) {

        Connection conn;
        conn = makeDBConnection("bank.db");
        ClientRepository clientRepository = new ClientRepository(conn);
        clientRepository.createTable();


        Bank adiko = new Bank("Adiko", "Banfica");
        System.out.println("Bank " + adiko.getName() + " is generated\n");

        int choice = 0;

        while (choice != 5) {
            System.out.println("1 - Create - add the new client");
            System.out.println("2 - Read - print all clients");
            System.out.println("3 - Update - update the client by OIB");
            System.out.println("4 - Delete -  delete the client");
            System.out.println("5 - Exit");
            System.out.print("Enter:");

            choice = s.nextInt();
            System.out.println();

            switch (choice){

                case 1:
                    Client newClient = new Client();
                    System.out.println("First name: ");
                    newClient.setFirstName(s.next());
                    System.out.println("Last name: ");
                    newClient.setLastName(s.next());
                    System.out.println("Address: ");
                    newClient.setAddress(s.next());
                    System.out.println("OIB: ");
                    newClient.setOib(s.next());
                    clientRepository.insertClient(newClient);
                    break;

                case 2:
                    clientList = clientRepository.clientList();
                    for (Client c: clientList){
                        System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getAddress() + " " + c.getOib());
                    }
                    System.out.println();
                    break;

                case 3:

                    break;

                case 4:

            }
        }
    }

    public static Connection makeDBConnection(String filename){
        try {
            return DriverManager.getConnection("jdbc:h2:./" + filename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}