import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exporter {

    public static File file;

//    public static void export(String text) {
//        export(text, Main.getSourceFile().getName() + ".json");
//    }
//
//    public static void export(String data, String filepath) {
//        file = new File(filepath);
//
//        try {
//            System.out.println("The file " + file.getAbsolutePath() +
//                (file.createNewFile() ? " is created" : " already exists") );
//        } catch (IOException ex) {
//            System.err.println("Couldn't create " +  file.getAbsolutePath()
//                + " file.");
//            System.exit(3001);
//        }
//
//        try (FileWriter writer = new FileWriter(file)) {
//            writer.write(data);
//            writer.close();
//        } catch (IOException ex) {
//            System.err.println("Couldn't write in " +  file.getAbsolutePath()
//                + " file.");
//            System.exit(3002);
//        }
//
//    }
//
//    public static void display() {
//
//        if( file == null ) {
//            System.err.println("The file isn't exported yet, "
//                    + "so it cannot be displayed! ");
//            return;
//        }
//
//        try {
//            Desktop desktop = Desktop.getDesktop();
//            desktop.browse(file.toURI());
//        } catch (IOException ex) {
//            System.err.println("Couldn't open " +  file.getAbsolutePath());
//            System.exit(3003);
//        }
//    }

}