import org.json.JSONObject;
import java.io.IOException;

public class Main {

    private static FileExtended sourceFile;
    private static FileExtended destinFile;
    private static JSONObject finalJson;

    public static void main(String[] args) throws Exception {
        if ( args.length == 0 ) {
            GUI.openFileChooser();
            sourceFile.mapContent();
        } else {
            Arguments.parse(args);
            sourceFile.mapContent(Arguments.getDepth());
            finalJson = Main.getSourceFile().toJson( Arguments.getOptionsGivenStringArray() ) ;
        }
        Exporter.export(finalJson);
    }

    public static void setSourceFilepath(String filepath) {
        sourceFile = new FileExtended(filepath);
        System.out.println("Source folder: " + sourceFile.getAbsoluteFile());

        if (!sourceFile.exists()) {
            System.err.println("The requested folder doesn't exist.");
            System.exit(-6);
        }
    }

    public static void setDestinFilepath(String destinFilepath ) {
        destinFile = new FileExtended(destinFilepath);
        if (!destinFile.exists()) {
            try {
                destinFile.createNewFile();
            } catch (IOException ex) {
                System.err.println("Couldn't create " + destinFile.getAbsolutePath() + " file");
                System.exit(-4);
            }
        }
    }

    public static FileExtended getDestinFile() {
        return destinFile;
    }

    public static FileExtended getSourceFile() {
        return sourceFile;
    }

}
