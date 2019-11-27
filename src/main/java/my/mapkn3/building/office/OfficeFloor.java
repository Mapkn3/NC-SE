package my.mapkn3.building.office;

import my.mapkn3.building.iterator.FloorIterator;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
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

    public OfficeFloor(Space[] spaces) {
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
    public double getTotalArea() {
        return Arrays.stream(getSpaces())
                .mapToDouble(Space::getArea)
                .sum();
    }

    @Override
    public int getTotalRoomsCount() {
        return Arrays.stream(getSpaces())
                .mapToInt(Space::getRoomsCount)
                .sum();
    }

    @Override
    public Space[] getSpaces() {
        List<Space> spaceList = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            spaceList.add(i.getValue());
        }
        return spaceList.toArray(new Space[getCountSpace()]);
    }

    @Override
    public Space getSpace(int index) {
        return getNodeByIndex(index).getValue();
    }

    @Override
    public void setSpace(int index, Space space) {
        this.getNodeByIndex(index).setValue(space);
    }

    @Override
    public void insertSpace(int index, Space space) {
        this.insertNode(index, new Node(space));
    }

    @Override
    public void deleteSpace(int index) {
        this.deleteNode(index);
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(getSpaces())
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
    }

    @Override
    public Iterator<Space> iterator() {
        return new FloorIterator(this);
    }

    public static class Node {
        private Space value;
        private Node next;

        public Node(Space value) {
            this.value = value;
            this.next = this;
        }

        public Space getValue() {
            return value;
        }

        public void setValue(Space value) {
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
