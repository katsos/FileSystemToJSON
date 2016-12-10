import java.io.IOException;

public class Main {

    private static FileExtended sourceFile;
    private static FileExtended destinFile;
    public static String finalJson;

    public static void main(String[] args) throws Exception {
        if ( args.length == 0 ) {
            GUI.openFileChooser();
        } else {
            Arguments.parse(args);
        }
        Exporter.export(finalJson);
    }

    public static void setSourceFile(String filepath) {
        sourceFile = new FileExtended(filepath);
        System.out.println("Source folder: " + sourceFile.getAbsoluteFile());

        if (!sourceFile.exists()) {
            System.err.println("The requested folder doesn't exist.");
            System.exit(-6);
        }
    }

    public static void setDestinFile( String destinFilepath ) {
        destinFile = new FileExtended(destinFilepath);
        if (!destinFile.exists()) {
            try {
                destinFile.createNewFile();
            } catch (IOException ex) {
                System.err.printf("Couldn't create %s file",
                        destinFile.getAbsolutePath());
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
