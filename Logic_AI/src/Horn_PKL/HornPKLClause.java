/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_PKL;

import java.util.ArrayList;

/*

------Κλάση HornPKLClause για αναπαράσταση προτάσεων Horn στην Πρωτοβάθμια Κατηγορηματική Λογική--------

    Αποτετελείται από μία ArrayList που περιέχει κανόνες (Rules) της μορφής Missile(x)^Owns(Nono,x)
    καθώς και από ένα συμπέρασμα που είναι μια σχέση (Relation) της μορφής Sells(West,x,Nono).

    Ο συνδυασμός τους μας δίνει τον κανόνα Missile(x)^Owns(Nono,x)=>Sells(West,x,Nono).

*/

public class HornPKLClause {
    private ArrayList<Rule> KB;
    private Relation a;
    
    public HornPKLClause() {
        this.KB = new ArrayList<Rule>();
    }
    
    public void addHornClause(Rule rule) {
        this.KB.add(rule);
    }
    
    public void setA(Relation a) {
        this.a = a;
    }
    
    public Relation getA() {
        return this.a;
    }
    
    public void fol_fc_ask () {
        
    }
}
