package gitlet;

import java.io.Serializable;
import java.util.List;

public class Commit implements Serializable {

    private String log;
    private String timestamp;
    private String parent;
    private List<String> blobs;

    public Commit() {
        log = "initial commit";
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        parent = null;
        blobs = null;
    }

    public Commit(String log, String timestamp, String parent, List<String> blobs) {
        this.log = log;
        this.timestamp = timestamp;
        this.parent = parent;
        this.blobs = blobs;
    }
}
