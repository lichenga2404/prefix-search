package enron;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Iterator;

public class FileWalker {

    private final Iterator<File> it;

    public FileWalker(String directory) {
        it = FileUtils.iterateFiles(new File(directory), null, true);
    }

    public File next() {
        return this.it.next();
    }

    public boolean hasNext() {
        return this.it.hasNext();
    }

}
