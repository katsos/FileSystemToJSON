package gr.katsos.nikos;

import java.awt.HeadlessException;
import javax.swing.JFileChooser;

public class GUI {
    
    private static JFileChooser fileChooser;
    
    public static void openFileChooser() {
        try {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int status = fileChooser.showOpenDialog(null);

            if ( status == JFileChooser.APPROVE_OPTION ) {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                Main.setSourceFile(filepath);
            } else if ( status == JFileChooser.CANCEL_OPTION ) {
                System.out.println("File choosing canceled!");
                System.exit(-5);
            }
        } catch (HeadlessException e) {
            System.err.println("Graphical Interface isn't supported " 
                    + "on your machine. Try using the programming with "
                    + "its command line arguments and options");
            System.exit(-6);
        }
    }
    
}
