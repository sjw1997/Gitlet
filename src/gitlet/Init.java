package gitlet;

import java.io.File;

public class Init {
    public static void init() {
        String path = ".gitlet";
        // try to create .gitlet directory
        boolean isSuccess = mkdir(path);
        if (isSuccess) {
            String sep = System.getProperty("file.separator");
            // create a list of directory
            mkdir(path + sep + "refs");
            mkdir(path + sep + "refs" + sep + "heads");
            mkdir(path + sep + "objects");
            mkdir(path + sep + "objects" + sep + "stage");
            mkdir(path + sep + "objects" + sep + "blobs");
            mkdir(path + sep + "objects" + sep + "commits");

            // create initial commit and store it
            Commit commit = new Commit();
            String commitHash = Utils.sha1(Utils.serialize(commit));
            File commitFile = new File(Directory.commit + commitHash);
            Utils.writeObject(commitFile, commit);

            // update the head of master branch
            File masterFile = new File(Directory.branch + "master");
            Utils.writeContents(masterFile, commitHash);

            // head is at the master branch
            File HEAD = new File(Directory.HEAD);
            String branch = path + sep + "refs" + sep + "heads" + sep + "master";
            Utils.writeContents(HEAD, branch);
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }

    private static boolean mkdir(String path) {
        File dir  = new File(path);
        return dir.mkdir();
    }
}
