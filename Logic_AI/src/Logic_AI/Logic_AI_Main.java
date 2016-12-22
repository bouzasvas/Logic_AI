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
import java.util.HashMap;
import java.util.InputMismatchException;
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
        System.out.println("//  1. CNF Resolution");
        System.out.println("//  2. Horn Forward Chaining");
        System.out.println("//  3. Horn PKL");
        System.out.println("//  0. Exit");
        System.out.println("******************************");

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
                System.out.println("\n!!!!Please choose one of the available options!!!!!\n");
                break;
        }
    }

    public static void performCnfResolution() {
        //local variables
        String filepath;
        String userExpression;
        CNFClause KB = null;
        Literal a = null;

        System.out.print("\nType the path of KB (Knowledge Base) file for Resolution Algorithm: ");

        filepath = input.next();
        KB = ReadFile.CNFReadFile(filepath);

        //  Σε περίπτωση που κάτι πήγε στραβά με το άνοιγμα του αρχείου επέστρεψε στο αρχικό μενού
        if (KB == null) {
            return;
        }

        System.out.println("Type the expression you want to prove");
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

        //  Επιλογή του χρήστη αν θέλει να εμφανιστούν ή όχι οι λεπτομέρειες εκτέλεησς του αλγορίθμου
        System.out.print("Print details during execution? (y/n): ");
        String details = input.next();

        System.out.println("***Performing CNF Resolution Algorithm...***\n");
        CNFMain cnf = new CNFMain(KB, cnfsub);

        cnf.PL_Resolution(true ? details.startsWith("y") : false);

        System.out.println();
    }

    private static void performHornForwardChaining() {
        String filepath;
        String userInferred;
        HornClause KB = null;
        Literal a = null;

        System.out.print("\nType the path of Horn KB file: ");
        filepath = input.next();

        KB = ReadFile.HornForwardChaining(filepath);

        //  Σε περίπτωση που κάτι πήγε στραβά με το άνοιγμα του αρχείου επέστρεψε στο αρχικό μενού
        if (KB == null) {
            return;
        }

        System.out.print("Type the expression you want to prove: ");
        userInferred = input.next();

        if (userInferred.startsWith("~")) {
            a = new Literal(userInferred.substring(1), true);
        } else {
            a = new Literal(userInferred, false);
        }

        //  Επιλογή του χρήστη αν θέλει να εμφανιστούν ή όχι οι λεπτομέρειες εκτέλεησς του αλγορίθμου
        System.out.print("Print details during execution? (y/n): ");
        String details = input.next();
        
        System.out.println("***Performing Horn Forward Chaining...***\n");
        HornMain horn = new HornMain(KB, a);
        horn.PL_FC_Entails(true ? details.startsWith("y") : false);

        System.out.println();
    }

    public static void performHornPKL() {
        String filepath;
        String userType;

        Relation a;
        ArrayList<String> paramsArr = new ArrayList<String>();

        System.out.print("\nType the path of Horn KB file: ");
        filepath = input.next();

        //  Εισαγωγή Βάσης Γνώσης από αρχείο .txt
        HornPKLClause hornClauses = ReadFile.HornPKL(filepath);

        //  Σε περίπτωση που κάτι πήγε στραβά με το άνοιγμα του αρχείου επέστρεψε στο αρχικό μενού
        if (hornClauses == null) {
            return;
        }

        System.out.println("Type the expression you want to prove");
        System.out.println("You can type an expression like \"Criminal(West)\" to see if West is Criminal");
        System.out.println("Use ~ for logical NOT");
        System.out.print("Your type: ");
        userType = input.next();

        //  Ορισμός της προς απόδειξη σχέσης ως αντκείμενο της κλάσης Relation
        String[] paramsList = null;
        String relName = null;

        while (true) {
            try {
                int leftParIndex = userType.lastIndexOf("(");
                relName = userType.substring(0, leftParIndex);
                String params = userType.substring(leftParIndex+1, userType.length() - 1);
                paramsList = params.split(",");
            } catch (StringIndexOutOfBoundsException ex) {
                System.err.println("Check your expression, only expression which follow the format Human(John) are allowed!");
                System.out.print("Your expression: ");
                userType = input.next();
                continue;
            }
            break;
        }

        for (String param : paramsList) {
            paramsArr.add(param);
        }

        if (relName.startsWith("~")) {
            a = new Relation(relName.substring(1), paramsArr, true);
        } else {
            a = new Relation(relName, paramsArr, false);
        }
        hornClauses.setA(a);
        
        //  Επιλογή του χρήστη αν θέλει να εμφανιστούν ή όχι οι λεπτομέρειες εκτέλεησς του αλγορίθμου
        System.out.print("Print details during execution? (y/n): ");
        String details = input.next();

        System.out.println("***Performing Horn fol-fc-ask Algorithm...***\n");

        //  Ο αλγόριθμος επιστρέφει ένα Map.Entry της μορφής {x -> West} σε περίπτωση που
        //  καταφέρει να κάνει την ενοποίηση με τον προς απόδειξη τύπο
        HashMap<String, String> unifiedMap = hornClauses.fol_fc_ask(true ? details.startsWith("y") : false);

        if (unifiedMap != null) {
            System.out.println("Unify Done!");
            for (String param : a.getParams()) {
                System.out.print("x" + "->" + unifiedMap.get(param));
            }
            System.out.println();
        } else {
            System.out.println("Could not find unifier for your expression!");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        while (userChoice != 0) {
            System.out.println("***********Main Menu**********");
            showMenuItems();

            while (true) {
                try {
                    userChoice = input.nextInt();
                } catch (InputMismatchException ex) {
                    System.err.print("Only numbers are allowed for your choice! Please type 1, 2 or 3 > ");
                    input.next();
                    continue;
                }
                openMenuItem(userChoice);
                break;
            }
        }
    }
}
