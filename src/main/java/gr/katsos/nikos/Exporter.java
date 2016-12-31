package gr.katsos.nikos;

import org.json.JSONObject;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Exporter {

    public static File file;
    private static JSONObject jsonObject;

    public static void export(JSONObject jsonToString) {
        export(jsonToString, Main.getSourceFile().getAbsolutePath() + ".json");
    }

    public static void export(JSONObject jsonObject, String filepath) {

        if (jsonObject == null) {
            System.err.println("No data to display");
            System.exit(500);
        }

        Exporter.jsonObject = jsonObject;
        file = new File(filepath);

        askUserForOverwrite();

        createExportFile();

        writeFile();
    }

    private static void askUserForOverwrite() {
        String answer;

        if ( !file.exists() ) {
            return;
        }

        System.out.println("The file " + file.getAbsoluteFile() + " already exists.");
        System.out.println("Do you want to overwrite it? (y/n)");

        Scanner scanner = new Scanner(System.in);
        answer = scanner.nextLine().toLowerCase();

        while ( answer.equals("y") && answer.equals("n") ) {
            System.err.println("Invalid answer. Press \"y\" for yes or \"n\" for no.");
            System.out.println("Do you want to overwrite it? (y/n)");
            answer = scanner.nextLine().toLowerCase();
        }

        if ( answer.equals("n") ) {
            System.exit(0);
        }
    }

    private static void createExportFile() {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.err.println("Couldn't create " +  file.getAbsolutePath() + " file.");
            // TODO: give user the option to check other available filepaths
            // to save his file instead of terminating the program
            System.exit(3001);
        }
    }

    private static void writeFile() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(jsonObject.toString(Arguments.getIndentation()));
            writer.close();
        } catch (IOException ex) {
            System.err.println("Couldn't write into " +  file.getAbsolutePath() + " file.");
            System.exit(3002);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

}