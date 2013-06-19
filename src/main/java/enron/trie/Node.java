package enron.trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

    public char letter = ' ';
    private long id;
    public List<Node> children = new ArrayList<Node>();
    public Set<Long> docIds = new HashSet<Long>();

    public Node(char letter) {
        this.letter = letter;
        this.id = NodeIdGenerator.getInstance().getId();
    }

    public String toString() {
        return " " + letter;
    }

    public long id() {
        return this.id;
    }

}
