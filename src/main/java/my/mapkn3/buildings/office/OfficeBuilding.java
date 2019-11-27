package my.mapkn3.buildings.office;

import my.mapkn3.buildings.iterators.BuildingIterator;
import my.mapkn3.exceptions.FloorIndexOutOfBoundsException;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.buildings.interfaces.Building;
import my.mapkn3.buildings.interfaces.Floor;
import my.mapkn3.buildings.interfaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OfficeBuilding implements Building {
    private Node head;

    private Node getNodeByIndex(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
            if (node == head) {
                throw  new FloorIndexOutOfBoundsException();
            }
        }
        return node;
    }

    private void insertNode(int index, Node node) {
        Node nextNode = getNodeByIndex(index);
        Node prevNode = nextNode.getPrev();
        node.setNext(prevNode.getNext());
        node.setPrev(nextNode.getPrev());
        nextNode.setPrev(node);
        prevNode.setNext(node);
    }

    private void deleteNode(int index) {
        Node nodeForDelete = getNodeByIndex(index);
        Node nextNode = nodeForDelete.getNext();
        Node prevNode = nodeForDelete.getPrev();
        prevNode.setNext(nodeForDelete.getNext());
        nextNode.setPrev(nodeForDelete.getPrev());
        nodeForDelete.setNext(null);
        nodeForDelete.setPrev(null);
    }

    public OfficeBuilding(int countFloors, int[] countFlatsOnFloorArray) {
        this(Arrays.stream(countFlatsOnFloorArray)
                .mapToObj(OfficeFloor::new)
                .limit(countFloors)
                .toArray(OfficeFloor[]::new));
    }

    public OfficeBuilding(Floor[] floors) {
        this.head = new Node(floors[0]);
        for (int i = 1; i < floors.length; i++) {
            insertNode(i, new Node(floors[i]));
        }
    }

    @Override
    public int getCountFloors() {
        int count = (head == null) ? 0 : 1;
        if (head != null) {
            for (Node i = head; i.getNext() != head; i = i.getNext()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getCountSpaces() {
        return getFloorList().stream()
                .mapToInt(Floor::getCountSpace)
                .sum();
    }

    @Override
    public double getTotalSquare() {
        return getFloorList().stream()
                .mapToDouble(Floor::getTotalSquare)
                .sum();
    }

    @Override
    public int getTotalCountRooms() {
        return getFloorList().stream()
                .mapToInt(Floor::getTotalCountRooms)
                .sum();
    }

    @Override
    public Floor[] getFloorArray() {
        return getFloorList().toArray(new Floor[getCountFloors()]);
    }

    @Override
    public Floor getFloor(int index) {
        return getNodeByIndex(index).getValue();
    }

    @Override
    public void setFloor(int index, Floor floor) {
        getNodeByIndex(index).setValue(floor);
    }

    @Override
    public Space getSpace(int index) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getCountSpace() < index; node = node.getNext()) {
            index -= node.getValue().getCountSpace();
        }
        return node.getValue().getSpace(index);
    }

    @Override
    public void setSpace(int index, Space space) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getCountSpace() <= index; node = node.getNext()) {
            index -= node.getValue().getCountSpace();
        }
        node.getValue().setSpace(index, space);
    }

    @Override
    public void insertSpace(int index, Space space) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getCountSpace() < index; node = node.getNext()) {
            index -= node.getValue().getCountSpace();
        }
        node.getValue().insertSpace(index, space);
    }

    @Override
    public void deleteSpace(int index) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getCountSpace() < index; node = node.getNext()) {
            index -= node.getValue().getCountSpace();
        }
        node.getValue().deleteSpace(index);
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(getFloorArray())
                .map(Floor::getBestSpace)
                .max(Comparator.comparingDouble(Space::getSquare))
                .orElse(null);
    }

    @Override
    public Space[] getSortedSpaceDesc() {
        return Arrays.stream(getFloorArray())
                .map(Floor::getSpaces)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Space::getSquare))
                .toArray(Space[]::new);
    }

    private List<Floor> getFloorList() {
        List<Floor> floors = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors;
    }

    private void checkOfficeIndex(int index) {
        if (index < 0 || index >= getCountSpaces()) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<Floor> iterator() {
        return new BuildingIterator(this);
    }

    public static class Node {
        private Floor value;
        private Node prev;
        private Node next;

        public Node(Floor value) {
            this.value = value;
            this.prev = this;
            this.next = this;
        }

        public Floor getValue() {
            return value;
        }

        public void setValue(Floor value) {
            this.value = value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}