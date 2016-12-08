package FileIO;

import CNF_Resolution.*;
import Horn_ForwardChaining.*;
import Logic_AI.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

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

    public static CNFClause CNFReadFile(String filename) {
        initBuffer(filename);

        CNFSubClause cnf;
        CNFClause KB = new CNFClause();

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
                    KB.getSubclauses().add(cnf);
                }
            }

        } catch (IOException ex) {
            System.err.println("Something went wrong while trying to read from file:\n" + file.getAbsolutePath());
            System.err.println(ex.getMessage());
        }

        closeFile();

        return KB;
    }

    public static HornClause HornForwardChaining(String filename) {
        initBuffer(filename);

        String line;
        HornClause horn = new HornClause();

        try {
            while ((line = reader.readLine()) != null) {
                HornSubClause hornSub = null;
                Literal literal;
                String[] hornParts = line.split("=>");

                if (hornParts.length == 1) {
                    if (hornParts[0].startsWith("~")) {
                        literal = new Literal(hornParts[0], true);
                    } else {
                        literal = new Literal(hornParts[0], false);
                    }
                    hornSub = new HornSubClause(null, literal);
                } else {
                    hornSub = new HornSubClause();

                    String[] leftPart = hornParts[0].split("\\^");

                    for (String l : leftPart) {
                        if (l.startsWith("~")) {
                            literal = new Literal(l, true);
                        } else {
                            literal = new Literal(l, false);
                        }
                        hornSub.addArrLiteral(literal);
                    }

                    String rightPart = hornParts[1];

                    if (rightPart.startsWith("~")) {
                        literal = new Literal(rightPart, true);
                    } else {
                        literal = new Literal(rightPart, false);
                    }
                    hornSub.setInference(literal);
                    hornSub.calculateCount();
                }

                horn.addHornSubClause(hornSub);
            }
        } catch (IOException ex) {
            System.err.println("Could not read the next line of file: "+ file.getAbsolutePath());
            System.err.println(ex.getMessage());
        }

        closeFile();

        return horn;
    }
}
