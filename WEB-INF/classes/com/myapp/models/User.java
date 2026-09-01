package com.myapp.models;

public class User 
{
    private int id;
    private String username;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String dateOfBirth;
    private Profile profile;
    private boolean quizCompleted;
    private SimpleArrayList<Integer> friendIds;
    private SimpleLinkedList<FriendAction> actionHistory;
    private SimpleStack<FriendAction> undoStack;
    private SimpleLinkedList<Notification> notifications;
    private SimpleLinkedList<SearchHistoryEntry> searchHistory;
    private SimpleHashMap<Integer, Integer> lastReadMessageByChat;
    private SimpleHashMap<Integer, SimpleArrayList<Integer>> deletedMessagesByChat;
    private PersonalityProfile personalityProfile;




    public User(int id,String username, String password) 
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = new Profile();
        this.friendIds = new SimpleArrayList<>();
        this.actionHistory = new SimpleLinkedList<>();
        this.undoStack = new SimpleStack<>();
        this.notifications = new SimpleLinkedList<>();
        this.searchHistory = new SimpleLinkedList<>();
        this.lastReadMessageByChat = new SimpleHashMap<>();
        this.quizCompleted = false;
        this.deletedMessagesByChat = new SimpleHashMap<>();

    }

    public int getId() 
    {
        return id;
    }

    public String getUsername() 
    {
        return username;
    }

    public boolean checkPassword(String password) 
    {
        return this.password.equals(password);
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getAge() 
    {
        return age;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getDateOfBirth() 
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) 
    {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isQuizCompleted() 
    {
        return quizCompleted;
    }

    public Profile getProfile() 
    {
        return profile;
    }

    public String getPassword() 
    {
        return password;
    }


    public SimpleArrayList<Integer> getFriendIds() 
    {
        return friendIds;
    }

    public boolean isFriendWith(int otherUserId) 
    {
        for (int i = 0; i < friendIds.size(); i++) 
        {
            if (friendIds.get(i) == otherUserId) 
            {
                return true;
            }
        }

        return false;
    }

    public SimpleLinkedList<FriendAction> getActionHistory() 
    {
        return actionHistory;
    }

    public SimpleStack<FriendAction> getUndoStack() 
    {
        return undoStack;
    }

    public SimpleLinkedList<Notification> getNotifications() 
    {
        return notifications;
    }

    public SimpleLinkedList<SearchHistoryEntry> getSearchHistory()
    {
        return searchHistory;
    }

    public SimpleHashMap<Integer, Integer> getLastReadMessageByChat()
    {
        return lastReadMessageByChat;
    }

    public SimpleHashMap<Integer, SimpleArrayList<Integer>> getDeletedMessagesByChat()
    {
        return deletedMessagesByChat;
    }

    public PersonalityProfile getPersonalityProfile()
    {
        return personalityProfile;
    }

    public void setPersonalityProfile(PersonalityProfile profile)
    {
        this.personalityProfile = profile;
        this.quizCompleted = true;
    }

}
