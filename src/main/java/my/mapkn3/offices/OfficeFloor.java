package my.mapkn3.offices;

import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.interfaces.Floor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class OfficeFloor implements Floor {
    private Node head;

    private Node getNodeByIndex(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
            if (node == head) {
                throw new SpaceIndexOutOfBoundsException();
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

    public OfficeFloor(int countSpaces) {
        this(Stream.generate(Office::new)
                .limit(countSpaces)
                .toArray(Office[]::new));
    }

    public OfficeFloor(Office[] spaces) {
        this.head = new Node(spaces[0]);
        for (int i = 1; i < spaces.length; i++) {
            this.insertNode(i, new Node(spaces[i]));
        }
    }

    @Override
    public int getCountSpace() {
        int count = (head == null) ? 0 : 1;
        if (head != null) {
            for (Node i = head; i.getNext() != head; i = i.getNext()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public double getTotalSquare() {
        return Arrays.stream(getSpaces())
                .mapToDouble(my.mapkn3.interfaces.Space::getSquare)
                .sum();
    }

    @Override
    public int getTotalCountRooms() {
        return Arrays.stream(getSpaces())
                .mapToInt(my.mapkn3.interfaces.Space::getCountRooms)
                .sum();
    }

    @Override
    public my.mapkn3.interfaces.Space[] getSpaces() {
        List<my.mapkn3.interfaces.Space> spaceList = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            spaceList.add(i.getValue());
        }
        return spaceList.toArray(new my.mapkn3.interfaces.Space[getCountSpace()]);
    }

    @Override
    public my.mapkn3.interfaces.Space getSpace(int index) {
        return getNodeByIndex(index).getValue();
    }

    @Override
    public void setSpace(int index, my.mapkn3.interfaces.Space space) {
        this.getNodeByIndex(index).setValue(space);
    }

    @Override
    public void insertSpace(int index, my.mapkn3.interfaces.Space space) {
        this.insertNode(index, new Node(space));
    }

    @Override
    public void deleteSpace(int index) {
        this.deleteNode(index);
    }

    @Override
    public my.mapkn3.interfaces.Space getBestSpace() {
        return Arrays.stream(getSpaces())
                .max(Comparator.comparingDouble(my.mapkn3.interfaces.Space::getSquare))
                .orElse(null);
    }

    public static class Node {
        private my.mapkn3.interfaces.Space value;
        private Node next;

        public Node(my.mapkn3.interfaces.Space value) {
            this.value = value;
            this.next = this;
        }

        public my.mapkn3.interfaces.Space getValue() {
            return value;
        }

        public void setValue(my.mapkn3.interfaces.Space value) {
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
