package com.myapp.models;

public class PersonalityQuizService 
{
    private SimpleArrayList<PersonalityQuestion> questions;

    public PersonalityQuizService() 
    {
        questions = new SimpleArrayList<>();
        loadQuestions();
    }

     private void loadQuestions() 
    {

        String[] options1 = {"Find a quiet corner and observe.","Stick with one or two people you know.","Jump into the loudest group immediately.","Check my phone and decide later."};
        
        questions.add(new PersonalityQuestion(1, "You enter a party. What is your first instinct?", options1));
        
        
        String[] options2 = {"Reading, gaming, or solo hobbies at home.","One chill hangout plus alone time.","Back-to-back plans with different friends.","I don't plan, I just scroll and see."};
        
        questions.add(new PersonalityQuestion(2, "Your ideal weekend looks like:", options2));
        
        
        String[] options3 = {"Let it ring and text them instead.","Check who it is, then maybe pick up.","Answer instantly, ready to talk.","Hope it stops ringing soon."};
        
        questions.add(new PersonalityQuestion(3, "When your phone rings unexpectedly, you:", options3));
        
        
        String[] options4 = {"Drained. I'd rather work alone.","Okay, if the group is small.","Energised. I love bouncing ideas.","Depends on my mood completely."};
       
        questions.add(new PersonalityQuestion(4, "Group projects make you feel:", options4));
        
        
        String[] options5 = {"Recharge alone or with headphones on.","Talk to one close friend.","Float between different groups.","Just sit and scroll on my phone."};
       
        questions.add(new PersonalityQuestion(5, "At school or work, during breaks you:", options5));
        
        
        String[] options6 = {"Struggle to start conversations.","Can talk if they start first.","Start conversations easily.","Prefer to just be introduced."};
        
        questions.add(new PersonalityQuestion(6, "When meeting new people, you:", options6));
        
        
        String[] options7 = {"Runs out very fast.","Goes up and down depending on people.","Feels almost unlimited.","Is random, even I can't predict it."};
       
        questions.add(new PersonalityQuestion(7, "Your social battery:", options7));
        
        
        String[] options8 = {"You're alone in your own space.","You're with one trusted person.","You're in a lively, social environment.","You're online more than offline."};
       
        questions.add(new PersonalityQuestion(8, "You feel most like yourself when:", options8));
        
        
        String[] options9 = {"Secretly feel relieved.","Feel neutral, it's fine.","Feel disappointed, you were excited.","Immediately look for other people to meet."};
       

        questions.add(new PersonalityQuestion(9, "When someone cancels plans last minute, you:", options9));
        
        
        String[] options10 = {"Quiet and thoughtful.","Balanced and adaptable.","Loud and outgoing.","Depends who you ask."};
        
        questions.add(new PersonalityQuestion(10, "People usually describe you as:", options10));
    }

    public SimpleArrayList<PersonalityQuestion> getQuestions() 
    {
        return questions;
    }


    public PersonalityProfile evaluateProfile(SimpleHashMap<Integer, Integer> answers)
    {
        int intro = 0, ambi = 0, extro = 0;

        SimpleArrayList<Integer> vals = answers.values();

        for (int i = 0; i < vals.size(); i++)
        {
            int ans = vals.get(i);

            if (ans == 1) 
            {
                intro += 2;
            }
            else if (ans == 2) 
            {
                ambi += 2;
            }
            else if (ans == 3) 
            {
                extro += 2;
            }
            else 
            { 
                intro++; 
                ambi++; 
                extro++; 
            }
        }

        return new PersonalityProfile(intro, ambi, extro);
    }

}