/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horn_PKL;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Giannis
 */
public class Unify {
    
    public static HashMap<String, String> Unify(Rule a, Rule a1) {
        HashMap<String, String> unifiedValues = new HashMap<String, String>();
        
        ArrayList<Relation> relA = a.getClause();
        ArrayList<Relation> relA1 = a1.getClause();
        
        for (int index = 0; index < relA.size(); index++) {
            Relation relAItem = relA.get(index);
            Relation relA1Item = relA1.get(index);
            
            for (int index2 = 0; index2 < relAItem.getParams().size(); index2++) {
                if (!relA1Item.getConstParams().get(index2)) {
                    unifiedValues.put(relA1Item.getParams().get(index2), relAItem.getParams().get(index2));
                }
            }
        }
        
        return unifiedValues;
    }
}
