package gr.katsos.nikos;

import java.util.ArrayList;
import java.util.List;

import gr.katsos.nikos.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Arguments {

    private static final int DEFAULT_INDENTATION = 0;
    private static final int DEFAULT_DEPTH = Integer.MAX_VALUE;

    private static CommandLine command;
    private static Options options;
    private static List<String> argsGiven;
    private static Option[] optsGiven;
    private static String[] optsGivenStringArray;

    private static int indentation;
    private static int depth;

    public static void parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        setOptions();
        try {
            command = parser.parse(options, args);
            parseArguments();
            parseOptions();
            setOptsGivenStringArray();
        } catch (Exception e) {
            System.err.println("An error occurred while parsing the arguments!");
            System.err.println(e.getMessage());
            System.exit(2001);
        }
    }

    private static void setOptions() {
        options = new Options()
            .addOption("t", "type", false, "Display the type of the file")
            .addOption("p", "permissions", false, "Display current permissions")
            .addOption(Option.builder("d").longOpt("depth")
                .desc("Search folder recursively with depth limit")
                .hasArg().required(false).build())
            .addOption(Option.builder("pr").longOpt("pretty")
                .desc("Print json pretty. Give your desired indentation")
                .hasArg().required(false).build());
    }

    /**
     * Checks command for source folder and export file arguments.
     * If there no source folder argument exits the program with error code <i><2001</i>.
     * If it meet more than 2 arguments exits the abort the program with error code <i><2002</i>.
     */
    private static void parseArguments() throws Exception {
        argsGiven = command.getArgList();
        int argsGivenSize = argsGiven.size();

        if ( argsGiven.isEmpty() ) {
            throw new Exception("No source folder given.");
        } else if( argsGivenSize > 2 ) {
            throw new Exception("Unknown argument met [" + argsGiven.get(2) + "]");
        }

        Main.setSourceFilepath(argsGiven.get(0));
        if ( argsGivenSize == 2 ) Main.setDestinFilepath(argsGiven.get(1));
    }

    private static void parseOptions() {
        setIndentation();
        setDepth();
        setOptsGivenStringArray();
    }

    /**
     * Set the list with all <i>boolean</i> options that met in the given command.
     */
    private static void setOptsGivenStringArray() {
        optsGiven = command.getOptions();

        ArrayList<String> optionsList = new ArrayList<String>();

        for( Option option : command.getOptions() ) {
            /* ignore options with variables */
            if ( option.hasArgs() ) continue;
            optionsList.add(option.getLongOpt());
        }

        optsGivenStringArray = optionsList.toArray(new String[0]);
    }

    public static String[] getOptionsGivenStringArray() {
        return optsGivenStringArray;
    }

    private static void setIndentation() {
        String prettyInput = command.getOptionValue("pretty");

        if ( prettyInput == null ) {
            indentation = DEFAULT_INDENTATION;
            return;
        }

        try {
            indentation = Integer.parseInt(prettyInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid \"prety\" input!");
            System.exit(-2);
        }
    }

    private static void setDepth() {
        String depthInput = command.getOptionValue("depth");

        if ( depthInput == null ) {
            depth = DEFAULT_DEPTH;
            return;
        }

        try {
            depth = Integer.parseInt(depthInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid \"depth\" input!");
            System.exit(-2);
        }
    }

    public static int getIndentation() {
        return indentation;
    }

    public static int getDepth() {
        return depth;
    }

}
