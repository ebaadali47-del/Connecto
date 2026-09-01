package com.myapp.models;

public class SimpleArrayList<T> 
{
    private Object[] data;
    private int size;

    public SimpleArrayList() 
    {
        data = new Object[10];
        size = 0;
    }

    public void add(T value) 
    {
        ensureCapacity();

        data[size++] = value;
    }

    public T get(int index) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException();
        }

        T value = (T) data[index];

        return value;
    }

    public void set(int index, T value) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException();
        }

        data[index] = value;
    }

    public T removeAt(int index) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException();
        }

        T removed = (T) data[index];

        for (int i = index; i < size - 1; i++) 
        {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;
        
        return removed;
    }

    public int size() 
    {
        return size;
    }

    public boolean isEmpty() 
    {
        return size == 0;
    }


    public int indexOf(T value) 
    {
        for (int i = 0; i < size; i++) 
        {
            T current = (T) data[i];

            if (current == null && value == null) 
                return i;

            if (current != null && current.equals(value)) 
                return i;
        }

        return -1;
    }

    public boolean contains(T value) 
    {
        for (int i = 0; i < size; i++) 
        {
            T current = (T) data[i];
            if (current == null && value == null) 
                return true;

            if (current != null && current.equals(value)) 
                return true;
        }

        return false;
    }

    private void ensureCapacity() 
    {
        if (size < data.length) 
            return;

        int newCap = data.length * 2;


        Object[] newData = new Object[newCap];

        for (int i = 0; i < size; i++) 
        {
            newData[i] = data[i];
        }

        data = newData;
    }
}
