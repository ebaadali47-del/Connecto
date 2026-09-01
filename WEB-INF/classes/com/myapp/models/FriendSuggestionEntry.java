package com.myapp.models;

public class FriendSuggestionEntry 
{
    private PublicProfileSummary summary;
    private double similarityPercent;

    public FriendSuggestionEntry(PublicProfileSummary summary, double similarityPercent) 
    {
        this.summary = summary;
        this.similarityPercent = similarityPercent;
    }

    public PublicProfileSummary getSummary() 
    {
        return summary;
    }

    public double getSimilarityPercent() 
    {
        return similarityPercent;
    }
}
