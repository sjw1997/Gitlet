package gitlet;

import java.io.File;

public class Init {
    public static void init() {
        String path = ".gitlet";
        File file = new File(path);
        // try to create .gitlet directory
        boolean isSuccess = file.mkdir();
        if (isSuccess) {
            Commit initial = new Commit();
            String initialHash = Utils.sha1(Utils.serialize(initial));
            String sep = System.getProperty("file.separator");
            File initialFile = new File(path + sep + initialHash);
            File HEAD = new File(path + sep + "HEAD");

            Utils.writeObject(initialFile, initial);
            Utils.writeObject(HEAD, initialHash);
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }
}
