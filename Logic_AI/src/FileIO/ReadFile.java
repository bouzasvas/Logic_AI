package FileIO;

import logic_ai.CNFSubClause;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import logic_ai.Literal;

/**
 *
 * @author Tassias
 */
public class ReadFile {

    private static BufferedReader reader = null;

    public static List CNFReadFile(String filename) {
        List subClauses = new ArrayList<CNFSubClause>();
        
        CNFSubClause cnf;
        
        File file = new File(filename);

        try {
            reader = new BufferedReader(new FileReader(file));
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] andSubClause = line.split("\\^");
                
                for (String andStr : andSubClause) {
                    cnf = new CNFSubClause();
                    
                    andStr = andStr.trim();
                    String[] orSubClause = andStr.split("\\|");
                    
                    for (String orStr : orSubClause) {
                        orStr = orStr.replace("(", "");
                        orStr = orStr.replace(")", "");
                        
                        if (orStr.startsWith("~"))
                            cnf.getLiterals().add(new Literal(orStr.substring(1), true));
                        else
                            cnf.getLiterals().add(new Literal(orStr, false));
                    }
                    subClauses.add(cnf);
                }
            }
            
        } catch (IOException ex) {
            System.err.println("Could not open file:\n" + file.getAbsolutePath());
            System.exit(-1);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.err.println("Could not close file:\n" + file.getAbsolutePath());
            }
        }
        
        return subClauses;
    }
}
