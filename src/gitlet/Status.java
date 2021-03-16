package gitlet;

import java.io.File;
import java.util.*;

public class Status {
    public static void status() {
        disBranch();
        disStage();
    }

    private static void disStage() {
        HashMap<String, String> map = Utils.getMap();
        HashMap<String, String> currCommit = Utils.getCurrCommit().getFiles();
        List<String> allFiles = Utils.plainFilenamesIn(".");
        List<String> modified = new LinkedList<>();
        List<String> removals = new LinkedList<>();
        List<String> staged = new LinkedList<>();
        List<String> untracked = getUntrackedFile(allFiles, currCommit, map);

        for (String fileName : allFiles) {
            // tracked in the current commit
            if (currCommit.containsKey(fileName)) {
                File file = new File(fileName);
                Blob blob = new Blob(fileName, Utils.readContents(file));
                String sha = Utils.sha1((Object) Utils.serialize(blob));
                // tracked in the current commit, changing in the working directory
                // but not staged
                if (!sha.equals(currCommit.get(fileName)) && !map.containsKey(fileName)) {
                    modified.add(fileName + " (modified)");
                }
            }
        }

        for (Map.Entry<String, String> entry : currCommit.entrySet()) {
            String fileName = entry.getKey();
            if (!allFiles.contains(fileName) && (!map.containsKey(fileName) || !map.get(fileName).equals("rm"))) {
                // Not staged for removal, but tracked in the current commit
                // and deleted from the working directory
                modified.add(fileName + " (deleted)");
            }
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fileName = entry.getKey(), blobName = entry.getValue();
            if (blobName.equals("rm")) {
                // staged for removal
                removals.add(fileName);
            } else {
                // staged for addition
                staged.add(fileName);
                if (!allFiles.contains(fileName)) {
                    // staged for addition, but deleted in the working directory
                   if (!modified.contains(fileName + " (deleted)")) {
                       modified.add(fileName + " (deleted)");
                   }
                } else {
                    File file = new File(fileName);
                    Blob blob = new Blob(fileName, Utils.readContents(file));
                    String sha = Utils.sha1((Object) Utils.serialize(blob));
                    if (!sha.equals(blobName)) {
                        // staged for addition, but with different contents in the working directory
                        modified.add(fileName + " (modified)");
                    }
                }
            }
        }

        Collections.sort(removals);
        Collections.sort(staged);
        Collections.sort(untracked);
        Collections.sort(modified);

        System.out.println("=== Staged Files ===");
        for (String fileName : staged) {
            System.out.println(fileName);
        }

        System.out.println("\n=== Removed Files ===");
        for (String fileName : removals) {
            System.out.println(fileName);
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (String fileName : modified) {
            System.out.println(fileName);
        }

        System.out.println("\n=== Untracked Files ===");
        for (String fileName : untracked) {
            System.out.println(fileName);
        }
        System.out.println();
    }

    private static void disBranch() {
        String branchFile = Utils.getCurrBranch();
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

    public static List<String> getUntrackedFile(final List<String> allFiles,
                                                 final HashMap<String, String> currCommit,
                                                 final HashMap<String, String> map) {
        List<String> untracked = new LinkedList<>();
        for (String fileName : allFiles) {
            if (!currCommit.containsKey(fileName)) {
                // neither staged for addition nor tracked
                if (!map.containsKey(fileName)) {
                    untracked.add(fileName);
                }
            } else if ("rm".equals(map.get(fileName))) {
                // or staged for removal, but then re-created without Gitlet's knowledge
                untracked.add(fileName);
            }
        }
        return untracked;
    }
}
