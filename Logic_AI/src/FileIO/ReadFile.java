/*
    Μέλη Ομάδας

Λόκκας Ιωάννης ΑΜ: 3120095
Μπούζας Βασίλειος ΑΜ: 3120124
Τασσιάς Παναγιώτης ΑΜ: 3120181

*/

package FileIO;

import CNF_Resolution.*;
import Horn_ForwardChaining.*;
import Horn_PKL.Relation;
import Horn_PKL.Rule;
import Logic_AI.*;
import Horn_PKL.HornPKLClause;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

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

                //Commented Lines
                if (line.startsWith("#")) {
                    continue;
                }

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

                //Commented Lines
                if (line.startsWith("#")) {
                    continue;
                }

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
            System.err.println("Could not read the next line of file: " + file.getAbsolutePath());
            System.err.println(ex.getMessage());
        }

        closeFile();

        return horn;
    }

    public static HornPKLClause HornPKL(String filename) {
        initBuffer(filename);

        String line;
        HornPKLClause hornClause = new HornPKLClause();

        try {
            while ((line = reader.readLine()) != null) {
                Relation rel;
                Rule rule;

                if (line.startsWith("#")) {
                    continue;
                }

                String[] infSplit = line.split("=>");

                if (infSplit.length == 1) {
                    int leftParIndex = infSplit[0].lastIndexOf("(");

                    String relName = infSplit[0].substring(0, leftParIndex);
                    ArrayList<String> params = new ArrayList<String>();

                    String paramsStr = infSplit[0].substring(leftParIndex + 1);
                    paramsStr = paramsStr.replace(")", "");

                    String[] paramsList = paramsStr.split(",");

                    for (String str : paramsList) {
                        params.add(str);
                    }

                    if (relName.startsWith("~")) {
                        rel = new Relation(relName.substring(1), params, true);
                    } else {
                        rel = new Relation(relName, params, false);
                    }

                    rule = new Rule(null, rel);
                } else {
                    ArrayList<Relation> clauses = new ArrayList<Relation>();
                    Relation inf;

                    String[] andClause = infSplit[0].split("\\^");
                    String infStr = infSplit[1];

                    for (String andStr : andClause) {
                        ArrayList<String> params = new ArrayList<String>();

                        int leftParIndex = andStr.indexOf("(");

                        String relName = andStr.substring(0, leftParIndex);

                        String paramsStr = andStr.substring(leftParIndex + 1);
                        paramsStr = paramsStr.replace(")", "");

                        String[] paramsList = paramsStr.split(",");

                        for (String str : paramsList) {
                            params.add(str);
                        }

                        if (relName.startsWith("~")) {
                            rel = new Relation(relName.substring(1), params, true);
                        } else {
                            rel = new Relation(relName, params, false);
                        }
                        clauses.add(rel);
                    }

                    int leftParIndex = infStr.lastIndexOf("(");

                    String relName = infStr.substring(0, leftParIndex);
                    ArrayList<String> params = new ArrayList<String>();

                    String paramsStr = infStr.substring(leftParIndex + 1);
                    paramsStr = paramsStr.replace(")", "");

                    String[] paramsList = paramsStr.split(",");

                    for (String str : paramsList) {
                        params.add(str);
                    }
                    
                    if (infStr.startsWith("~")) {
                        inf = new Relation(relName.substring(1), params, true);
                    } else {
                        inf = new Relation(relName, params, false);
                    }

                    rule = new Rule(clauses, inf);
                }
                hornClause.addHornClause(rule);
            }
        } catch (IOException ex) {
            System.err.println("Could not read next line");
            System.err.println(ex.getMessage());
        }

        closeFile();
        
        return hornClause;
    }
}
