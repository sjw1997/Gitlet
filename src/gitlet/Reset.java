package gitlet;

import java.io.File;

public class Reset {
    static void reset(String commitID) {
        Checkout.checkoutCommitID(commitID);
        // update the head pointer of current branch
        for (String s : Utils.plainFilenamesIn(Directory.commit)) {
            if (s.contains(commitID)) {
                commitID = s;
            }
        }
        File file  = new File(Utils.getCurrBranch());
        Utils.writeContents(file, commitID);
    }
}
