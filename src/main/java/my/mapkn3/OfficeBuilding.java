package my.mapkn3;

import my.mapkn3.buildings.DwellingFloor;
import my.mapkn3.buildings.Flat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OfficeBuilding {
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

    public OfficeBuilding(OfficeFloor[] floors) {
        this.head = new Node(floors[0]);
        for (int i = 1; i < floors.length; i++) {
            insertNode(i, new Node(floors[i]));
        }
    }

    public int getCountFloors() {
        int count = (head == null) ? 0 : 1;
        if (head != null) {
            for (Node i = head; i.getNext() != head; i = i.getNext()) {
                count++;
            }
        }
        return count;
    }

    public int getCountOffices() {
        ArrayList<OfficeFloor> floors = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors.stream().mapToInt(OfficeFloor::getCountOffices).sum();
    }

    public double getTotalSquare() {
        ArrayList<OfficeFloor> floors = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors.stream().mapToDouble(OfficeFloor::getTotalSquare).sum();
    }

    public int getTotalCountRooms() {
        ArrayList<OfficeFloor> floors = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors.stream().mapToInt(OfficeFloor::getTotalCountRooms).sum();
    }

    public OfficeFloor[] getOfficeArray() {
        ArrayList<OfficeFloor> floors = new ArrayList<>();
        for (Node i = head; i.getNext() != head; i = i.getNext()) {
            floors.add(i.getValue());
        }
        return floors.toArray(new OfficeFloor[getCountFloors()]);
    }

    public OfficeFloor getFloorByIndex(int index) {
        return getNodeByIndex(index).getValue();
    }

    public void setFloor(int index, OfficeFloor floor) {
        getNodeByIndex(index).setValue(floor);
    }

    public Office getOffice(int index) {
        Office office = null;
        if (index >= 0 && index < getCountOffices()) {
            Node node;
            for (node = head; node.getValue().getCountOffices() < index; node = node.getNext()) {
                index -= node.getValue().getCountOffices();
            }
            office = node.getValue().getOfficeByIndex(index);
        }
        return office;
    }

    public void setOffice(int index, Office office) {
        if (index >= 0 && index < getCountOffices()) {
            Node node;
            for (node = head; node.getValue().getCountOffices() < index; node = node.getNext()) {
                index -= node.getValue().getCountOffices();
            }
            node.getValue().setOffice(index, office);
        }
    }

    public void insertOffice(int index, Office office) {
        if (index >= 0 && index < getCountOffices()) {
            Node node;
            for (node = head; node.getValue().getCountOffices() < index; node = node.getNext()) {
                index -= node.getValue().getCountOffices();
            }
            node.getValue().insertOffice(index, office);
        }
    }

    public void deleteOffice(int index) {
        if (index >= 0 && index < getCountOffices()) {
            Node node;
            for (node = head; node.getValue().getCountOffices() < index; node = node.getNext()) {
                index -= node.getValue().getCountOffices();
            }
            node.getValue().deleteOffice(index);
        }
    }

    public Office getBestSpace() {
        return Arrays.stream(getOfficeArray())
                .map(OfficeFloor::getBestSpace)
                .max(Comparator.comparingDouble(Office::getSquare))
                .orElse(null);
    }

    public Office[] getSortedOfficeDesc() {
        return Arrays.stream(getOfficeArray())
                .map(OfficeFloor::getOfficeArray)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Office::getSquare))
                .toArray(Office[]::new);
    }

    public static class Node {
        private OfficeFloor value;
        private Node prev;
        private Node next;

        public Node(OfficeFloor value) {
            this.value = value;
            this.prev = this;
            this.next = this;
        }

        public OfficeFloor getValue() {
            return value;
        }

        public void setValue(OfficeFloor value) {
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
