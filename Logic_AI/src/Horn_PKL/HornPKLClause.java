/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

 */
package Horn_PKL;

import java.util.ArrayList;
import Horn_PKL.Unify;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*

------Κλάση HornPKLClause για αναπαράσταση προτάσεων Horn στην Πρωτοβάθμια Κατηγορηματική Λογική--------

    Αποτετελείται από μία ArrayList που περιέχει κανόνες (Rules) της μορφής Missile(x)^Owns(Nono,x)
    καθώς και από ένα συμπέρασμα που είναι μια σχέση (Relation) της μορφής Sells(West,x,Nono).

    Ο συνδυασμός τους μας δίνει τον κανόνα Missile(x)^Owns(Nono,x)=>Sells(West,x,Nono).

 */
public class HornPKLClause {

    private ArrayList<Rule> KB;
    private ArrayList<Relation> facts;
    private HashMap<String, String> unifiedVars;
    private Relation a;

    public HornPKLClause() {
        this.KB = new ArrayList<Rule>();
        this.unifiedVars = new HashMap<>();
        this.facts = new ArrayList<>();
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

    public Map.Entry<String, String> fol_fc_ask() {
        boolean newUnify = false;
        //do {
            this.facts = new ArrayList<>();
            for (int index = 0; index < this.KB.size(); index++) {
                Rule rule = this.KB.get(index);
                if (!rule.isFact()) {
                    for (Relation rel : rule.getClause()) {
                        for (int index3 = 0; index3 < this.KB.size(); index3++) {
                            Rule rule2 = this.KB.get(index3);
                            if (rule2.isFact()) {
                                Map.Entry<String, String> mapEntry = Unify.Unify(rel, rule2.getInferrence());
                                if (mapEntry != null) {
                                    unifiedVars.put(mapEntry.getKey(), mapEntry.getValue());
                                }
                            }
                        }
                    }
//            for (Relation relat : rule.getClause()) {
                    ArrayList<String> newParams = new ArrayList<>(rule.getInferrence().getParams());
                    for (int index2 = 0; index2 < newParams.size(); index2++) {
                        String param = newParams.get(index2);
                        if (unifiedVars.containsKey(param)) {
                            //Rule newRule = new Rule(null, new Relation(unifiedVars))
                            newUnify = true;
                            newParams.set(index2, unifiedVars.get(param));
                        }
                    }

                    if (newUnify) {
                        Relation newRelation = new Relation(rule.getInferrence().getName(), newParams, rule.getInferrence().isNegation());
                        this.facts.add(newRelation);          
                        
                        Map.Entry<String, String> mapUnify = Unify.Unify(newRelation, a);
                        
                        if (mapUnify != null) {
                            return mapUnify;                           
                        }
                        this.KB.add(new Rule(null, newRelation));
                    }
                }
            }
        //} while (!this.facts.isEmpty());
        return null;
    }
}
