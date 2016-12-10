/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_ForwardChaining;

import Logic_AI.Literal;

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
