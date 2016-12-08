/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_ForwardChaining;

import Logic_AI.Literal;
import java.util.ArrayList;

/**
 *
 * @author Vassilis
 */
public class HornSubClause {

    private ArrayList<Literal> clause;
    private Literal inferrence;
    private int count;
    
    private boolean isInferred;

    public HornSubClause() {
        this.clause = new ArrayList<Literal>();
    }

    public HornSubClause(ArrayList<Literal> clause, Literal inference) {
        if (clause != null) {
            this.clause = new ArrayList<Literal>();
            this.clause.addAll(clause);
        }
        this.inferrence = new Literal(inference.getName(), inference.getNeg());
        this.calculateCount();
    }

    public void addArrLiteral(Literal literal) {
        this.clause.add(literal);
    }
    
    public ArrayList<Literal> getClause() {
        return this.clause;
    }

    public void setInference(Literal literal) {
        this.inferrence = new Literal(literal.getName(), literal.getNeg());
    }
    
    public Literal getInferrence() {
        return this.inferrence;
    }
    
    public void setInferred(boolean inf) {
        this.isInferred = inf;
    }
    
    public boolean getInferred() {
        return this.isInferred;
    }

    public void calculateCount() {
        if (this.clause != null) {
            this.count = this.clause.size();
        } else {
            this.count = 0;
        }
    }
    
    public void decrementCount() {
        this.count--;
    }
    
    public int getCount() {
        return this.count;
    }

    public void printSubClause() {
        System.out.println("------------------");
        if (clause != null) {
            for (int index = 0; index < clause.size(); index++) {
                if (index != 0) {
                    System.out.print("^");
                }
                this.clause.get(index).printNoBR();
            }
            System.out.print("=>");
        }
        inferrence.print();
        System.out.println("------------------");
    }
}
