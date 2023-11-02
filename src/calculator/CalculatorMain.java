package calculator;

import java.util.Scanner;

public class CalculatorMain {

    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("1 - Basic\n" +
                "2 - Advanced\n" +
                "Enter: ");

        int ch = s.nextInt();

        Calculator c = null;

        if (ch == 1){
            c = new Calculator();
        } else if (ch == 2) {
            c = new AdvancedCalculator();
        }

        System.out.println("Operations!!!");
        c.printOperations();
        System.out.println();

        System.out.print("A: ");
        c.setA(s.nextDouble());
        System.out.print("Op: ");
        c.setOperation(s.next());
        System.out.print("B: ");
        c.setB(s.nextDouble());

        System.out.println(c.calculate());
    }
}