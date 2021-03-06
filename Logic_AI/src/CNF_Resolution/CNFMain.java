/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package CNF_Resolution;

//import Logic_AI.Literal;
import CNF_Resolution.CNFSubClause;
import java.util.Vector;

/*
    Περιέχει τον αλγόριθμο ανάλυσης (Resolution) που θα τρέξει η main κλάση του προγράμματος
    
    Αποτελείται από την Βάση Γνώσης καθώς και το προς απόδειξη Literal που έχει εισάγει ο χρήστης
    από το πληκτρολόγιο.
*/
public class CNFMain {

    CNFClause KB;
    CNFSubClause a;

    public CNFMain(CNFClause KB, CNFSubClause a) {
        this.KB = KB;
        this.a = a;
    }

    /*
    
                                Αλγόριθμος Ανάλυσης
    
    Εφαρμογή αλγορίθμου ανάλυσης για μία δοσμένη KB και με τον προς απόδειξη τύπο a.
    Ο αλγόριθμος αποκρίνεται true ή false ανάλογα με τον αν παρήχθηκε τελικά το κενό subclause.
    Σε περίπτωση παραγωγής του επιστρέφουμε true ειδάλως false.
    
    Προϋπόθεση για να παραχθεί το κενό Subclause είναι να γίνει απαλοιφή όλων των Literals που υπάρχουν στη Βάση Γνώσης.
    
    */
    public boolean PL_Resolution(boolean details) {
        //We create a CNFClause that contains all the clauses of our Knowledge Base
        CNFClause clauses = new CNFClause();
        clauses.getSubclauses().addAll(this.KB.getSubclauses());

        //...plus a clause containing the negation of the literal we want to prove
        
        //Literal aCopy = new Literal(this.a.getName(), !this.a.getNeg());
        CNFSubClause aClause = new CNFSubClause(a);
        aClause.negateLiterals();
        clauses.getSubclauses().add(aClause);

        System.out.println("We want to prove...");
        this.a.print();

        boolean stop = false;
        int step = 1;
        //We will try resolution till either we reach a contradiction or cannot produce any new clauses
        while (!stop) {
            Vector<CNFSubClause> newsubclauses = new Vector<CNFSubClause>();
            Vector<CNFSubClause> subclauses = clauses.getSubclauses();

            if (details) {
                System.out.println("Step:" + step);
            }

            step++;
            //For every pair of clauses Ci, Cj...
            for (int i = 0; i < subclauses.size(); i++) {
                CNFSubClause Ci = subclauses.get(i);

                for (int j = i + 1; j < subclauses.size(); j++) {
                    CNFSubClause Cj = subclauses.get(j);

                    //...we try to apply resolution and we collect any new clauses
                    Vector<CNFSubClause> new_subclauses_for_ci_cj = CNFSubClause.resolution(Ci, Cj);

                    //We check the new subclauses...
                    for (int k = 0; k < new_subclauses_for_ci_cj.size(); k++) {
                        CNFSubClause newsubclause = new_subclauses_for_ci_cj.get(k);

                        //...and if an empty subclause has been generated we have reached contradiction; and the literal has been proved
                        if (newsubclause.isEmpty()) {
                            if (details) {
                                System.out.println("----------------------------------");
                                System.out.println("Resolution between");
                                Ci.print();
                                System.out.println("and");
                                Cj.print();
                                System.out.println("produced:");
                                System.out.println("Empty subclause!!!");
                                System.out.println("----------------------------------");
                            }
                            else {
                                System.out.println("Empty subclause produced, TRUE!");
                            }

                            return true;
                        }

                        //All clauses produced that don't exist already are added
                        if (!newsubclauses.contains(newsubclause) && !clauses.contains(newsubclause)) {
                            if (details) {
                                System.out.println("----------------------------------");
                                System.out.println("Resolution between");
                                Ci.print();
                                System.out.println("and");
                                Cj.print();
                                System.out.println("produced:");
                                newsubclause.print();
                                System.out.println("----------------------------------");
                            }
                            newsubclauses.add(newsubclause);
                        }
                    }
                }
            }

            boolean newClauseFound = false;

            //Check if any new clauses were produced in this loop
            for (int i = 0; i < newsubclauses.size(); i++) {
                if (!clauses.contains(newsubclauses.get(i))) {
                    clauses.getSubclauses().addAll(newsubclauses);
                    newClauseFound = true;
                }
            }

            //If not then Knowledge Base does not logically infer the literal we wanted to prove
            if (!newClauseFound) {
                System.out.println("Not found new clauses");
                stop = true;
            }
        }
        System.out.println();
        return false;
    }
}
