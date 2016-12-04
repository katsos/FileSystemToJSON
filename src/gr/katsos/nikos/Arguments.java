package gr.katsos.nikos;

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

    public static void handle(String[] args) {
        /* if no arguments given, start gui file chooser*/
        if (args.length == 0) {
            GUI.openFileChooser();
        } else {
            /* else the ammount of arguments is more than two */
            Main.setSourceFile(args[0]);

            if (args.length > 1) {
                Main.setDestinFile(args[1]);
                if (args.length < 3) {
                    Main.getSourceFile().mapContent();
                } else {
                    Arguments.parse(args);
                }
            }

        }
    }

    private static void parse(String[] args) {
        parser = new DefaultParser();
        setOptions();

        try {
            // parse the command line arguments
            command = parser.parse(options, args);

            // set the depth value that user gives
            if (command.hasOption("depth")) {
                int depth = parseDepthOption();
                Main.getSourceFile().mapContent(depth);
            } else {
                Main.getSourceFile().mapContent();
            }

            if (command.getOptions().length == 0) {
                Main.getSourceFile().toJson();
            } else {

                // fetch options
                // call json with array options
            }

        } catch (ParseException e) {
            System.err.println("An error occured while parsing the arguments!");
            System.err.println(e.getMessage());
            System.exit(2001);
        }

    }

    private static void setOptions() {
        options = new Options()
            .addOption("t", "type", false, "display current type")
            .addOption("p", "permissions", false, "Display current permissions")
            .addOption("n", "name", false, "display current name")
            .addOption("T", "no-type", false, "display current type")
            .addOption("P", "no-permissions", false, "display current type")
            .addOption("N", "no-name", false, "display current type")
            .addOption(Option.builder("d").longOpt("depth")
                .desc("execute to this depth value")
                .hasArg()
                .required(false)
                .build());
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

}
