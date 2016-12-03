package gr.katsos.nikos;

import java.io.File;
import javax.swing.JFileChooser;
import org.apache.commons.cli.*;

public class Main {

    private static FileExtended file;
    private static JFileChooser fileChooser;

    public static void main(String[] args) {
        file = new FileExtended("C:\\Program Files");

        file.mapContent(2);
        
        try {
            System.out.println(file.toJson());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        String val; //= new String[];
        
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
                       
            // set the depth value that user gives
            if( line.hasOption( "depth" ) ) {
                val = line.getOptionValue( "depth" );
                mapContent(val);
            }
            else {
                mapContent();
            }             
            
            while( line.iterator().hasNext() ){
                
            }
        }
        catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
    }

    private static void argumentsHandler(String[] args) {
        if ( args.length < 2 ) {
            openFileChooser();
            return;
        }
        if( args.length == 2 ){
            if( pathIsValid(args[1]) ){
                setFile(args[1]);
                return;
            }

            System.err.println("Invalid arguments");
            System.exit(-1); 
        }

        parseArguments(args);
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
        } finally {
            System.gc();
        }
    }

    private static void setFile(String filepath) {
        try {
            file = new FileExtended(filepath);
            String mess = "Chosen " + (file.isDirectory() ? "folder" : "folder") 
                    + ": " + file.getAbsoluteFile();
            System.out.println(mess);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean pathIsValid (String path) {
        return new File(path).exists();
    }
}