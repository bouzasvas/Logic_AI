/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_PKL;

import java.util.ArrayList;

/*

---------------------------------Κλάση Rule.java--------------------------------

    Αναπαριστά μία πρόταση Horn ΠΚΛ πχ. Missile(x)^Owns(Nono,x)=>Sells(West,x,Nono)

*/

public class Rule {
    //  ArrayList που περιέχει τις σχέσεις του κανόνα
    private ArrayList<Relation> clause;
    // Σχέση που αφορά το συμπέρασμα που προκύπτει αν ισχύουν οι παραπάνω σχέσεις
    private Relation inferrence;
    
    public Rule(ArrayList<Relation> clause, Relation inferrence) {
        if (clause != null)
            this.clause =  new ArrayList<Relation>(clause);
        this.inferrence = new Relation(inferrence);
    }
    
    public void printRule() {
        for (Relation rel : this.clause) {
            rel.printRelation();
            System.out.print("^");
        }
        System.out.print("=>");
        this.inferrence.printRelation();
        System.out.println();
    }
    
    public Relation getInferrence() {
        return this.inferrence;
    }
    
    public ArrayList<Relation> getClause() {
        return this.clause;
    }
    
    public boolean isFact() {
        return (clause==null) ? true : false;
    }
}
