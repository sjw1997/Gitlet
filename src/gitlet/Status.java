package gitlet;

import java.util.*;

public class Status {
    public static void status() {
        disBranch();
        disStage();
    }

    private static void disStage() {
        HashMap<String, String> map = Add.getMap();
        HashMap<String, String> files = Commit.getCurrHead().getFiles();
        List<String> deleted = new LinkedList<>();
        List<String> modified = new LinkedList<>();
        List<String> removals = new LinkedList<>();
        List<String> stagings = new LinkedList<>();
        List<String> allFiles = Utils.plainFilenamesIn(".");

        for (String fileName : allFiles) {
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fileName = entry.getKey(), blobName = entry.getValue();
            if (blobName.equals("rm")) {
                removals.add(fileName);
            } else {
                stagings.add(fileName);
            }
        }

        Collections.sort(removals);
        Collections.sort(stagings);
        System.out.println("=== Staging Files ===");
        for (String fileName : stagings) {
            System.out.println(fileName);
        }
        System.out.println("\n=== Removed Files ===");
        for (String fileName : removals) {
            System.out.println(fileName);
        }
        System.out.println();
    }

    private static void disBranch() {
        String branchFile = Commit.getCurrBranch();
        StringBuilder currBranch = new StringBuilder();
        for (int i = branchFile.length() - 1; !Directory.sep.equals("" + branchFile.charAt(i)); i--) {
            currBranch.insert(0, branchFile.charAt(i));
        }
        System.out.println("=== Branches ===");
        List<String> branches = Utils.plainFilenamesIn(Directory.branch);
        Collections.sort(branches);
        for (String branch : branches) {
            if (branch.equals(currBranch.toString())) {
                System.out.print("*");
            }
            System.out.println(branch);
        }
        System.out.println();
    }
}
