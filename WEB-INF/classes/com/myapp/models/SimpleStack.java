package com.myapp.models;

public class SimpleStack<T> 
{
    private SimpleArrayList<T> list;
    
    public SimpleStack() 
    {
        list = new SimpleArrayList<>();
    }
    
    public void push(T value) 
    {
        list.add(value);
    }
    
    public T pop() 
    {
        if (list.isEmpty()) 
            return null;
        return list.removeAt(list.size() - 1); 
    }
    
    public T peek() 
    {
        if (list.isEmpty()) 
            return null;
        
        return list.get(list.size() - 1);
    }
    
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }
    
    public int size() 
    {
        return list.size();
    }

    public void clear() 
    {
        while (!isEmpty()) 
        {
            pop();
        }
    }
}