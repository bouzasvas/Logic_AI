/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_ForwardChaining;

import Logic_AI.Literal;

import java.util.ArrayList;

public class HornClause {

    ArrayList<HornSubClause> KB;
    
    public HornClause() {
        KB = new ArrayList<HornSubClause>();
    }
    
    public void addHornSubClause(HornSubClause subClause) {
        KB.add(subClause);
    }
    
    public ArrayList<Literal> getFacts() {
        ArrayList<Literal> trueSubClauses = new ArrayList<Literal>();
        
        for (HornSubClause hsc : KB) {
            if (hsc.getClause() == null) {
                trueSubClauses.add(hsc.getInferrence());
            }
        }
        
        return trueSubClauses;
    }
    
    public void print() {
        for (HornSubClause subClause : KB) {
            subClause.printSubClause();
        }
    }
    
    public ArrayList<HornSubClause> getSubClauses() {
        return this.KB;
    }
}
