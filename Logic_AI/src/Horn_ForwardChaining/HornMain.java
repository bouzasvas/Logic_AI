/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_ForwardChaining;

import Logic_AI.Literal;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Vassilis
 */
public class HornMain {

    HornClause KB;
    Literal a;

    ArrayList<HornSubClause> agenda = new ArrayList<HornSubClause>();

    public HornMain(HornClause KB, Literal a) {
        this.KB = KB;
        this.a = a;

        agenda.addAll(KB.getTrueSubClauses());
    }

    public boolean PL_FC_Entails(boolean details) {
        Iterator<HornSubClause> KB_Iterator = KB.getIterator();

        for (int index = 0; index < agenda.size(); index++) {
            HornSubClause HSubClause = agenda.get(index);
            if (details)
                System.out.println("Checking if Clause is visited before...");
            if (!HSubClause.getInferred()) {
                HSubClause.setInferred(true);

                while (KB_Iterator.hasNext()) {
                    HornSubClause HSub = KB_Iterator.next();
                    HSub.decrementCount();

                    if (HSub.getCount() == 0) {
                        if (details)
                            System.out.println("Fire the Rule!");
                        if (HSub.getInferrence().equals(a)) {
                            System.out.println("-----TRUE-----");
                            System.out.println("Conclusion " + a.toString() + " comes from KB!");
                            return true;
                        } else {
                            agenda.add(HSub);
                        }
                    }
                }
            }
        }
        System.out.println("-----FALSE-----");
        System.out.println("No conclusion occured from the KB");
        return false;
    }
}
