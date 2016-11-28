package gr.katsos.nikos;

import javax.swing.*;
import java.io.File;

public class Main {

    private static File file;
    private static JFileChooser fileChooser;

    public static void main(String[] args) {

        if ( args.length == 0) {
            openFileChooser();
        } else if ( pathIsValid(args[0]) ) {
            try {
                setFile(args[1]);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                System.gc();
            }
        }

    }

    private static void openFileChooser() {
        try {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int status = fileChooser.showOpenDialog(null);

            if ( status == JFileChooser.APPROVE_OPTION ) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                setFile(filepath);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        } finally {
            System.gc();
        }
    }

    private static void setFile(String filepath) {
        try {
            file = new File(filepath);
            System.out.println("Chosen file/folder: " + file.getAbsoluteFile());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean pathIsValid (String path) {
        return new File(path).exists();
    }
}