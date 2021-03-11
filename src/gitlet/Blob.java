package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    private String fileName;
    private byte[] content;
    private static final long serialVersionUID = 6529685038267757690L;

    public Blob(String _fileName, byte[] _content) {
        fileName = _fileName;
        content = _content;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public static Blob getBlob(String path) {
        File file = new File(path);
        return Utils.readObject(file, Blob.class);
    }
}
