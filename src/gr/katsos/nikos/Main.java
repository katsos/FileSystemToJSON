package gr.katsos.nikos;

import java.io.IOException;
import javax.swing.JFileChooser;
import org.apache.commons.cli.*;

public class Main {

    private static FileExtended sourceFile;
    private static FileExtended destinFile;
    private static JFileChooser fileChooser;

    public static void main(String[] args) throws Exception {
        argumentsHandler(args);
    }

    private static void parseArguments(String[] args){        
        CommandLineParser parser = new DefaultParser();        
        
        Options options = new Options();
        options.addOption("t", "type", false, "display current type");
        options.addOption("p", "permissions", false, "Display current permissions");
        options.addOption("n", "name", false, "display current name");        
        options.addOption("T", "no-type", false, "display current type");
        options.addOption("P", "no-permissions", false, "display current type");
        options.addOption("N", "no-name", false, "display current type");
        options.addOption( Option.builder( "d" ).longOpt( "depth" )
                                .desc( "execute to this depth value" )
                                .hasArg()
                                .required( false )
                                .argName( "value" )
                                .build() );
        
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            
            // set the depth value that user gives
            if( line.hasOption( "depth" ) ) {
                String depthInput = line.getOptionValue( "depth" );
                int depth;
                
                try {
                    depth = Integer.parseInt(depthInput);
                    sourceFile.mapContent(depth);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid depth input!");
                    System.exit(-2);
                }
            }
            else {
                sourceFile.mapContent();
            }             

        }
        catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
    }

    private static void argumentsHandler(String[] args) throws Exception {
        /* if no arguments given, start gui file chooser*/
        if ( args.length == 0 ) {
            openFileChooser();
        } else {
            /* else the ammount of arguments is more than two */
           sourceFile = new FileExtended(args[0]);

           if ( args.length > 1 ) {
               destinFile = new FileExtended(args[1]);
               if ( !destinFile.exists() ) {
                   try {
                       destinFile.createNewFile();
                   } catch (IOException ex) {
                       System.err.println("Couldn't create " 
                               + destinFile.getAbsolutePath());
                       System.exit(-4);
   //                    System.out.println("The output file "
   //                            + sourceFile.getAbsolutePath() + ".json"
   //                            + " is going to be created automatically");
                   }
               }

               if ( args.length < 3 ) {
                   sourceFile.mapContent();
               } else {
                   parseArguments(args);
               }
           }           
            
        }
        
        sourceFile.toJson();
    }

    private static void openFileChooser() {
        try {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int status = fileChooser.showOpenDialog(null);

            if ( status == JFileChooser.APPROVE_OPTION ) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                setFile(filepath);
            } else if ( status == JFileChooser.CANCEL_OPTION ) {
                System.out.println("File choosing canceled!");
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    private static void setFile(String filepath) {
        try {
            sourceFile = new FileExtended(filepath);
            String mess = "Chosen " + (sourceFile.isDirectory() ? "folder" : "folder") 
                    + ": " + sourceFile.getAbsoluteFile();
            System.out.println(mess);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}