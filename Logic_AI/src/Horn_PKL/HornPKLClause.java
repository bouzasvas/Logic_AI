/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_PKL;

import java.util.ArrayList;

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
