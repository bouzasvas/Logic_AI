/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package FileIO;

import CNF_Resolution.*;
import Horn_ForwardChaining.*;
import Horn_PKL.Relation;
import Horn_PKL.Rule;
import Logic_AI.*;
import Horn_PKL.HornPKLClause;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/*
    -------------------------------------------ΔΙΑΒΑΣΜΑ ΑΡΧΕΙΟΥ------------------------------------------
    
Η κλάση αυτή είναι υπέυθυνη για το διάβασμα του αρχείου που περιέχει τη Βάση Γνώσης για κάθε μορφή λογικής.
Για κάθε μορφή υπάρχουν υλοποιημένες οι αντίστοιχες συναρτήσεις:
    CNFReadFile --> Για διάβασμα αρχείου σε CNF μορφή Προτασιακής Λογικής
    HornForwardChaining --> Για διάβασμα αρχείου σε Horn μορφή Προτασιακής Λογικής
    HornPKL --> Για διάβασμα αρχείου σε Horn μορφή Πρωτοβάθμιας Κατηγορηματικής Λογικής

*/

public class ReadFile {

    private static BufferedReader reader = null;
    private static File file = null;

    
    //  Άνοιγμα του αρχείου και αρχικοποίηση του Buffer
    public static void initBuffer(String filename) {
        file = new File(filename);

        try {
            System.out.println("\nStart Reading.....");
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException ex) {
            System.err.println("Could not open file:" + file.getAbsolutePath());
        }
    }

    //  Κλείσιμο του αρχείου
    public static void closeFile() {
        try {
            reader.close();
            file = null;
            System.out.println("SUCCESS!\n");
        } catch (IOException ex) {
            System.err.println("Could not close file:\n" + file.getAbsolutePath());
        }
        catch (NullPointerException nullEx) {
            System.err.println("File could not close because buffer has not been initialized, check the file path!");
        }
    }

    //  Μέθοδος για διάβασμα του αρχείου στην CNF Μορφή Προτασιακής Λογικής
    public static CNFClause CNFReadFile(String filename) {
        initBuffer(filename);

        CNFSubClause cnf;
        CNFClause KB = new CNFClause();

        try {
            String line;
            while ((line = reader.readLine()) != null) {

                //  Γραμμές με σχόλια στο .txt αρχείο
                if (line.startsWith("#")) {
                    continue;
                }

                //  Χωρισμός κάθε γραμμής με βάση τον χαρακτήρα '^' που χρησιμοποιούμε ως λογικό AND
                String[] andSubClause = line.split("\\^");

                for (String andStr : andSubClause) {
                    cnf = new CNFSubClause();

                    andStr = andStr.trim();
                    
                    //  Χωρισμός κάθε γραμμής με βάση τον χαρακτήρα '|' που χρησιμοποιούμε ως λογικό OR
                    String[] orSubClause = andStr.split("\\|");

                    for (String orStr : orSubClause) {
                        //  Απαλοιφή των παρενθέσεων
                        orStr = orStr.replace("(", "");
                        orStr = orStr.replace(")", "");

                        //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                        //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                        if (orStr.startsWith("~")) {
                            cnf.getLiterals().add(new Literal(orStr.substring(1), true));
                        } else {
                            cnf.getLiterals().add(new Literal(orStr, false));
                        }
                    }
                    KB.getSubclauses().add(cnf);
                }
            }
        } catch (IOException ex) {
            System.err.println("Something went wrong while trying to read from file:" + file.getAbsolutePath());
            return null;
        }
        catch (NullPointerException nullEx) {
            System.err.println("Buffer has not been initialized, check the file path!\n");
            return null;
        }

        closeFile();

        return KB;
    }

    //  Μέθοδος για διάβασμα του αρχείου στην Horn μορφή Προτασιακής Λογικής
    public static HornClause HornForwardChaining(String filename) {
        initBuffer(filename);

        String line;
        HornClause horn = new HornClause();

        try {
            while ((line = reader.readLine()) != null) {

                //  Γραμμές με σχόλια στο .txt αρχείο
                if (line.startsWith("#")) {
                    continue;
                }

                HornSubClause hornSub = null;
                Literal literal;
                
                //  Διαχωρισμός με βάση το σύμβολο '=>' που χρησιμοποιούμε ως τρόπο αναπαράστασης του TOTE
                String[] hornParts = line.split("=>");

                //  Έλεγχος αν έχουμε συναντήσει κανόνα ή γεγονός
                if (hornParts.length == 1) {                   
                    //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                    //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                    if (hornParts[0].startsWith("~")) {
                        literal = new Literal(hornParts[0], true);
                    } else {
                        literal = new Literal(hornParts[0], false);
                    }
                    hornSub = new HornSubClause(null, literal);
                } else {
                    hornSub = new HornSubClause();

                    //  Χωρισμός κάθε γραμμής με βάση τον χαρακτήρα '^' που χρησιμοποιούμε ως λογικό AND 
                    String[] leftPart = hornParts[0].split("\\^");

                    //  Σύζευξη γεγονότων που δίνουν το συμπέρασμα
                    for (String l : leftPart) {
                        //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                        //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                        if (l.startsWith("~")) {
                            literal = new Literal(l, true);
                        } else {
                            literal = new Literal(l, false);
                        }
                        hornSub.addArrLiteral(literal);
                    }

                    //  Το συμπέρασμα που προκύπτει
                    String rightPart = hornParts[1];
                    //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                    //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                    if (rightPart.startsWith("~")) {
                        literal = new Literal(rightPart, true);
                    } else {
                        literal = new Literal(rightPart, false);
                    }
                    hornSub.setInference(literal);
                    hornSub.calculateCount();
                }
                horn.addHornSubClause(hornSub);
            }
        } catch (IOException ex) {
            System.err.println("Could not read the next line of file: " + file.getAbsolutePath());
            System.err.println(ex.getMessage());
            return null;
        }
        catch (NullPointerException nullEx) {
            System.err.println("Buffer has not been initialized, check the file path!\n");
            return null;
        }

        closeFile();

        return horn;
    }

    //  Μέθοδος για διάβασμα του αρχείου στην Horn μορφή Πρωτοβάθμιας Κατηγορηματικής Λογικής
    public static HornPKLClause HornPKL(String filename) {
        initBuffer(filename);

        String line;
        HornPKLClause hornClause = new HornPKLClause();

        try {
            while ((line = reader.readLine()) != null) {
                Relation rel;
                Rule rule;

                //  Γραμμές με σχόλια στο .txt αρχείο
                if (line.startsWith("#")) {
                    continue;
                }

                //  Διαχωρισμός με βάση το σύμβολο '=>' που χρησιμοποιούμε ως τρόπο αναπαράστασης του TOTE
                String[] infSplit = line.split("=>");

                //  Όταν έχουμε γεγονός και όχι κανόνα (πχ isHuman(John))
                if (infSplit.length == 1) {
                    //  Παίρνουμε το όνομα της σχέσης στο σημείο που εμφανίζεται για 1η φορά η αριστερή παρένθεση
                    //  Εκεί δηλαδή που ξεκινούν οι παράμετροι της σχέσης
                    int leftParIndex = infSplit[0].lastIndexOf("(");

                    String relName = infSplit[0].substring(0, leftParIndex);
                    
                    // Παράμετροι της σχέσης
                    ArrayList<String> params = new ArrayList<String>();

                    String paramsStr = infSplit[0].substring(leftParIndex + 1);
                    paramsStr = paramsStr.replace(")", "");

                    //  Διαχωρισμός των παραμέτρων με το σύμβολο ',' ώστε να πάρουμε όλες τις παραμέτρους της σχέσης
                    String[] paramsList = paramsStr.split(",");

                    for (String str : paramsList) {
                        params.add(str);
                    }

                    //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                    //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                    if (relName.startsWith("~")) {
                        rel = new Relation(relName.substring(1), params, true);
                    } else {
                        rel = new Relation(relName, params, false);
                    }

                    rule = new Rule(null, rel);
                
                //  Όταν έχουμε κανόνα που συμπεραίνει κάποιο γεγονός (πχ Enemy(x,America)=>Hostile(x))
                } else {
                    ArrayList<Relation> clauses = new ArrayList<Relation>();
                    Relation inf;

                    //  Χωρισμός κάθε γραμμής με βάση τον χαρακτήρα '^' που χρησιμοποιούμε ως λογικό AND 
                    String[] andClause = infSplit[0].split("\\^");
                    String infStr = infSplit[1];

                    for (String andStr : andClause) {
                        ArrayList<String> params = new ArrayList<String>();

                        //  Παίρνουμε το όνομα της σχέσης στο σημείο που εμφανίζεται για 1η φορά η αριστερή παρένθεση
                        //  Εκεί δηλαδή που ξεκινούν οι παράμετροι της σχέσης
                        int leftParIndex = andStr.indexOf("(");

                        String relName = andStr.substring(0, leftParIndex);

                        // Παράμετροι της σχέσης
                        String paramsStr = andStr.substring(leftParIndex + 1);
                        paramsStr = paramsStr.replace(")", "");

                        //  Διαχωρισμός των παραμέτρων με το σύμβολο ',' ώστε να πάρουμε όλες τις παραμέτρους της σχέσης
                        String[] paramsList = paramsStr.split(",");

                        for (String str : paramsList) {
                            params.add(str);
                        }

                        //  Έλεγχος αν το Literal που συναντήσαμε είναι άρνηση ή όχι
                        //  Αναπαριστούμε με το σύμβολο '~' το λογικό NOT
                        if (relName.startsWith("~")) {
                            rel = new Relation(relName.substring(1), params, true);
                        } else {
                            rel = new Relation(relName, params, false);
                        }
                        clauses.add(rel);
                    }

                    //  Ίδια επεξεργασία με παραπάνω για το γεγονός....
                    int leftParIndex = infStr.lastIndexOf("(");

                    String relName = infStr.substring(0, leftParIndex);
                    ArrayList<String> params = new ArrayList<String>();

                    String paramsStr = infStr.substring(leftParIndex + 1);
                    paramsStr = paramsStr.replace(")", "");

                    String[] paramsList = paramsStr.split(",");

                    for (String str : paramsList) {
                        params.add(str);
                    }
                    
                    if (infStr.startsWith("~")) {
                        inf = new Relation(relName.substring(1), params, true);
                    } else {
                        inf = new Relation(relName, params, false);
                    }

                    rule = new Rule(clauses, inf);
                }
                hornClause.addHornClause(rule);
            }
        } catch (IOException ex) {
            System.err.println("Could not read next line");
            System.err.println(ex.getMessage());
            return null;
        }
        catch (NullPointerException nullEx) {
            System.err.println("Buffer has not been initialized, check the file path!\n");
            return null;
        }

        closeFile();
        
        return hornClause;
    }
}
