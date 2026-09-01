package com.myapp.models;

public class SimpleHashMap<K, V> 
{

    private static class Entry<K, V> 
    {
        K key;
        V value;

        Entry(K key, V value) 
        {
            this.key = key;
            this.value = value;
        }
    }
    
    private SimpleArrayList<Entry<K, V>>[] buckets;
    private int capacity;
    private int size;

    public SimpleHashMap()
    {
        capacity = 16;   
        size = 0;

        buckets = new SimpleArrayList[capacity]; 

        for (int i = 0; i < capacity; i++)
        {
            buckets[i] = new SimpleArrayList<>();
        }
    }

    private int hash(K key)
    {
        if (key == null)
            return 0;

        if (key instanceof Integer)
        {
            int k = (Integer) key;
            return Math.abs(k) % capacity;
        }

        String s = (String) key;
        int hash = 0;
        int p = 31;

        for (int i = 0; i < s.length(); i++)
        {
            hash = (hash * p + s.charAt(i)) % capacity;
        }

        return hash;
    }

    
    public void put(K key, V value)
    {
        int index = hash(key);
        SimpleArrayList<Entry<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++)
        {
            Entry<K, V> e = bucket.get(i);

            if ((key == null && e.key == null) || (key != null && key.equals(e.key)))
            {
                e.value = value;
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;
    }

    public V remove(K key)
    {
        int index = hash(key);
        SimpleArrayList<Entry<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++)
        {
            Entry<K, V> e = bucket.get(i);

            if ((key == null && e.key == null) || (key != null && key.equals(e.key)))
            {
                V value = e.value;
                bucket.removeAt(i);
                size--;
                return value;
            }
        }

        return null;
    }
    
    public V get(K key)
    {
        int index = hash(key);
        SimpleArrayList<Entry<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++)
        {
            Entry<K, V> e = bucket.get(i);

            if ((key == null && e.key == null) || key != null && key.equals(e.key))
            {
                return e.value;
            }
        }

        return null;
    }


    public boolean containsKey(K key)
    {
        return get(key) != null;
    }

    public SimpleArrayList<V> values()
    {
        SimpleArrayList<V> list = new SimpleArrayList<>();

        for (int i = 0; i < capacity; i++)
        {
            SimpleArrayList<Entry<K, V>> bucket = buckets[i];

            for (int j = 0; j < bucket.size(); j++)
            {
                list.add(bucket.get(j).value);
            }
        }

        return list;
    }

}
