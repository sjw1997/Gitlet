package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Add {
    public static void add(String[] fileNames) {
        // judge whether file exists
        List<String> allFiles = Utils.plainFilenamesIn(".");
        for (String fileName : fileNames) {
            if (!allFiles.contains(fileName)) {
                System.out.println("File does not exist.");
                return;
            }
        }

        HashMap<String, String> map = Utils.getMap();
        HashMap<String, String> currCommit = (Utils.getCurrCommit()).getFiles();
        for (String fileName : fileNames) {
            File file  = new File(fileName);
            Blob blob = new Blob(fileName, Utils.readContents(file));
            String blobName = Utils.sha1((Object) Utils.serialize(blob));

            if (currCommit != null && currCommit.getOrDefault(fileName, "").equals(blobName)) {
                // If the file is the same as the current version of the file and
                // remove it from the staging area if it is already there
                if (map != null && map.containsKey(fileName)) {
                    boolean flag = (new File(Directory.stage + map.get(fileName))).delete();
                    if (!flag) {
                        System.out.println("DEBUG: deleting staing file failed");
                    }
                    map.remove(fileName);
                }
                continue;
            }

            if (map != null && map.containsKey(fileName)) {
                // Staging an already-staged file overwrites the previous
                // entry in the staging area with the new contents
                if (!map.get(fileName).equals(blobName)) {
                    boolean flag = (new File(Directory.stage + map.get(fileName))).delete();
                    if (!flag) {
                        System.out.println("DEBUG: deleting staging file failed.");
                    }
                }
            }
            Utils.writeObject(new File(Directory.stage + blobName), blob);
            map.put(fileName, blobName);
        }
        Utils.updateStage(map);
    }
}
