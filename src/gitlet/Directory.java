package gitlet;

public class Directory {
    public final static String sep = System.getProperty("file.separator");
    public final static String blob = ".gitlet" + sep + "objects" + sep + "blobs" + sep;
    public final static String commit = ".gitlet" + sep + "objects" + sep + "commits" + sep;
    public final static String stage = ".gitlet" + sep + "objects" + sep + "stage" + sep;
    public final static String branch = ".gitlet" + sep + "refs" + sep + "heads" + sep;
    public final static String HEAD = ".gitlet" + sep + "HEAD" + sep;
}
