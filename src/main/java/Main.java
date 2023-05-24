import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.util.Scanner;

/**
 * The main class prompts the user to enter an order ID, and then calls the methods needed to draw the project
 */
public class Main {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        JavaCSG csg = JavaCSGFactory.createDefault();
        Print print = new Print();

        System.out.println("Indtast et ordre-ID :");
        Scanner scanner = new Scanner(System.in);
        int orderID = scanner.nextInt();

        print.printAndDrawProject(orderID, connectionPool, csg);
    }
}