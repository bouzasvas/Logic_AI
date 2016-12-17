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

    public boolean PL_FC_Entails(boolean details) {
        for (int index = 0; index < agenda.size(); index++) {
            Literal factLiteral = agenda.get(index);
            if (details)
                System.out.println("Checking if Clause is visited before...");
            if (!factLiteral.isInferred()) {
                factLiteral.setInferred(true);

                for (HornSubClause HSub : KB.getSubClauses()) {
                    
                    if (HSub.containsLiteral(factLiteral)) {
                        HSub.decrementCount();
                    }                 

                    if (HSub.getCount() == 0) {
                        if (details)
                            System.out.println("Fire the Rule!");
                        if (HSub.getInferrence().equals(a)) {
                            System.out.println("-----TRUE-----");
                            System.out.println("Conclusion " + a.toString() + " comes from KB!");
                            return true;
                        } else {
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
