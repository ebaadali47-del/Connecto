package com.myapp.models;

public class SearchHistoryEntry
{
    private String query;
    private long timestamp;

    public SearchHistoryEntry(String query, long timestamp)
    {
        this.query = query;
        this.timestamp = timestamp;
    }

    public String getQuery()
    {
        return query;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
