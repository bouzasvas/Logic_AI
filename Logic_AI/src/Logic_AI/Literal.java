/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Logic_AI;

/*

-------------------------------------Literal.java----------------------------------

    Η κλάση αυτή αναπαριστά κάθε σύμβολο της Βάσης Γνώσης για την Προτασιακή Λογική
    πχ. A,~B κτλ.

*/

public class Literal implements Comparable<Literal> {

    //The name of the literal
    private String Name;
    //Whether or not the literal is negated; if negation is true then it is negated
    private boolean negation;
    
    private boolean inferred = false;

    public Literal(String n, boolean neg) {
        this.Name = n;
        this.negation = neg;
    }

    public Literal(Literal l) {
        this.Name = l.Name;
        this.negation = l.negation;
        this.inferred = l.inferred;
    }

    public void print() {
        if (negation) {
            System.out.println("NOT_" + Name);
        } else {
            System.out.println(Name);
        }
    }

    public void printNoBR() {
        if (negation) {
            System.out.print("NOT_" + Name);
        } else {
            System.out.print(Name);
        }
    }

    public boolean isInferred() {
        return inferred;
    }

    public void setInferred(boolean isInferred) {
        this.inferred = isInferred;
    }
    
    public void setName(String n) {
        this.Name = n;
    }

    public String getName() {
        return this.Name;
    }

    public void setNeg(boolean b) {
        this.negation = b;
    }

    public boolean getNeg() {
        return this.negation;
    }

    //Override
    public boolean equals(Object obj) {
        Literal l = (Literal) obj;

        if (l.getName().compareTo(this.Name) == 0 && l.getNeg() == this.negation) {
            return true;
        } else {
            return false;
        }

    }

    //@Override
    public int hashCode() {
        if (this.negation) {
            return this.Name.hashCode() + 1;
        } else {
            return this.Name.hashCode() + 0;
        }
    }

    //@Override
    public int compareTo(Literal x) {
        int a = 0;
        int b = 0;

        if (x.getNeg()) {
            a = 1;
        }

        if (this.getNeg()) {
            b = 1;
        }

        return x.getName().compareTo(Name) + a - b;
    }

    @Override
    public String toString() {
        if (negation)
            return "NOT_"+this.Name;
        else
            return this.Name;
    }
}
