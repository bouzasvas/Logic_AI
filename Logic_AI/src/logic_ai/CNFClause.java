package logic_ai;

import java.util.Vector;

/*
 * A CNFClause consists of a conjunction of CNFSubClauses
 * And each CNFSubClause in turn consists of a disjunction of Literals
 */
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
