package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout {
    static void checkoutFile(String fileName) {
        checkoutFileCommitID(Utils.getCurrCommitID(), fileName);
    }

    static void checkoutFileCommitID(String commitID, String fileName) {
        Commit currCommit = Utils.getCommit(commitID);
        if (currCommit == null) {
            System.out.println("No commit with that id exists.");
            return;
        }

        HashMap<String, String> files = currCommit.getFiles();
        if (!files.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        HashMap<String, String> map = Utils.getMap();
        if (map.containsKey(fileName)) {
            map.remove(fileName);
            Utils.updateStage(map);
        }
        String blobID = files.get(fileName);
        Blob blob = Utils.getBlob(blobID);
        File file = new File(fileName);
        Utils.writeContents(file, (Object) blob.getContent());
    }

    static void checkoutBranch(String branchName) {
        String currBranch = Utils.extractCurrBranch();
        if (currBranch.equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }

        String fullPath = Directory.branch + branchName;
        File file = new File(fullPath);
        if (!file.exists()) {
            System.out.println("No such branch exists.");
            return;
        }

        String commitID = Utils.readContentsAsString(file);
        checkoutCommitID(commitID);

        // update HEAD
        File HEAD = new File(Directory.HEAD);
        Utils.writeContents(HEAD, fullPath);
    }

    static void checkoutCommitID(String commitID) {
        List<String> allFiles = Utils.plainFilenamesIn(".");
        List<String> untracked = Status.getUntrackedFile(allFiles, Utils.getCurrCommit().getFiles(), Utils.getMap());
        HashMap<String, String> branchCommit = Utils.getCommit(commitID).getFiles();

        for (String fileName : untracked) {
            if (branchCommit.containsKey(fileName)) {
                System.out.println("There is an untracked file in the way; delete it, " +
                        "or add and commit it first.");
                return;
            }
        }
        for (Map.Entry<String, String> entry : branchCommit.entrySet()) {
            String fileName = entry.getKey(), blobID = entry.getValue();
            Utils.updateFile(fileName, Utils.getBlob(blobID));
        }
        for (String fileName : allFiles) {
            if (!branchCommit.containsKey(fileName)) {
                Utils.restrictedDelete(fileName);
            }
        }

        // clear staging area
        Utils.clearStage();
    }
}
