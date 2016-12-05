/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic_ai;

import FileIO.ReadFile;
import java.util.List;
/**
 *
 * @author Tassias
 */
public class Logic_AI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List l = ReadFile.CNFReadFile("test.txt");
        
        for (int index = 0; index < l.size(); index++) {
            CNFSubClause c = (CNFSubClause) l.get(index);
            c.print();
        }
    }
    
}
