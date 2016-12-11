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
public class Relation {

    private String name;
    private ArrayList<String> params;
    private boolean negation;

    public Relation() {

    }

    public Relation(String name, ArrayList<String> params, boolean negation) {
        this.name = name;
        this.params = new ArrayList<String>(params);
        this.negation = negation;
    }
    
    public Relation(Relation rel) {
        this.name = rel.name;
        this.params = new ArrayList<String>(rel.params);
    }

    public boolean equals(Relation rel) {;
        return this.name == rel.name;
    }

    public void addParam(String param) {
        this.params.add(param);
    }

    public void printRelation() {
        if (negation)    
            System.out.print("NOT_"+this.name + "(");
        else
            System.out.print(this.name + "(");
        
        for (String str : this.params) {
            System.out.print(str+",");
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
}
