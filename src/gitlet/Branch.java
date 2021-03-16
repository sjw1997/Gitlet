package gitlet;

import java.io.File;
import java.util.List;

public class Branch {
    static void create(String newBranch) {
        List<String> branches = Utils.plainFilenamesIn(Directory.branch);
        if (branches.contains(newBranch)) {
            System.out.println("A branch with that name already exists.");
            return;
        }

        String commitID = Utils.getCurrCommitID();
        File file = new File(Directory.branch + newBranch);
        Utils.writeContents(file, commitID);
    }

    static void remove(String branch) {
        List<String> braches = Utils.plainFilenamesIn(Directory.branch);
        if (!braches.contains(branch)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }

        String currBranch = Utils.extractCurrBranch();
        if (currBranch.equals(branch)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }

        File file = new File(Directory.branch + branch);
        if (!file.delete()) {
            System.out.println("Deleting branch failed");
        }
    }
}
