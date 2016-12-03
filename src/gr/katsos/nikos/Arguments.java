package gr.katsos.nikos;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Arguments {
    
    public static void handle( String[] args ) {
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
        CommandLineParser parser = new DefaultParser();

        Options options = new Options()
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
                .argName("value")
                .build());

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // set the depth value that user gives
            if (line.hasOption("depth")) {
                String depthInput = line.getOptionValue("depth");
                int depth;

                try {
                    depth = Integer.parseInt(depthInput);
                    Main.getSourceFile().mapContent(depth);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid depth input!");
                    System.exit(-2);
                }
            } else {
                Main.getSourceFile().mapContent();
            }

        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }

    }        

}
