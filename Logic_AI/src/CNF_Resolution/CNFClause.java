/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package CNF_Resolution;

import java.util.Vector;

public class CNFClause 
{
    public Vector<CNFSubClause> theClauses = new Vector<CNFSubClause>();
    
    public Vector<CNFSubClause> getSubclauses()
    {
        return theClauses;
    }
    
    public boolean contains(CNFSubClause newS)
    {
        for(int i = 0; i < theClauses.size(); i ++)
        {
            if(theClauses.get(i).getLiterals().equals(newS.getLiterals()))
            {
                return true;
            }
        }
        return false;
    }
}
