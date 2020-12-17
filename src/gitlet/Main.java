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

            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }
}
