/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_ForwardChaining;

import Logic_AI.Literal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*

---------------Κλάση που περιέχει τον αλγόριθμο PL_FC_Entails----------------------

    Περιέχει τον αλγόριθμο για την εξαγωγή συμπεράσματος προς τα εμπρός για προτάσεις Horn.
    Αποτελείται από τη Βάση Γνώσης (KB) καθώς και τον προς απόδειξη τύπο που έχει δώσει ο χρήστης (a).

    Ακόμη αποτελείται από την agenda που περιέχει τα γεγονότα που έχουμε συμπεράνει και που υπάρχουν 
    κάθε χρονική στιγμή στη KB και μια λίστα inferred που δείχνει για κάθε Literal αν το έχουμε επισκεφθεί ή όχι.

*/

public class HornMain {

    HornClause KB;
    Literal a;

    ArrayList<Literal> agenda = new ArrayList<Literal>();
    HashMap<Literal, Boolean> inferred = new HashMap<Literal, Boolean>();

    public HornMain(HornClause KB, Literal a) {
        this.KB = KB;
        this.a = a;

        agenda.addAll(KB.getFacts());
    }

    /*
        
        -----------------Horn Forward Chaining------------------------
        Αλγόριθμος που υλοποιεί το Horn Forward Chaining όπως ακριβώς υπάρχει στις διαφάνειες του μαθήματος.
    
        
    
    */
    public boolean PL_FC_Entails(boolean details) {
        //  Για κάθε γεγονός που έχουμε συμπεράνει στη KB
        for (int index = 0; index < agenda.size(); index++) {
            Literal factLiteral = agenda.get(index);
            if (details)
                System.out.println("Checking if Clause is visited before...");
            
            //  Έλεγχος αν έχω ξαναεπισκεφθεί το συγκεκριμένο γεγονός
            if (!factLiteral.isInferred()) {
                factLiteral.setInferred(true);

                //  Για κάθε HornSubClause
                for (HornSubClause HSub : KB.getSubClauses()) {
                    //  Αν συμμετέχει το συγκεκριμένο γεγονός στο HornSubClause μείωσε του το count
                    if (HSub.containsLiteral(factLiteral)) {
                        HSub.decrementCount();
                    }                 
                    //  Πυροδότηση κανόνα όταν το count γίνει 0
                    if (HSub.getCount() == 0) {
                        if (details)
                            System.out.println("Fire the Rule!");
                        if (HSub.getInferrence().equals(a)) {
                            System.out.println("-----TRUE-----");
                            System.out.println("Conclusion " + a.toString() + " comes from KB!");
                            return true;
                        } else {
                            //  Προσθήκη του γεγονότος που συμπεράναμε στη KB
                            agenda.add(HSub.getInferrence());
                        }
                    }
                }
            }
        }
        System.out.println("-----FALSE-----");
        System.out.println("No conclusion occured from the KB");
        return false;
    }
}
