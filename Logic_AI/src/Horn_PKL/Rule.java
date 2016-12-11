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
public class Rule {

    private ArrayList<Relation> clause;
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
}
