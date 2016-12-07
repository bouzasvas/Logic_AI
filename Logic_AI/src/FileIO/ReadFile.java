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
    private static File file = null;

    public static void initBuffer(String filename) {
        file = new File(filename);

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException ex) {
            System.err.println("Could not open file:\n" + file.getAbsolutePath());
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }

    public static void closeFile() {
        try {
            reader.close();
            file = null;
        } catch (IOException ex) {
            System.err.println("Could not close file:\n" + file.getAbsolutePath());
            System.err.println(ex.getMessage());
        }
    }

    public static List CNFReadFile(String filename) {
        initBuffer(filename);
        
        List subClauses = new ArrayList<CNFSubClause>();

        CNFSubClause cnf;

        try {
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

                        if (orStr.startsWith("~")) {
                            cnf.getLiterals().add(new Literal(orStr.substring(1), true));
                        } else {
                            cnf.getLiterals().add(new Literal(orStr, false));
                        }
                    }
                    subClauses.add(cnf);
                }
            }

        } catch (IOException ex) {
            System.err.println("Something went wrong while trying to read from file:\n" + file.getAbsolutePath());
            System.err.println(ex.getMessage());
        }

        closeFile();
        
        return subClauses;
    }
}
