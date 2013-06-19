package enron.trie;

import enron.db.NodeDocIndex;
import enron.db.TrieIndex;

import java.sql.Connection;
import java.sql.SQLException;

// will write Trie to DB
public class NodeVisitor {

    NodeDocIndex nodeDocIndexer = null;
    TrieIndex trieIndexer = null;

    public NodeVisitor(Connection connection) {
         nodeDocIndexer = new NodeDocIndex(connection);
         trieIndexer = new TrieIndex(connection);
    }

    public void visit(Node node) {
        System.out.println("VISIT node: " + node.id());

        for (long l: node.docIds) nodeDocIndexer.add(node.id(), l);
    }

    public void visit(Node parent, Node child) {
        System.out.println("VISIT PARENT: " + parent.id() + " ; CHILD: " + child.id());
        trieIndexer.add(child.id(), parent.id(), child.letter);
    }

    public void listAll() {
        try {
            nodeDocIndexer.listAll();
            trieIndexer.listAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
