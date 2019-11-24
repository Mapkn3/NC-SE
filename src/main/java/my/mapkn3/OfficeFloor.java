package my.mapkn3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class OfficeFloor {
    private Node head;

    private Node getNodeByIndex(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
            if (node == head) {
                node = null;
                break;
            }
        }
        return node;
    }

    private void insertNode(int index, Node node) {
        Node prevNode = getNodeByIndex(index - 1);
        node.setNext(prevNode.getNext());
        prevNode.setNext(node);
    }

    private void deleteNode(int index) {
        Node prevNode = getNodeByIndex(index - 1);
        Node nodeForDelete = prevNode.getNext();
        prevNode.setNext(nodeForDelete.getNext());
        nodeForDelete.setNext(null);
    }

    public OfficeFloor(int countOffices) {
        this(Stream.generate(Office::new)
                .limit(countOffices)
                .toArray(Office[]::new));
    }

    public OfficeFloor(Office[] offices) {
        this.head = new Node(offices[0]);
        for (int i = 1; i < offices.length; i++) {
            this.insertNode(i, new Node(offices[i]));
        }
    }

    public int getCountOffices() {
        int count = (head == null) ? 0 : 1;
        if (head != null) {
            for (Node i = head; i.getNext() != head; i = i.getNext()) {
                count++;
            }
        }
        return count;
    }

    public double getTotalSquare() {
        double totalSquare = head.getValue().getSquare();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            totalSquare += i.getNext().getValue().getSquare();
        }
        return totalSquare;
    }

    public int getTotalCountRooms() {
        int totalCountRooms = head.getValue().getCountRooms();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            totalCountRooms += i.getNext().getValue().getCountRooms();
        }
        return totalCountRooms;
    }

    public Office[] getOfficeArray() {
        List<Office> officeList = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            officeList.add(i.getValue());
        }
        return officeList.toArray(new Office[getCountOffices()]);
    }

    public Office getOfficeByIndex(int index) {
        return getNodeByIndex(index).getValue();
    }

    public void setOffice(int index, Office office) {
        this.getNodeByIndex(index).setValue(office);
    }

    public void insertOffice(int index, Office office) {
        this.insertNode(index, new Node(office));
    }

    public void deleteOffice(int index) {
        this.deleteNode(index);
    }

    public Office getBestSpace() {
        return Arrays.stream(getOfficeArray())
                .max(Comparator.comparingDouble(Office::getSquare))
                .orElse(null);
    }

    public static class Node {
        private Office value;
        private Node next;

        public Node(Office value) {
            this.value = value;
            this.next = this;
        }

        public Office getValue() {
            return value;
        }

        public void setValue(Office value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
