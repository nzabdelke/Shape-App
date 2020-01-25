public class MyList {

    private int size;
    private Node head;

    // Default constructor
    public MyList() {
    }

    public MyList(MyList paramList) {
        this();
        for (int i = 0; i < paramList.size(); i++) {
            Object s = paramList.get(i);
            this.add(s);
        }
    }

    // Returns size of list
    public int size() {
        return size;
    }

    // Add to end
    public void add(Object data) {
        if (head == null) {
            head = new Node(data);
        }
        Node temp = new Node(data);
        Node nodePresent = head;

        if (nodePresent != null) {
            while (nodePresent.getNext() != null) {
                nodePresent = nodePresent.getNext();
            }
            nodePresent.setNext(temp);
        }
        size++; // increasing size since elem added to tail
    }

    // Get at index
    public Object get(int index) {
        Node nodePresent = null;
        if (head != null) {
            nodePresent = head.getNext();
            for (int i = 0; i < index; i++) {
                if (nodePresent.getNext() == null)
                    return null;
                nodePresent = nodePresent.getNext();
            }
            return nodePresent.getData();
        }
        return nodePresent;
    }

    public void remove(int index) {
        if (index < this.size()) {
            Node nodePresent = head;
            if (head != null) { // Ensure that there is a head
                for (int i = 0; i < index; i++) {
                    if (nodePresent.getNext() == null) {
                        break;
                    }
                    nodePresent = nodePresent.getNext();
                }
                nodePresent.setNext(nodePresent.getNext().getNext());
                size--; // Decreasing size
            }
        }
    }
}

class Node {

    // Subsequent node reference
    Node next;

    // Node data
    Object data;

    // Initialize
    public Node(Object dataValue) {
        next = null;
        data = dataValue;
    }

    // Setting the next reference
    public void setNext(Node next) {
        this.next = next;
    }

    // return the next reference
    public Node getNext() {
        return next;
    }

    // Constructor to go to specific node
    public Node(Object data, Node next) {
        this.next = next;
        this.data = data;
    }

    // Sets data
    public void setData(Object data) {
        this.data = data;
    }

    // Returns data
    public Object getData() {
        return data;
    }
}
