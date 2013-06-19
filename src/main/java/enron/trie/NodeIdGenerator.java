package enron.trie;

public class NodeIdGenerator {

    //singleton
    protected NodeIdGenerator() {

    }

    private static NodeIdGenerator instance = new NodeIdGenerator();
    private long counter = 0;   //basic id gen

    public static NodeIdGenerator getInstance() {
        return instance;
    }

    public synchronized long getId() {
        counter ++;
        return counter;
    }


}
