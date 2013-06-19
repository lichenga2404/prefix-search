package enron.trie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Serializer {

    private final Node root;
    private FileChannel writer = null;
    long b = 0;

    public Serializer(Trie trie, String file) throws IOException {
        this.root = trie.root();
        writer = (new RandomAccessFile(file, "rw")).getChannel();
        writer.truncate(0);
    }

    public long serialize() throws IOException {
        return serialize(root);
    }

    private long serialize(Node current) throws IOException {

        ArrayList<Long> offsets = new ArrayList<Long>();
        for (Node children: current.children) {
            serialize(children);
            offsets.add(b);
            System.out.println(b);
        }

        String s = current.id() + " (";
        for (long d: current.docIds) s += d + " ";
        s += ") [";

        for (long o: offsets) s += o + " ";
        s += "]\n";

        System.out.println(s);

        ByteBuffer buf = ByteBuffer.allocate(s.length());
        wrote(s.length());

        buf.put(s.getBytes());
        buf.flip();

        writer.write(buf);

        return 0;
    }

    private void wrote(int len) {
        b += len;

    }

    public void close() throws IOException {
        writer.close();
    }
}
