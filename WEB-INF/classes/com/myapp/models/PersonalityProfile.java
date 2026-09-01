package com.myapp.models;

public class PersonalityProfile
{
    private double introvertConfidence;
    private double ambivertConfidence;
    private double extrovertConfidence;

    public PersonalityProfile(double intro, double ambi, double extro)
    {
        this.introvertConfidence = intro;
        this.ambivertConfidence = ambi;
        this.extrovertConfidence = extro;
        normalize();
    }

    public double getIntrovertConfidence()
    {
        return introvertConfidence;
    }

    public double getAmbivertConfidence()
    {
        return ambivertConfidence;
    }

    public double getExtrovertConfidence()
    {
        return extrovertConfidence;
    }

    private void normalize()
    {
        double sum = introvertConfidence + ambivertConfidence + extrovertConfidence;

        introvertConfidence /= sum;
        ambivertConfidence /= sum;
        extrovertConfidence /= sum;
    }

    public void reinforceExtroversion(double strength)
    {
        extrovertConfidence += strength;
        ambivertConfidence += strength * 0.5;
        normalize();
    }

    public void reinforceIntroversion(double strength)
    {
        introvertConfidence += strength;
        ambivertConfidence += strength * 0.5;
        normalize();
    }

}

