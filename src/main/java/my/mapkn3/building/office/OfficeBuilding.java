package my.mapkn3.building.office;

import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.building.iterator.BuildingIterator;
import my.mapkn3.exception.FloorIndexOutOfBoundsException;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OfficeBuilding implements Building {
    private Node head;

    public OfficeBuilding(int floorsCount, int[] spacesCounts) {
        this(Arrays.stream(spacesCounts)
                .mapToObj(OfficeFloor::new)
                .limit(floorsCount)
                .toArray(OfficeFloor[]::new));
    }

    public OfficeBuilding(Floor[] floors) {
        this.head = new Node(floors[0]);
        for (int i = 1; i < floors.length; i++) {
            insertNode(i, new Node(floors[i]));
        }
    }

    private Node getNodeByIndex(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
            if (node == head) {
                throw new FloorIndexOutOfBoundsException();
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

    @Override
    public int getFloorsCount() {
        int count = (head == null) ? 0 : 1;
        if (head != null) {
            for (Node i = head; i.getNext() != head; i = i.getNext()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getSpacesCount() {
        return getFloorList().stream()
                .mapToInt(Floor::getSpaceCount)
                .sum();
    }

    @Override
    public double getTotalArea() {
        return getFloorList().stream()
                .mapToDouble(Floor::getTotalArea)
                .sum();
    }

    @Override
    public int getTotalRoomsCount() {
        return getFloorList().stream()
                .mapToInt(Floor::getTotalRoomsCount)
                .sum();
    }

    @Override
    public Floor[] getFloorArray() {
        return getFloorList().toArray(new Floor[getFloorsCount()]);
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
        for (node = head; node.getValue().getSpaceCount() < index; node = node.getNext()) {
            index -= node.getValue().getSpaceCount();
        }
        return node.getValue().getSpace(index);
    }

    @Override
    public void setSpace(int index, Space space) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getSpaceCount() <= index; node = node.getNext()) {
            index -= node.getValue().getSpaceCount();
        }
        node.getValue().setSpace(index, space);
    }

    @Override
    public void insertSpace(int index, Space space) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getSpaceCount() < index; node = node.getNext()) {
            index -= node.getValue().getSpaceCount();
        }
        node.getValue().insertSpace(index, space);
    }

    @Override
    public void deleteSpace(int index) {
        checkOfficeIndex(index);
        Node node;
        for (node = head; node.getValue().getSpaceCount() < index; node = node.getNext()) {
            index -= node.getValue().getSpaceCount();
        }
        node.getValue().deleteSpace(index);
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(getFloorArray())
                .map(Floor::getBestSpace)
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
    }

    @Override
    public Space[] getSortedSpaceArrayDesc() {
        return Arrays.stream(getFloorArray())
                .map(Floor::getSpaceArray)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Space::getArea))
                .toArray(Space[]::new);
    }

    private List<Floor> getFloorList() {
        List<Floor> floors = new ArrayList<>();
        floors.add(head.getValue());
        for (Node i = head.getNext(); i != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors;
    }

    private void checkOfficeIndex(int index) {
        if (index < 0 || index >= getSpacesCount()) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<Floor> iterator() {
        return new BuildingIterator(this);
    }

    @Override
    public String toString() {
        return String.format("OfficeBuilding (%d, %s)",
                getFloorsCount(),
                Arrays.stream(getFloorArray())
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeBuilding)) return false;

        OfficeBuilding officeBuilding = (OfficeBuilding) o;

        if (getFloorsCount() != officeBuilding.getFloorsCount()) return false;
        for (int i = 0; i < getFloorsCount(); i++) {
            if (!getFloor(i).equals(officeBuilding.getFloor(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.stream(getFloorArray())
                .mapToInt(Object::hashCode)
                .reduce(getFloorsCount(), (accum, next) -> accum ^ next);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
