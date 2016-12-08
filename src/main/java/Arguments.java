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

    public static void parse(String[] args) {
        parser = new DefaultParser();
        setOptions();

        try {
            // parse the command line arguments
            command = parser.parse(options, args);
            argsGiven = command.getArgList();
            optsGiven = command.getOptions();

            if ( argsGiven.isEmpty() ) {
                System.err.println("No source path given");
                System.exit(2001);
            }

            parseArguments();

            // set the depth value that user gives
            if (command.hasOption("depth")) {
                int depth = parseDepthOption();
                Main.getSourceFile().mapContent(depth);
            } else {
                Main.getSourceFile().mapContent();
            }

            if (command.getOptions().length == 1) { // TODO: fix this line
                Main.finalJson = Main.getSourceFile().toJson().toString();
            } else {
                String[] optionsMet = parseOptions();
//                Main.finalJson = Main.getSourceFile().toJson(optionsMet);
            }

        } catch (ParseException e) {
            System.err.println("An error occured while parsing the arguments!");
            System.err.println(e.getMessage());
            System.exit(2001);
        }

    }

    private static void setOptions() {
        options = new Options()
            .addOption("n", "name", false, "Display the name of the file")
            .addOption("t", "type", false, "Display the type of the file")
            .addOption("p", "permissions", false, "Display current permissions")
//            .addOption("T", "no-type", false, "display current type")
//            .addOption("P", "no-permissions", false, "display current type")
//            .addOption("N", "no-name", false, "display current type")
            .addOption(Option.builder("d").longOpt("depth")
                .desc("execute to this depth value")
                .hasArg()
                .required(false)
                .build());
    }

    private static void parseArguments() {
        int argsGivenSize = argsGiven.size();

        if( argsGivenSize > 2 ) {
            System.err.println("Uknown argument met");
            System.exit(2002);
        }

        Main.setSourceFile(argsGiven.get(0));

        if ( argsGivenSize == 2 ) {
            Main.setDestinFile(argsGiven.get(1));
        }
    }

    private static int parseDepthOption() {
        String depthInput = command.getOptionValue("depth");

        try {
            return Integer.parseInt(depthInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid depth input!");
            System.exit(-2);
        }

        return 0;
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

}
