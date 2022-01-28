package controller;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, MyNode> history = new HashMap<>();
    private MyLinkedList<Task> list = new MyLinkedList<>();

    @Override
    public void add(Task task) {
        MyNode newNode = history.get(task.getId());
        if (newNode != null) {
            list.removeNode(newNode);
        }
        MyNode freshNode = list.linkLast(task);
        history.put(task.getId(), freshNode);
    }

    @Override
    public void remove(int id) {
        MyNode newNode = history.get(id);
        list.removeNode(newNode);
    }

    @Override
    public List<Task> getHistory() {
        return list.getTasks();
    }

    private class MyLinkedList<T> {
        int size = 0;
        MyNode first;
        MyNode last;

        public MyLinkedList() {

        }

        MyNode linkLast(T e) {
            final MyNode<T> l = last;
            final MyNode<T> newNode = new MyNode<>(l, e, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;
            return newNode;
        }

        T removeNode(MyNode<T> x) {

            final T element = x.task;
            final MyNode<T> next = x.next;
            final MyNode<T> prev = x.prev;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                x.prev = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                x.next = null;
            }

            x.task = null;
            size--;
            return element;
        }

        public ArrayList<T> getTasks() {
            ArrayList<T> result = new ArrayList<>(size);
            int i = 0;
            for (MyNode<T> x = first; x != null; x = x.next)
                result.add(x.task);
            return result;
        }

        public int size() {
            return size;
        }
    }
}
