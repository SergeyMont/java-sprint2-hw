package controller;

public class MyNode<T> {
    T task;
    MyNode<T> next;
    MyNode<T> prev;

    MyNode(MyNode<T> prev, T element, MyNode<T> next) {
        this.task = element;
        this.next = next;
        this.prev = prev;
    }
}
