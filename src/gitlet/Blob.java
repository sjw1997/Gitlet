package gitlet;

import java.io.Serializable;

public class Blob implements Serializable {
    private String fileName;
    private String content;

    public Blob(String _fileName, String _content) {
        fileName = _fileName;
        content = _content;
    }
}
