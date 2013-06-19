package enron.trie;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Trie {

    private Node root = new Node('*');

    public void addWord(String word, long docID) {
        char[] letters = word.toCharArray();

        Node current = root;

        for (char letter : letters) {
            boolean found = false;
            for (Node child: current.children)
                if (child.letter == letter) {
                    current = child;
                    found = true;
                    break;
                }

            //did not find letter
            if ( !found ) {
                Node child = new Node(letter);
                current.children.add(child);
                current = child;
            }
        }

        current.docIds.add(docID);
    }

    public Iterable<Long> find(String word) {
        char[] letters = word.toCharArray();

        Node current = root;

        for (char letter : letters) {
            boolean found = false;
            for (Node child: current.children)
                if (child.letter == letter) {
                    current = child;
                    found = true;
                    break;
                }

            if ( !found )
                return null;
        }

        return current.docIds;
    }

    public void visit(NodeVisitor visitor) {

        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while ( !queue.isEmpty() ) {

            Node current = queue.remove();
            visitor.visit(current);

            for (Node child: current.children) {
                visitor.visit(current, child);
                queue.add(child);
            }
        }


    }


    public Node root() {
        return root;
    }
}
