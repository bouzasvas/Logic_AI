/*
 Μέλη Ομάδας

 Λόκκας Ιωάννης ΑΜ: 3120095
 Μπούζας Βασίλειος ΑΜ: 3120124
 Τασσιάς Παναγιώτης ΑΜ: 3120181

 */

/*

 -----------------------------Unify.java-------------------------

 Περιέχει τον αλγόριθμο που αφορά την ενοποίηση των μεταβλητών.

 */
package Horn_PKL;

import java.util.AbstractMap;
import java.util.HashMap;

public class Unify {

    /*
    
     ----------------Αλγόριθμος Ενοποίησης (Uify) μεταβλητών--------------------
    
     Ο αλγόριθμος ενοποίησης όπως περιγράφεται στις διαφάνειες του μαθήματος
     .   με μια μικρή αλλαγή που αφορά τις παραμέτρους του.
    
     Παίρνει ως είσοδο 2 κανόνες με την ακόλουθη μορφή:
     -a: Human(x)
     -a1: Human(John)
     και βγάζει ως έξοδο έναν ενοποιητή (εδώ ένα Map.Entry) που κάνει την ενοποίηση
     x->John.
    
     */
    public static HashMap Unify(Rule a, Rule a1, boolean inferrences) {
        HashMap<String, String> unifiedMap = new HashMap<>();
        Relation rel1 = a1.getInferrence();

        if (!inferrences) {
            for (Relation rel : a.getClause()) {
                if (rel.getName().equals(rel1.getName())) {
                    for (int index = 0; index < rel.getParams().size(); index++) {
                        if (!rel.getConstParams().get(index)) {
                            if (!rel.getParams().get(index).equals(rel1.getParams().get(index))) {
                                unifiedMap.put(rel.getParams().get(index), rel1.getParams().get(index));
                            }
                        }
                    }
                }
            }
        } else {
            Relation inf = a.getInferrence();
            Relation inf1 = a1.getInferrence();

            if (inf.getName().equals(inf1.getName())) {
                for (int index = 0; index < inf.getParams().size(); index++) {
                    unifiedMap.put(inf.getParams().get(index), inf1.getParams().get(index));
                }
            }
        }

        return unifiedMap;
    }
}
