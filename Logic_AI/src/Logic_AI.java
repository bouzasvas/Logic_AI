
import FileIO.ReadFile;
import logic_ai.*;
import java.util.List;
import java.util.Scanner;

public class Logic_AI {

    static Scanner input = new Scanner(System.in);
    static int userChoice = -1;

    public static void showMenuItems() {
        System.out.println("1. CNF Resolution");
        System.out.println("0. Exit");

        System.out.println("\nType the number of your choice");
    }

    public static void openMenuItem(int choice) {
        switch (choice) {
            case 1:
                String filename;
                System.out.print("Type the path of file you want to perform Resolution Algorithm: ");
                
                filename = input.next();
                List l = ReadFile.CNFReadFile(filename);

                for (int index = 0; index < l.size(); index++) {
                    CNFSubClause c = (CNFSubClause) l.get(index);
                    c.print();
                }
                break;
            case 0:
                System.exit(0);
            default:
                System.out.println("Please choose one of the available options!");
                break;
        }
    }

    public static void main(String[] args) {

        while (userChoice != 0) {
            System.out.println("**Main Menu**");
            showMenuItems();

            userChoice = input.nextInt();
            openMenuItem(userChoice);
        }
    }

}
