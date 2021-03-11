package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Commit implements Serializable {

    private final String log;
    private final String timestamp;
    private final String parent;
    private final HashMap<String, String> files;
    private static final long serialVersionUID = 6529685098267757690L;

    public Commit() {
        log = "initial commit";
        timestamp = (new Date()).toString();
        parent = null;
        files = new HashMap<>();
    }

    public Commit(String log, String timestamp, String parent, HashMap<String, String> map) {
        this.log = log;
        this.timestamp = timestamp;
        this.parent = parent;
        this.files = map;
    }

    public static void commit(String msg) {
        HashMap<String, String> map = Add.getMap();
        if (map.size() == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }

        HashMap<String, String> files = getCurrHead().files;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fileName = entry.getKey(), blobName = entry.getValue();
            if (blobName.equals("rm")) {
                // remove a file
                files.remove(fileName);
            } else {
                // add a new file or modify a existed file
                files.put(fileName, blobName);
                String path = Directory.stage + blobName;
                String npath = Directory.blob + blobName;
                Utils.writeContents(new File(npath), (Object) Utils.readContents(new File(path)));
            }
        }
        // clear staging area
        deleteStage();

        // create new commit
        Commit nhead = new Commit(msg, (new Date()).toString(), getCurrSHA(), files);
        String sha = Utils.sha1((Object) Utils.serialize(nhead));
        // store new commit
        Utils.writeObject(new File(Directory.commit + sha), nhead);
        // update the head of commit tree
        Utils.writeContents(new File(getCurrBranch()), sha);

    }

    public static void deleteStage() {
        File dir = new File(Directory.stage);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("DEBUG: deleting staging file failed");
                }
            }
        }
    }

    public static String getCurrSHA() {
        String branch = getCurrBranch();
        return Utils.readContentsAsString(new File(branch));
    }

    public static Commit getCurrHead() {
        return getCommit(getCurrSHA());
    }

    public static String getCurrBranch() {
        return Utils.readContentsAsString(new File(Directory.HEAD));
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public String getLog() {
        return log;
    }

    public String getParent() {
        return parent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static Commit getCommit(String sha) {
        File file = new File(Directory.commit + sha);
        return Utils.readObject(file, Commit.class);
    }
}
