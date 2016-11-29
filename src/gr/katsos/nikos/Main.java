package gr.katsos.nikos;

import java.io.File;
import javax.swing.JFileChooser;

public class Main {

    private static File file;
    private static JFileChooser fileChooser;
    private static Mapper fileMapper;

    public static void main(String[] args) {

        argumentsHandler(args);

        if ( file != null && file.exists() ) {
            fileMapper = new Mapper(file);
            fileMapper.map();
            fileMapper.dummy();
        }

    }

    private static void argumentsHandler(String[] args) {
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
        } else {
            System.err.println("Invalid arguments");
            System.exit(-1);
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
            file = new File(filepath);
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