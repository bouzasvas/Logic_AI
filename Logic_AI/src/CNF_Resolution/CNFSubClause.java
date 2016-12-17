/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package CNF_Resolution;

import Logic_AI.Literal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/*

    Το CNFSubClause αποτελείται από τα Literals που ενώνονται μεταξύ τους με λογικό OR.
    Η διάζευζη των CNF Subclauses θα μας δώσει τελικά την CNF έκφραση.

 */
public class CNFSubClause implements Comparable<CNFSubClause>
{
    // HashSet για τα Literals του Subclause ώστε να μην έχουμε διπλότυπα
    private HashSet<Literal> literals;
            
    public CNFSubClause()
    {
        literals = new HashSet<Literal>();
    }
    
    public CNFSubClause(CNFSubClause subclause) {
        literals = new HashSet<Literal>();
        literals.addAll(subclause.getLiterals());
    }
         
    public  HashSet<Literal> getLiterals()            
    {
        return literals;
    }
    
    public Iterator<Literal> getLiteralsList()
    {
        return literals.iterator();
    }
         
    public boolean isEmpty()
    {
        return literals.isEmpty();
    }
    
    public void print()
    {
        System.out.println("**************************");
        Iterator<Literal> iter = this.getLiteralsList();
        
        while(iter.hasNext())
        {
            Literal l = iter.next();
            
            l.print();
        }
        System.out.println("**************************\n");
    }
    
    public void negateLiterals() {
        for (Literal l : literals) {
            l.setNeg(!l.getNeg());
        }
    }

    /*
        
            Αλγόριθμος Ανάλυσης για 2 CNFSubClauses
    
    Το αποτέλεσμά του θα είναι ένα Vector από SubClauses που θα περιέχει όλα τα Literals εκτός αυτών για τα οποία
    έγινε απαλοιφή.
    
     */
    public static Vector<CNFSubClause> resolution(CNFSubClause CNF_SC_1, CNFSubClause CNF_SC_2)
    {
        Vector<CNFSubClause> newClauses = new Vector<CNFSubClause>();

        Iterator<Literal> iter = CNF_SC_1.getLiteralsList();

        //The iterator goes through all Literals of the first clause
        while(iter.hasNext())
        {            
            Literal l = iter.next();
            Literal m = new Literal(l.getName(), !l.getNeg());

            //If the second clause contains the negation of a Literal in the first clause
            if(CNF_SC_2.getLiterals().contains(m))
            {
                //We construct a new clause that contains all the literals of both CNFSubclauses...
                CNFSubClause newClause = new CNFSubClause();

                //...except the pair the literals that were a negation of one another
                HashSet<Literal> CNF_SC_1_Lits = new HashSet(CNF_SC_1.getLiterals());
                HashSet<Literal> CNF_SC_2_Lits = new HashSet(CNF_SC_2.getLiterals());
                CNF_SC_1_Lits.remove(l);
                CNF_SC_2_Lits.remove(m);

                //Normally we have to remove duplicates of the same literal; the new clause must not contain the same literal more than once
                //But since we use HashSet only one copy of a literal will be contained anyway

                newClause.getLiterals().addAll(CNF_SC_1_Lits);
                newClause.getLiterals().addAll(CNF_SC_2_Lits);

                newClauses.add(newClause);
            }
        }//The loop runs for all literals, producing a different new clause for each different pair of literals that negate each other
        
        return newClauses;
    }
    
    
    public boolean equals(Object obj)
    {
        CNFSubClause l = (CNFSubClause)obj;

        Iterator<Literal> iter = l.getLiteralsList();
        
        while(iter.hasNext())
        {
            Literal lit = iter.next();
            if(!this.getLiterals().contains(lit))
                return false;
        }
        
        if(l.getLiterals().size() != this.getLiterals().size())
            return false;
        
        return true;
    }
	
    
    public int hashCode()
    {
        Iterator<Literal> iter = this.getLiteralsList();
        int code = 0;
        
        while(iter.hasNext())
        {
            Literal lit = iter.next();
               code = code + lit.hashCode();
        }
        
        return code;
    }
	
    
    public int compareTo(CNFSubClause x)
    {
        int cmp = 0;
        
        Iterator<Literal> iter = x.getLiteralsList();
        
        while(iter.hasNext())
        {
            Literal lit = iter.next();
            
            Iterator<Literal> iter2 = this.getLiterals().iterator();
                    
            while(iter2.hasNext())
            {                
                Literal lit2 = iter2.next();
                cmp = cmp + lit.compareTo(lit2);
            }
        }
        
        return cmp;
    }
}
