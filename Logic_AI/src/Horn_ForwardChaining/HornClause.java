/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_ForwardChaining;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Vassilis
 */
public class HornClause {

    ArrayList<HornSubClause> KB;
    
    public HornClause() {
        KB = new ArrayList<HornSubClause>();
    }
    
    public void addHornSubClause(HornSubClause subClause) {
        KB.add(subClause);
    }
    
    public ArrayList<HornSubClause> getTrueSubClauses() {
        ArrayList<HornSubClause> trueSubClauses = new ArrayList<HornSubClause>();
        
        for (HornSubClause hsc : KB) {
            if (hsc.getClause() == null) {
                trueSubClauses.add(hsc);
            }
        }
        
        return trueSubClauses;
    }
    
    public void print() {
        for (HornSubClause subClause : KB) {
            subClause.printSubClause();
        }
    }
    
    public Iterator<HornSubClause> getIterator() {
        return this.KB.iterator();
    }
}
