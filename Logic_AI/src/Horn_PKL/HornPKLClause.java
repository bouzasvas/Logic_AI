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
    HashMap<String, String> unifiedVars;
    private Relation a;

    public HornPKLClause() {
        this.KB = new ArrayList<Rule>();
        this.facts = new ArrayList<Relation>();
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

    public void fol_fc_ask() {
        for (Rule rule : KB) {
            if (!rule.isFact()) {
                this.unifiedVars = new HashMap<>();
                for (Relation rel : rule.getClause()) {
                    for (Rule rule2 : KB) {
                        if (rule2.isFact()) {
                            if (rel.getName().equals(rule2.getInferrence().getName())) {
                                Map.Entry<String, String> mapEntry = Unify.Unify(rule, rule2);
                                if (mapEntry != null) {
                                    unifiedVars.entrySet().add(Unify.Unify(rule, rule2));
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
//            for (Relation relat : rule.getClause()) {
                ArrayList<String> newParams = new ArrayList<>(rule.getInferrence().getParams());
                for (int index = 0; index < rule.getInferrence().getParams().size(); index++) {
                    String param = rule.getInferrence().getParams().get(index);
                    if (unifiedVars.containsKey(param)) {
                        //Rule newRule = new Rule(null, new Relation(unifiedVars))
                        newParams.set(index, param);
                    }

                }
                Relation newRelation = new Relation(rule.getInferrence().getName(), newParams, rule.getInferrence().isNegation());
                this.facts.add(newRelation);
            } else {
                this.facts.add(rule.getInferrence());
            }
        }
    }
}
