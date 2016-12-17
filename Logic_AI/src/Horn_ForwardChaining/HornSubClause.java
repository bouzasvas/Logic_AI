/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_ForwardChaining;

import Logic_AI.Literal;
import java.util.ArrayList;

public class HornSubClause {

    private ArrayList<Literal> clause;
    private Literal inferrence;
    private int count;

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

    public boolean containsLiteral(Literal lit) {
        boolean exists = false;

        if (clause != null) {
            for (Literal l : clause) {
                if (l.equals(lit)) {
                    exists = true;
                }
            }
        }

        return exists;
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
