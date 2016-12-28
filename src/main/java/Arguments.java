import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Arguments {

    private static CommandLineParser parser;
    private static CommandLine command;
    private static Options options;
    private static List<String> argsGiven;
    private static Option[] optsGiven;
    private static String[] optionsMet;

    public static void parse(String[] args) {
        parser = new DefaultParser();
        setOptions();

        try {
            // parse the command line arguments
            command = parser.parse(options, args);
            argsGiven = command.getArgList();
            optsGiven = command.getOptions();

            parseArguments();
            mapContent();
            System.out.println(optsGiven.length);

            if (optsGiven.length == 0) {
                Main.setFinalJson(Main.getSourceFile().toJson());
            } else {
                optionsMet = parseOptions();
                Main.setFinalJson(Main.getSourceFile().toJson(optionsMet));
            }

        } catch (ParseException e) {
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
            .addOption(Option.builder("pr")
                .longOpt("pretty")
                .desc("Print json pretty. Give your desired indentation")
                .hasArg().required(false).build());
    }

    private static void parseArguments() {
        int argsGivenSize = argsGiven.size();

        if ( argsGiven.isEmpty() ) {
            System.err.println("No source folder given.");
            System.exit(2001);
        } else if( argsGivenSize > 2 ) {
            System.err.println("Unknown argument met [" + argsGiven.get(2) + "]");
            System.exit(2002);
        }

        Main.setSourceFile(argsGiven.get(0));

        if ( argsGivenSize == 2 ) {
            Main.setDestinFile(argsGiven.get(1));
        }
    }

    private static void mapContent() {
        // set the depth value that user gives
        if (command.hasOption("depth")) {
            int depth = parseDepthOption();
            Main.getSourceFile().mapContent(depth);
        } else {
            Main.getSourceFile().mapContent();
        }
    }

    private static int parseDepthOption() {
        String depthInput = command.getOptionValue("depth");
        if ( depthInput == null ) return Integer.MAX_VALUE;

        try {
            return Integer.parseInt(depthInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid depth input!");
            System.exit(-2);
            return -2;
        }
    }

    private static int parsePrettyOption() {
        String prettyInput = command.getOptionValue("pretty");
        if ( prettyInput == null ) return 0;
        try {
            return Integer.parseInt(prettyInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid pretty-json input!");
            System.exit(-2);
            return -2;
        }
    }

    private static String[] parseOptions() {
        ArrayList<String> optionsList = new ArrayList<String>();

        for( Option option : command.getOptions() ) {
            /* ignore options with variables */
            if ( option.hasArgs() ) continue;
            optionsList.add(option.getLongOpt());
        }

        return optionsList.toArray(new String[0]);
    }

    public static String[] getOptionsGiven() {
        ArrayList<String> optionsGiven = new ArrayList<String>();

        for(Option o : optsGiven) {
            optionsGiven.add(o.getOpt());
        }

        return optionsGiven.toArray(new String[0]);
    }

    public static int getIndentation() {

        if ( Util.contains(optionsMet, "pr") ) return 0;

        return parsePrettyOption();
    }
}
