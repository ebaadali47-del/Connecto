package com.myapp.models;

public class SimpleLinkedList<T> 
{
    public static class Node<T> 
    {
        public T value;
        public Node<T> next;

        public Node(T value) 
        { 
            this.value = value; 
        }
    }

    public Node<T> head;
    public int size;

    public void addLast(T value) 
    {
        Node<T> node = new Node<>(value);

        if (head == null) 
        {
            head = node;
        } 
        else 
        {
            Node<T> current = head;
            
            while (current.next != null) 
            {
                current = current.next;
            }
            current.next = node;
        }

        size++;
    }

    public void addFirst(T value)
    {
        Node<T> node = new Node<>(value);

        if(head==null)
        {
            head = node;
        }
        else 
        {
            node.next = head;
            head = node;
        }

        size++;
    }

    public boolean removeAt(int index) 
    {
        if (index < 0 || index >= size) 
            return false;

        if (index == 0) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        
        for (int i = 0; i < index - 1; i++) 
        {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }

        return false;
    }



    public boolean isEmpty() 
    {
        return size == 0;
    }

    public T get(int index) 
    {
        if (index < 0 || index >= size) 
            throw new IndexOutOfBoundsException();

        Node<T> curr = head;

        for (int i = 0; i < index; i++) 
        {
            curr = curr.next;
        }

        return curr.value;
    }

    public void clear() 
    {
        head = null;
        size = 0;
    }

   
}