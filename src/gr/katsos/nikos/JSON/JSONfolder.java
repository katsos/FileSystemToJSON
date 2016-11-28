package gr.katsos.nikos.JSON;

import java.io.File;
import java.util.ArrayList;

public class JSONfolder extends JSONfile {

    private static final String type = "folder";
    private ArrayList<JSONfile> content;

    public JSONfolder(File file) {
        super(file);
    }
}
