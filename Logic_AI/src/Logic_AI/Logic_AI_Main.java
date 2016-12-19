/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

 */
package Logic_AI;

import CNF_Resolution.*;
import Horn_ForwardChaining.*;
import Horn_PKL.HornPKLClause;
import Horn_PKL.Relation;

import FileIO.ReadFile;
import java.util.ArrayList;
import java.util.Scanner;

/*

----------------------------------------------MAIN CLASS-----------------------------------------

    Η κλάση αυτή είναι η Main κλάση για την εκτέλεση του προγράμματος.

    Εμφανίζει στον χρήστη το μενού με τις διαθέσιμες επιλογές, δέχεται ως είσοδο την επιλογή του
    και εκτελεί ανάλογα με την επιλογή του τον αντίστοιχο αλγόριθμο.

    Συγκεκριμένα πατώντας:
        1. Εκτελείται ο αλγόριθμος ανάλυσης
        2. Εκτελείται ο αλγόριθμος εξαγωγής συμπεράσματος προς τα εμπρός για προτάσεις Horn Προτασιακής Λογικής
        3. Εκτελείται ο αλγόριθμος εξαγωγής συμπεράσματος προς τα εμπρός για προτάσεις Horn ΠΚΛ
        0. Τερματίζει το πρόγραμμα

    Μετά την επιλογή του ο χρήστης καλέιται να εισάγει ένα αρχείο .txt το οποίο θα χρησιμοποιηθεί ως Βάση Γνώσης
    για τον εκάστοτε αλγόριθμο και τον προς απόδειξη τύπο.
    Τα παραπάνω θα χρησιμοποιηθούν από τον αλγόριθμο ο οποίος θα αποφανθεί αν ισχύει ή όχι ο προς απόδειξη τύπος
    με βάση τους κανόνες που υπάρχουν στη Βάση Γνώσης του.

 */
public class Logic_AI_Main {

    static Scanner input = new Scanner(System.in);
    static int userChoice = -1;

    //  Κύριο Μενού
    public static void showMenuItems() {
        System.out.println("1. CNF Resolution");
        System.out.println("2. Horn Forward Chaining");
        System.out.println("3. Horn PKL");
        System.out.println("0. Exit");

        System.out.print("\nType the number of your choice: ");
    }

    //  Εκτέλεση αλγορίθμου ανάλογα με την επιλογή του χρήστη
    public static void openMenuItem(int choice) {
        switch (choice) {
            case 1:
                performCnfResolution();
                break;
            case 2:
                performHornForwardChaining();
                break;
            case 3:
                performHornPKL();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Please choose one of the available options!");
                break;
        }
    }

    public static void performCnfResolution() {
        //local variables
        String filepath;
        String userExpression;
        CNFClause KB = null;
        Literal a = null;

        System.out.print("\nType the path of file you want to perform Resolution Algorithm: ");

        filepath = input.next();
        KB = ReadFile.CNFReadFile(filepath);
        
        if (KB == null) return;

        System.out.println("Type the expression you want to prove if TRUE or FALSE");
        System.out.println("(use ^ for logical AND, | for logical OR and ~ for logical NOT)");
        System.out.print("Your expression: ");

        userExpression = input.next();

        CNFSubClause cnfsub = new CNFSubClause();
        
        //  Χωρισμός της εισόδου του χρήστη στο χαρακτήρα '|' που αναπαριστά το Λογικό OR
        String[] userExpOr = userExpression.split("\\|");
        for (String orLit : userExpOr) {
            if (orLit.startsWith("~")) {
                a = new Literal(orLit.substring(1), true);
            } else {
                a = new Literal(orLit, false);
            }
            cnfsub.getLiterals().add(a);
        }

        System.out.println("***Performing CNF Resolution Algorithm...***\n");
        CNFMain cnf = new CNFMain(KB, cnfsub);

        cnf.PL_Resolution(false);

        System.out.println();
    }

    public static void performHornPKL() {
        String filepath;
        String userType;

        Relation a;
        ArrayList<String> paramsArr = new ArrayList<String>();

        System.out.print("\nType the path of Horn PKL file: ");
        filepath = input.next();
        
        //Get the Horn KB Clauses from txt file
        HornPKLClause hornClauses = ReadFile.HornPKL(filepath);
        
        if (hornClauses == null) return;

        System.out.println("Type the Inference you want to see if TRUE or FALSE from KB facts: ");
        System.out.println("You can type an expression like \"Criminal(West)\" to see if West is Criminal");
        System.out.println("Use ~ for logical NOT");
        System.out.print("Your type: ");
        userType = input.next();

        //set the type which we want to confirm or not
        int leftParIndex = userType.lastIndexOf("(");
        String relName = userType.substring(0, leftParIndex);
        String params = userType.substring(leftParIndex, userType.length() - 1);
        String[] paramsList = params.split(",");

        for (String param : paramsList) {
            paramsArr.add(param);
        }

        if (relName.startsWith("~")) {
            a = new Relation(relName.substring(1), paramsArr, true);
        } else {
            a = new Relation(relName, paramsArr, false);
        }
        hornClauses.setA(a);

        System.out.println("***Performing Horn fol-fc-ask Algorithm...***\n");
        hornClauses.fol_fc_ask();
    }

    private static void performHornForwardChaining() {
        String filepath;
        String userInferred;
        HornClause KB = null;
        Literal a = null;

        System.out.print("\nType the path of Horn KB file: ");
        filepath = input.next();

        KB = ReadFile.HornForwardChaining(filepath);
        
        if (KB == null) return;

        System.out.print("Type the inferred you want to prove TRUE or FALSE: ");
        userInferred = input.next();

        if (userInferred.startsWith("~")) {
            a = new Literal(userInferred.substring(1), true);
        } else {
            a = new Literal(userInferred, false);
        }

        System.out.println("***Performing Horn Forward Chaining...***\n");
        HornMain horn = new HornMain(KB, a);
        horn.PL_FC_Entails(false);

        //KB.print();    
        System.out.println();
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
