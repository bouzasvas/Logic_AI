/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package Horn_PKL;

import java.util.ArrayList;

public class Relation {

    private String name;
    private ArrayList<String> params;
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
            }
            else {
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
}
