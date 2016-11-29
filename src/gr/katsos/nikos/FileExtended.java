package gr.katsos.nikos;

import java.io.File;
import java.util.ArrayList;

public class FileExtended extends File {

    private String type;
    private String permissions;
    private ArrayList<FileExtended> content;

    public FileExtended(String filename) {
        super(filename);
        setType();
        setPermissions();
    }

    private void setType() {

        if ( this.isDirectory() ) {
            type = "directory";
            return;
        }

        // TODO: add this regex check
        // this pattern matches whateverDOTwhatever...DOTwhateverEND, which whatever means "whatever except dot"
        // Pattern regex = Pattern.compile("[.^\\.]+[\\.+.+]+");
        String filename = this.getName();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        if (extension.isEmpty()) {
            type = "unknown";
        } else {
            // TODO: add converter for human readable types
            type = extension;
        }
    }
    
    private void setPermissions() {
        StringBuilder sb = new StringBuilder();
        
        if ( this.canRead() ) 
            sb.append("read ");
        if ( this.canWrite() )
            sb.append("write ");
        if ( this.canExecute() )
            sb.append("execute");
                  
        this.permissions = sb.toString().trim().replace(' ', '-');
    }

    private void setContent( ArrayList<FileExtended> content ) {
        this.content = content;
    }
    
    public String getType() {
        return type;
    }
    
    public String getPermissions() {
        return permissions;
    }
}