package gitlet;

public class Log {
    public static void log() {
        String sha = Commit.getCurrSHA();

        while (sha != null) {
            Commit head = Commit.getCommit(sha);
            print(head, sha);
            sha = head.getParent();
        }
    }

    public static void global_log() {
        for (String fileName : Utils.plainFilenamesIn(Directory.commit)) {
            Commit commit = Commit.getCommit(fileName);
            print(commit, fileName);
        }
    }

    private static void print(Commit commit, String commitID) {
        System.out.println("===");
        System.out.println("commit " + commitID);
        System.out.println("Date:" + commit.getTimestamp());
        System.out.println(commit.getLog() + "\n");
    }
}
