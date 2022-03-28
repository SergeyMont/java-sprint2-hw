package controller;

class MyNode<T> {
    protected T task;
    MyNode<T> next;
    MyNode<T> prev;

    public MyNode(MyNode<T> prev, T element, MyNode<T> next) {
        this.task = element;
        this.next = next;
        this.prev = prev;
    }
}
