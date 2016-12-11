/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_PKL;

import java.util.ArrayList;

/**
 *
 * @author Giannis
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
