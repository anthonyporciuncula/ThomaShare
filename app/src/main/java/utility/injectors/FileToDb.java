package utility.injectors;

/**
 * Created by Cerjeff Pineda on 4/5/2017.
 */

public class FileToDb {
    public String displayName;
    public String uploaderName;
    public String fileTag;
    public String downloadUri;
    public String dateUploaded;

    public FileToDb(){

    }

    public FileToDb(String displayName, String uploaderName, String fileTag, String downloadUri, String dateUploaded){
        this.displayName = displayName;
        this.uploaderName = uploaderName;
        this.fileTag = fileTag;
        this.downloadUri = downloadUri;
        this.dateUploaded = dateUploaded;
    }
}
