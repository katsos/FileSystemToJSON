import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exporter {

    public static File file;

    public static void export(String jsonToString) {
        export(jsonToString, Main.getSourceFile().getAbsolutePath() + ".json");
    }

    public static void export(String jsonToString, String filepath) {

        if (jsonToString == null) {
            System.err.println("No data to display");
            System.exit(5000);
        }

        file = new File(filepath);

        try {
            System.out.println("The file " + file.getAbsolutePath() +
                (file.createNewFile() ? " is created" : " already exists") );
        } catch (IOException ex) {
            // TODO: give user the option to check other available filepaths
            // to save his file instead of terminating the program
            System.err.println("Couldn't create " +  file.getAbsolutePath() + " file.");
            System.exit(3001);
        }

        try {
            FileWriter writer = new FileWriter(file);
            System.out.println(jsonToString);
            writer.write(jsonToString);
            writer.close();
        } catch (IOException ex) {
            System.err.println("Couldn't write into " +  file.getAbsolutePath() + " file.");
            System.exit(3002);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }

    }

    public static void display() {

        if( file == null ) {
            System.err.println("The file isn't exported yet, so it cannot be displayed!");
            return;
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(file.toURI());
        } catch (IOException ex) {
            System.err.println("Couldn't open " +  file.getAbsolutePath());
            System.exit(3003);
        }
    }

}