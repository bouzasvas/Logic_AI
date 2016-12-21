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
import java.util.Map.Entry;

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
    public static Entry<String, String> Unify(Relation a, Relation a1) {
        Entry<String, String> unifiedPair = null;

        if (a.getName().equals(a1.getName())) {
            for (int index = 0; index < a.getParams().size(); index++) {
                if (!a.getConstParams().get(index)) {
                    if (!a.getParams().get(index).equals(a1.getParams().get(index))) {
                        unifiedPair = new AbstractMap.SimpleEntry<String, String>(a.getParams().get(index), a1.getParams().get(index));
                    }
                } else {
                    return null;
                }
            }
        }
        else
            return null;

        return unifiedPair;
    }
}
