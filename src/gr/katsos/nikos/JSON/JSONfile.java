package gr.katsos.nikos.JSON;

import java.io.File;

public class JSONfile {

    private File file;
    private String name;
    private String type;
    private boolean hidden;
//    private String permissions;
    private boolean canRead, canWrite, canExecute;

    public JSONfile(File file) {
        this.file = file;

        name = file.getName();
        hidden = file.isHidden();
        setType();
        canRead = file.canRead();
        canWrite = file.canWrite();
        canExecute = file.canExecute();
    }

    private void setType() {

        if ( file.isDirectory() ) {
            type = "directory";
            return;
        }

        // TODO: add this regex check
        // this pattern matches whateverDOTwhatever...DOTwhateverEND, which whatever means "whatever except dot"
        // Pattern regex = Pattern.compile("[.^\\.]+[\\.+.+]+");
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        if (extension.isEmpty()) {
            type = "unique";
        } else {
            type = extension;
        }
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public boolean isCanExecute() {
        return canExecute;
    }
    
}