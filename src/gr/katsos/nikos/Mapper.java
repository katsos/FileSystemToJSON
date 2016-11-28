package gr.katsos.nikos;

import java.io.File;
import gr.katsos.nikos.JSON.JSONfile;

public class Mapper {

    private final File file;
    private final JSONfile jsonFile;

    public Mapper(File file) {
        this.file = file;
        jsonFile = new JSONfile(file);
    }

    public void map() {

        if ( !file.isDirectory() ) return;
        
        File[] files = file.listFiles();
        if ( files.length == 0 ) return;

//        for (File file : files) {
//            content.add( new JSONfile(file) );
//        }

    }
    
    public void dummy() {
        
//        for(JSONfile f : content) {
//            System.out.println(f.getName()+ '\t' + f.getType());
//        }
        
    }

}
