import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.json.*;

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
        type = this.isDirectory()
                ? "directory" : getName().substring(getName().lastIndexOf('.') + 1);
    }

    private void setPermissions() {
        StringBuilder sb = new StringBuilder();

        if (this.canRead()) {
            sb.append("read ");
        }
        if (this.canWrite()) {
            sb.append("write ");
        }
        if (this.canExecute()) {
            sb.append("execute");
        }

        this.permissions = sb.toString().trim().replace(' ', '-');
    }

    /**
     * Convert listFiles array attribute of java.io.File to ArrayList of
     * FileExtended object (for files) or ArrayList of FileExtended objects (for
     * folders).
     */
    private void setContent() {
        content = new ArrayList();
        File[] filesList = this.listFiles();
        for (File f : filesList) {
            if ( f.isHidden() ) continue;
            content.add(new FileExtended(f.getAbsolutePath()));
            System.out.println(f.getAbsolutePath());
        }
    }

    public void mapContent() {
        mapContent(Integer.MAX_VALUE);
    }

    /** dedicated variable for mapContent() method */
    private static int depthExplored = 1;
    /**
     * Requests root file to search recursively into its sub-folders
     * and map files and folders below it.
     * @param maxDepth the maximum depth to search for files
     */
    public void mapContent(int maxDepth) {
        setContent();
        for (FileExtended file : content) {
            if (file.isDirectory() && depthExplored < maxDepth) {
                depthExplored++;
                file.mapContent(maxDepth); // Recursion
                depthExplored--;
            }
        }
    }

    public ArrayList<FileExtended> getContent() {
        if (content == null) {
            setContent();
        }

        return content;
    }

    public String getType() {
        return type;
    }

    public String getPermissions() {
        return permissions;
    }

    /**
     * Print the default JSON format.
     * @return the JSON result as a string
     */
    public JSONObject toJson() {
        return toJson(null);
    }

    private JSONObject toJson(String[] options) { // TODO: add options exceptions
        JSONObject jsonObject = new JSONObject();

        Field[] fields = FileExtended.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(this) == null) {
                    continue;
                }
                jsonObject.put(field.getName(), field.get(this));
            } catch (Exception ex) {
                System.err.println("Couldn't convert file " + this.getName() + " into json!");
                System.err.println(ex.getMessage());
                System.exit(666);
            }
        }

        if (isDirectory() && content != null) {
            JSONArray contentJSONArray = this.contentToJson();
            jsonObject.put("content", contentJSONArray);
        }

        return jsonObject;
    }

    private JSONArray contentToJson() {
        JSONArray contentJSONArray = new JSONArray();
        for (FileExtended file : content) {
            contentJSONArray.put(file.toJson(null));
        }
        return contentJSONArray;
    }

}
