package gitlet;

import java.io.File;
import java.util.HashMap;

public class Remove {
    public static void remove(String fileName) {
        HashMap<String, String> map = Utils.getMap();
        boolean changed = false;
        String sep = System.getProperty("file.separator");
        String dir = ".gitlet" + sep + "objects" + sep + "stage";
        if (map.containsKey(fileName)) {
            // If the file is staged, unstage it.
            boolean flag = (new File(dir + sep + map.get(fileName))).delete();
            if (!flag) {
                System.out.println("Remove: deleting staging file failed");
            }
            map.remove(fileName);
            changed = true;
        } else {
            HashMap<String, String> currCommit = Utils.getCurrCommit().getFiles();
            if (currCommit.containsKey(fileName)) {
                // If the file is tracked in the current commit,
                // staged it for removal and remove the file from directory
                map.put(fileName, "rm");
                changed = true;
                if (!(new File(fileName)).delete()) {
                    System.out.println("Remove: deleting file failed, the file has been deleted");
                }
            } else {
                System.out.println("No reason to remove the file.");
            }
        }
        if (changed) {
            // update file 'stage'
            Utils.writeObject(new File(dir + sep + "stage"), map);
        }
    }
}
