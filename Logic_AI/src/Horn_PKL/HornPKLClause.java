/*
 Μέλη Ομάδας

 Λόκκας Ιωάννης ΑΜ: 3120095
 Μπούζας Βασίλειος ΑΜ: 3120124
 Τασσιάς Παναγιώτης ΑΜ: 3120181

 */
package Horn_PKL;

import java.util.ArrayList;
import java.util.HashMap;

/*

 ------Κλάση HornPKLClause για αναπαράσταση προτάσεων Horn στην Πρωτοβάθμια Κατηγορηματική Λογική--------

 Αποτετελείται από μία ArrayList που περιέχει κανόνες (Rules) της μορφής Missile(x)^Owns(Nono,x)
 καθώς και από ένα συμπέρασμα που είναι μια σχέση (Relation) της μορφής Sells(West,x,Nono).

 Ο συνδυασμός τους μας δίνει τον κανόνα Missile(x)^Owns(Nono,x)=>Sells(West,x,Nono).

 */
public class HornPKLClause {

    private ArrayList<Rule> KB;
    private ArrayList<Rule> facts;
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

    private void newVars(Rule rule, String varIndex, boolean inf) {
        if (!inf) {
            for (Relation relation : rule.getClause()) {
                for (int index = 0; index < relation.getParams().size(); index++) {
                    if (!relation.getConstParam(index)) {
                        relation.setParam(index, relation.getParam(index).concat(varIndex));
                    }
                }
            }
        }
        for (int index = 0; index < rule.getInferrence().getParams().size(); index++) {
            if (!rule.getInferrence().getConstParam(index)) {
                rule.getInferrence().setParam(index, rule.getInferrence().getParam(index).concat(varIndex));
            }
        }
    }

    public HashMap<String, String> fol_fc_ask() {
        int varIndex = 1;
        Rule userRule = new Rule(null, a);

        boolean newUnify = false;
        do {
            this.facts = new ArrayList<>();
            for (int index = 0; index < this.KB.size(); index++) {
                Rule rule = this.KB.get(index);
                if (!rule.isFact()) {
                    newVars(rule, String.valueOf(varIndex), false);
                    for (int index2 = index; index2 < this.KB.size(); index2++) {
                        Rule rule2 = this.KB.get(index2);
                        if (rule2.isFact()) {
                            newVars(rule2, String.valueOf(varIndex), true);
                            unifiedVars.putAll(Unify.Unify(rule, rule2, false));
                        }
                    }

                    ArrayList<String> newParams = new ArrayList<>(rule.getInferrence().getParams());
                    for (int index3 = 0; index3 < newParams.size(); index3++) {
                        String param = newParams.get(index3);
                        if (unifiedVars.containsKey(param)) {
                            //Rule newRule = new Rule(null, new Relation(unifiedVars))
                            newUnify = true;
                            newParams.set(index3, unifiedVars.get(param));
                        }
                    }

                    if (newUnify) {
                        Relation newRelation = new Relation(rule.getInferrence().getName(), newParams, rule.getInferrence().isNegation());
                        Rule newRule = new Rule(null, newRelation);
                        this.facts.add(newRule);

                        unifiedVars.putAll(Unify.Unify(newRule, userRule, true));

                        if (!unifiedVars.isEmpty()) {
                            return unifiedVars;
                        }
                    }
                }
                varIndex++;
            }
            this.KB.addAll(this.facts);
        } while (!this.facts.isEmpty());
        return null;
    }
}
