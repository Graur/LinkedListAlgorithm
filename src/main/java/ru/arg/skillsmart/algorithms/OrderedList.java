package ru.arg.skillsmart.algorithms;
import java.util.*;

class Node<T>
{
    public T value;
    public Node<T> next, prev;

    public Node(T _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}

public class OrderedList<T>
{
    public Node<T> head, tail;
    private boolean _ascending;
    private int size;

    public OrderedList(boolean asc)
    {
        head = null;
        tail = null;
        size = 0;
        _ascending = asc;
    }

    public int compare(T v1, T v2)
    {
        // -1 если v1 < v2
        // 0 если v1 == v2
        // +1 если v1 > v2
        if (v1 instanceof Integer && v2 instanceof Integer) {
            int intValueOfV1 = (int) v1;
            int intValueOfV2 = (int) v2;
            if (intValueOfV1 < intValueOfV2) {
                return -1;
            } else if (intValueOfV1 > intValueOfV2) {
                return 1;
            }
            return 0;
        } else if (v1 instanceof String && v2 instanceof String) {
            String stringValueOfV1 = ((String) v1).trim();
            String stringValueOfV2 = ((String) v2).trim();

            int i = stringValueOfV1.compareTo(stringValueOfV2);
            if (i > 0) {
                return 1;
            } else if(i < 0) {
                return -1;
            }
            return 0;
        }
        return 0;
    }

    public void add(T value)
    {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            insertAfter(null, newNode);
        } else {
            Node<T> node = _ascending ? this.head : this.tail;
            while (node != null) {
                if (compare(node.value, value) <= 0) {
                    Node<T> comparingNode = _ascending ? node.next : node.prev;
                    if (comparingNode == null) {
                        Node<T> afterNode = _ascending ? tail : null;
                        insertAfter(afterNode, newNode);
                        break;
                    } else if (compare(comparingNode.value, value) > 0) {
                        Node<T> insertingAfterNode = _ascending ? node : comparingNode;
                        insertAfter(insertingAfterNode, newNode);
                        break;
                    } else if (compare(comparingNode.value, value) == 0) {
                        Node<T> insertingAfterNode = _ascending ? node.next : comparingNode;
                        insertAfter(insertingAfterNode, newNode);
                        break;
                    }
                } else {
                    Node<T> afterNode = _ascending ? null : tail;
                    insertAfter(afterNode, newNode);
                    break;
                }
                node = _ascending ? node.next : node.prev;
            }
        }
    }

    public void insertAfter(Node<T> nodeAfter, Node<T> nodeToInsert)
    {
        if (nodeAfter == null) {
            nodeToInsert.next = this.head;
            this.head = nodeToInsert;
            if (this.head.next != null) {
                this.head.next.prev = nodeToInsert;
            }
            if (this.tail == null) {
                this.tail = this.head;
            }
            size++;
        } else {
            Node<T> node = find(nodeAfter.value);
            if (node != null) {
                nodeToInsert.next = node.next;
                node.next = nodeToInsert;
                if (node == tail) {
                    this.tail = nodeToInsert;
                    this.tail.prev = node;
                } else {
                    node.next.next.prev = nodeToInsert;
                    nodeToInsert.prev = node;
                }
                size++;
            }
        }
    }

    public Node<T> find(T val)
    {
        Node<T> node = _ascending ? this.head : this.tail;
        while (node != null) {
            if (node.value == val)
                return node;
            node = _ascending ? node.next : node.prev;
        }
        return null;
    }

    public void delete(T val)
    {
        Node<T> node = find(val);
        if (node != null) {
            unlinkNode(node.prev, node);
            size--;
        }
    }

    private void unlinkNode(Node<T> prevNode, Node<T> removableNode) {
        Node<T> next = removableNode.next;

        if (prevNode == null) {
            this.head = next;
            if (this.head != null) {
                this.head.prev = null;
            } else {
                this.tail = this.head;
            }
        } else {
            prevNode.next = next;
            if (next == null) {
                this.tail = prevNode;
            } else {
                prevNode.next.prev = prevNode;
            }
        }
        removableNode = null;
    }

    public void clear(boolean asc)
    {
        _ascending = asc;
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int count()
    {
        return size; // здесь будет ваш код подсчёта количества элементов в списке
    }

    ArrayList<Node<T>> getAll() // выдать все элементы упорядоченного
    // списка в виде стандартного списка
    {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while(node != null)
        {
            r.add(node);
            node = node.next;
        }
        return r;
    }
}