package controller;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, MyNode<Task>> history = new HashMap<Integer, MyNode<Task>>();
    private MyLinkedList<Task> list = new MyLinkedList<>();

    @Override
    public void add(Task task) {
        MyNode<Task> newNode = history.get(task.getId());
        if (newNode != null) {
            list.removeNode(newNode);
        }
        MyNode<Task> freshNode = list.linkLast(task);
        history.put(task.getId(), freshNode);
    }

    @Override
    public void remove(int id) {
        if (history.get(id) != null) {
            MyNode<Task> newNode = history.get(id);
            list.removeNode(newNode);
        }
    }

    @Override
    public List<Task> getHistory() {
        return list.getTasks();
    }

    @Override
    public void removeAll() {
        list.clear();
    }


    private class MyLinkedList<T> {
        int size = 0;
        MyNode<T> first;
        MyNode<T> last;

        MyNode<T> linkLast(T e) {
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

            if (first.equals(x)) {
                first = next;
            } else {
                prev.next = next;
                x.prev = null;
            }

            if (last.equals(x)) {
                last = prev;
            } else {
                next.prev = prev;
                x.next = null;
            }

            x.task = null;
            size--;
            return element;
        }

        public List<T> getTasks() {
            ArrayList<T> result = new ArrayList<>(size);
            for (MyNode<T> x = first; x != null; x = x.next)
                result.add(x.task);
            return result;
        }

        public void clear(){
            for(MyNode<T> x=first; x!=null;){
                MyNode<T> next=x.next;
                x.task=null;
                x.next=null;
                x.prev=null;
                x=next;
            }
            first=null;
            last=null;
            size=0;
        }

        public int size() {
            return size;
        }
    }
}
