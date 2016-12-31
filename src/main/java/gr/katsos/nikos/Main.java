package gr.katsos.nikos;

import org.json.JSONObject;
import java.io.IOException;

public class Main {

    private static FileExtended sourceFile = null;
    private static FileExtended destinFile = null;

    public static void main(String[] args) throws Exception {
        Arguments.parse(args);
        sourceFile.mapContent(Arguments.getDepth());
        JSONObject finalJson = Main.getSourceFile().toJson(Arguments.getOptionsGivenStringArray());
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

    public static FileExtended getSourceFile() {
        return sourceFile;
    }

    public static FileExtended getDestinFile() { return destinFile; }

}
