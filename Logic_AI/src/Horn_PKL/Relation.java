/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

 */
package Horn_PKL;

import java.util.ArrayList;

/*

------------------------------Κλάση Relation.java----------------------------------

    Αναπαριστά τις σχέσεις την Πρωτοβάθμιας Κατηγορηματικής Λογικής πχ. Human(John)

 */
public class Relation {

    //  Όνομα σχέσης
    private String name;
    //  Παράμετροι σχέσης
    private ArrayList<String> params;
    //  Λίστα που αναπαριστά ποιες από τις παραπάνω παραμέτρους είναι σταθερές (πχ. John)
    private ArrayList<Boolean> constParam;
    private boolean negation;

    public Relation() {

    }

    public Relation(String name, ArrayList<String> params, boolean negation) {
        this.name = name;
        this.params = new ArrayList<String>(params);
        this.negation = negation;

        this.constParam = new ArrayList<Boolean>();

        for (int index = 0; index < this.params.size(); index++) {
            if (this.params.get(index).toLowerCase().equals(this.params.get(index))) {
                this.constParam.add(false);

            } else {
                this.constParam.add(true);
            }
        }
    }

    public Relation(Relation rel) {
        this.name = rel.name;
        this.params = new ArrayList<String>(rel.params);

        this.constParam = new ArrayList<Boolean>(rel.constParam);
    }

    public boolean equals(Relation rel) {;
        return this.name == rel.name;
    }

    public void addParam(String param) {
        this.params.add(param);
    }

    public void printRelation() {
        if (negation) {
            System.out.print("NOT_" + this.name + "(");
        } else {
            System.out.print(this.name + "(");
        }

        for (String str : this.params) {
            System.out.print(str + ",");
        }
        System.out.print(")");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public ArrayList<Boolean> getConstParams() {
        return this.constParam;
    }

    public void setConstParams(ArrayList<Boolean> constParam) {
        this.constParam = constParam;
    }

    public boolean getConstParam(int index) {
        return this.constParam.get(index);
    }

    public String getParam(int index) {
        return this.params.get(index);
    }

    public void setParam(int index, String param) {
        this.params.set(index, param);
    }

    @Override
    public String toString() {
        super.toString();

        String rule2string = this.getName() + "(";

        for (int index = 0; index < this.getParams().size(); index++) {
            if (index == this.getParams().size() - 1) {
                rule2string = rule2string.concat(this.getParam(index));
            } else {
                rule2string = rule2string.concat(this.getParam(index) + ",");
            }
        }

        rule2string = rule2string.concat(")");

        return rule2string;
    }

}
