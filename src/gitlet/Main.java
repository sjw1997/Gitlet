package gitlet;

public class Main {
    public static void main(String[] args) {
        if (null == args || 0 == args.length) {
            System.out.println("Please enter a command.");
            return;
        }
        String cmd = args[0];
        switch (cmd) {
            case "init":
                // creates a new Gitlet version-control system in the current directory.
                Init.init();
                break;
            case "add":
                String[] fileNames = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    fileNames[i - 1] = args[i];
                }
                Add.add(fileNames);
                break;
            case "commit":
                if (args.length < 2) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                String msg = args[1].trim();
                if (msg.equals("")) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                Commit.commit(msg);
                break;
            case "rm":
                Remove.remove(args[1]);
                break;
            case "log":
                Log.log();
                break;
            case "global-log":
                Log.global_log();
                break;
            case "status":
                Status.status();
                break;
            case "checkout":
                if (args.length == 2) {
                    Checkout.checkoutBranch(args[1]);
                } else if (args[1].equals("--")) {
                    Checkout.checkoutFile(args[2]);
                } else if (args[2].equals("--")) {
                    Checkout.checkoutFileCommitID(args[1], args[3]);
                }
                break;
            case "branch":
                Branch.create(args[1]);
                break;
            case "rm-branch":
                Branch.remove(args[1]);
                break;
            case "reset":
                Reset.reset(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }
}
