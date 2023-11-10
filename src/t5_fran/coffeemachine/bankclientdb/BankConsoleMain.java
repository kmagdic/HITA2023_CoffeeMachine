package t5_fran.coffeemachine.bankclientdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankConsoleMain {
    static Scanner s = new Scanner(System.in);
    static List<Client> clientList = new ArrayList<>();
    public static void main(String[] args) {



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

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

            }
        }
    }
}
