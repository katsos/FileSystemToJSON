package gr.katsos.nikos;

import java.io.File;
import java.util.ArrayList;

public class Mapper {

    private final File file;
    private final FileExtended jsonFile;

    public Mapper(File file) {
        this.file = file;
        jsonFile = new FileExtended(file.getAbsolutePath());
    }

    public void map() {
        if ( !file.isDirectory() ) return;
        getFilesOfFolder(file);
    }
    
    private static ArrayList<FileExtended> getFilesOfFolder( File file ) {
        
        ArrayList<FileExtended> files = new ArrayList();
        File[] filesArray = file.listFiles();
        
        for (File f : filesArray) {
            files.add( new FileExtended(f.getAbsolutePath()));
        }
        
        return files;
    }
    
    public void dummy() {
        
//        for(JSONfile f : content) {
//            System.out.println(f.getName()+ '\t' + f.getType());
//        }
        
    }

}
