package com.myapp.models;

public class Manager 
{
    private SimpleHashMap<Integer, User> usersById;
    private SimpleHashMap<String, Integer> usernameToId;
    private PersonalityQuizService quizService;
    private int nextFriendReqId;
    private int nextUserId;
    private int nextNotificationId;
    private SimpleHashMap<Integer, ChatThread> chatsById;
    private SimpleHashMap<String, Integer> chatKeyToChatId;
    private SimpleHashMap<Integer, FriendRequest> friendRequestsById;
    private int nextChatId;
    private int nextMessageId;


    public Manager() 
    {
        usersById = new SimpleHashMap<>();
        usernameToId = new SimpleHashMap<>();
        quizService = new PersonalityQuizService();
        nextFriendReqId = 1;
        nextUserId = 1;
        chatsById = new SimpleHashMap<>();
        chatKeyToChatId = new SimpleHashMap<>();
        friendRequestsById = new SimpleHashMap<>();
        nextChatId = 1;
        nextMessageId = 1;
        nextNotificationId = 1;

    }

    public User signUp(String username, String password) 
    {
        User user = new User(nextUserId++,username, password);
        usersById.put(user.getId(), user);
        usernameToId.put(username, user.getId());

        UserDAO.insert(user);

        return user;
    }

    public boolean isUsernameTaken(String username)
    {
        return usernameToId.containsKey(username);
    }

    public boolean isValidPassword(String password)
    {
        if (password.length() < 6)
            return false;

        boolean hasDigit = false;
        boolean hasLetter = false;

        for (int i = 0; i < password.length(); i++)
        {
            char c = password.charAt(i);

            if (c >= '0' && c <= '9')
                hasDigit = true;

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                hasLetter = true;
        }

        return hasDigit && hasLetter;
    }



    public User signIn(String username, String password) 
    {
       Integer id = usernameToId.get(username);

        if (id == null)
            return null;

        User user = usersById.get(id);

        if (user != null && user.checkPassword(password))
            return user;

        return null;
    }

    public void CompleteProfile(int userId, Profile newProfileData) 
    {
        User user = getUserById(userId);

        if (user == null) 
            return;
        
        Profile profile = user.getProfile();

        profile.setAbout(newProfileData.getAbout());
        profile.setEducation(newProfileData.getEducation());
        profile.setProfilePicturePath(newProfileData.getProfilePicturePath());

        SimpleArrayList<String> skills = profile.getSkills();

        SimpleArrayList<String> newSkills = newProfileData.getSkills();

        for (int i = 0; i < newSkills.size(); i++) 
        {
            String skill = newSkills.get(i);
            skills.add(skill);
            ProfileDAO.addSkill(userId, skill);

        }

        SimpleArrayList<String> hobbies = profile.getHobbies();

        SimpleArrayList<String> newHobbies = newProfileData.getHobbies();

        for (int i = 0; i < newHobbies.size(); i++) 
        {
            String hobby = newHobbies.get(i);
            hobbies.add(hobby);
            ProfileDAO.addHobby(userId, hobby);

        }

        ProfileDAO.upsert(user);
        UserDAO.updateBasicInfo(user);

    }

    public void editName(int userId, String newName)
    {
        User user = getUserById(userId);

        user.setName(newName);

        UserDAO.updateBasicInfo(user);

    }

    public void editAge(int userId, int newAge)
    {
        User user = getUserById(userId);

        user.setAge(newAge);

        UserDAO.updateBasicInfo(user);

    }


    public void editAbout(int userId, String about)
    {
        User user = getUserById(userId);

        user.getProfile().setAbout(about);

        ProfileDAO.update(user);

    }

    public void editProfilePicturePath(int userId, String newPath)
    {
        User user = getUserById(userId);

        user.getProfile().setProfilePicturePath(newPath);

        ProfileDAO.update(user);
    }

    public void editEducation(int userId, String education)
    {
        User user = getUserById(userId);

        user.getProfile().setEducation(education);

        ProfileDAO.update(user);

    }

    private boolean containsIgnoreCase(SimpleArrayList<String> list, String value)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equalsIgnoreCase(value))
                return true;
        }

        return false;
    }

    public boolean addSkill(int userId, String skill)
    {
        User user = getUserById(userId);

        SimpleArrayList<String> skills = user.getProfile().getSkills();

        if (containsIgnoreCase(skills, skill))
            return false;

        skills.add(skill);

        ProfileDAO.addSkill(userId, skill);

        return true;
    }

    public boolean removeSkill(int userId, String skill)
    {
        User user = getUserById(userId);

        SimpleArrayList<String> skills = user.getProfile().getSkills();

        for (int i = 0; i < skills.size(); i++)
        {
            if (skills.get(i).equalsIgnoreCase(skill))
            {
                skills.removeAt(i);

                ProfileDAO.removeSkill(userId, skill);

                return true;
            }
        }

        return false;
    }

    public boolean addHobby(int userId, String hobby)
    {
        User user = getUserById(userId);

        SimpleArrayList<String> hobbies = user.getProfile().getHobbies();

        if (containsIgnoreCase(hobbies, hobby))
            return false;

        hobbies.add(hobby);

        ProfileDAO.addHobby(userId, hobby);

        return true;
    }

    public boolean removeHobby(int userId, String hobby)
    {
        User user = getUserById(userId);

        SimpleArrayList<String> hobbies = user.getProfile().getHobbies();

        for (int i = 0; i < hobbies.size(); i++)
        {
            if (hobbies.get(i).equalsIgnoreCase(hobby))
            {
                hobbies.removeAt(i);

                ProfileDAO.removeHobby(userId, hobby);

                return true;
            }
        }
        
        return false;
    }


    public Profile getFullProfile(int viewerId, int targetUserId) 
    {
        User viewer = getUserById(viewerId);
        User target = getUserById(targetUserId);

        if (target == null)
            return null;

        if (viewerId == targetUserId || viewer.isFriendWith(targetUserId)) 
        {
            return target.getProfile();
        }

        return null;
    }

    public PublicProfileSummary getPublicProfileSummary(int targetUserId) 
    {
        User user = getUserById(targetUserId);

        if (user == null)
            return null;

        int friendsCount = user.getFriendIds().size();
        String about = user.getProfile().getAbout();
        String picture = user.getProfile().getProfilePicturePath();

        return new PublicProfileSummary(user.getId(), user.getName(), about, picture, friendsCount);
    }

    public FriendRequest sendFriendRequest(int fromUserId, int toUserId) 
    {
        if (fromUserId == toUserId) 
            return null;

        User from = getUserById(fromUserId);
        User to = getUserById(toUserId);

        if (to == null)
            return null;

        if (from.isFriendWith(toUserId)) 
            return null;

        SimpleArrayList<FriendRequest> allRequests = friendRequestsById.values();

        for (int i = 0; i < allRequests.size(); i++) 
        {
            FriendRequest fr = allRequests.get(i);
            if (fr.getFromUserId() == fromUserId && fr.getToUserId() == toUserId && fr.getStatus() == FriendRequestStatus.PENDING) 
            {
                return null;
            }
        }


        FriendRequest request = new FriendRequest(nextFriendReqId++, fromUserId, toUserId);
        
        friendRequestsById.put(request.getId(), request);

        FriendRequestDAO.insert(request);
    
        FriendAction action = new FriendAction(ActionType.SEND_REQUEST,fromUserId, toUserId, request.getId(),System.currentTimeMillis());

        recordFriendAction(action);

        from.getPersonalityProfile().reinforceExtroversion(0.03);
        UserDAO.updatePersonality(from);

        Notification notification = new Notification(nextNotificationId++,"You received a friend request from " + from.getName());

        to.getNotifications().addLast(notification);
        NotificationDAO.insert(toUserId, notification);

        return request;
    }

    public void acceptFriendRequest(int requestId, int actingUserId) 
    {
        FriendRequest request = findFriendRequestById(requestId);

        if (request == null) 
            return;

        if (request.getToUserId() != actingUserId) 
            return;

        if (request.getStatus() != FriendRequestStatus.PENDING) 
            return;

        request.setStatus(FriendRequestStatus.ACCEPTED);

        User from = getUserById(request.getFromUserId());

        User to = getUserById(request.getToUserId());

        if (from == null || to == null) 
            return;

        from.getFriendIds().add(to.getId());

        to.getFriendIds().add(from.getId());

        FriendRequestDAO.updateStatus(requestId, FriendRequestStatus.ACCEPTED);

        FriendDAO.add(from.getId(), to.getId());
        FriendDAO.add(to.getId(), from.getId());

        FriendAction action = new FriendAction(ActionType.ACCEPT_REQUEST,actingUserId,request.getFromUserId(),request.getId(),System.currentTimeMillis());

        recordFriendAction(action);


        to.getPersonalityProfile().reinforceExtroversion(0.01);

        to.getPersonalityProfile().reinforceIntroversion(0.01);

        UserDAO.updatePersonality(to);

        Notification notification = new Notification(nextNotificationId++,to.getUsername() + " accepted your friend request");

        from.getNotifications().addLast(notification);
        NotificationDAO.insert(from.getId(), notification);

        from.getUndoStack().clear();

    }

    public void rejectFriendRequest(int requestId, int actingUserId) 
    {
        FriendRequest request = findFriendRequestById(requestId);

        if (request == null) 
            return;

        if (request.getToUserId() != actingUserId) 
            return;

        if (request.getStatus() != FriendRequestStatus.PENDING) 
            return;

        request.setStatus(FriendRequestStatus.REJECTED);

        FriendRequestDAO.updateStatus(requestId, FriendRequestStatus.REJECTED);

        User actor = getUserById(actingUserId);

        actor.getPersonalityProfile().reinforceIntroversion(0.03);
        UserDAO.updatePersonality(actor);

        FriendAction action = new FriendAction(ActionType.REJECT_REQUEST, actingUserId,request.getFromUserId(), request.getId(),System.currentTimeMillis());

        recordFriendAction(action);

        User sender = getUserById(request.getFromUserId());

        sender.getUndoStack().clear();
    
    }

    private FriendRequest findFriendRequestById(int requestId) 
    {
        return friendRequestsById.get(requestId);
    }

    public SimpleArrayList<FriendRequest> getPendingRequestsForUser(int userId) 
    {
        SimpleArrayList<FriendRequest> result = new SimpleArrayList<>();

        SimpleArrayList<FriendRequest> allRequests = friendRequestsById.values();

        for (int i = 0; i < allRequests.size(); i++) 
        {
            FriendRequest fr = allRequests.get(i);
            if (fr.getToUserId() == userId && fr.getStatus() == FriendRequestStatus.PENDING) 
            {
                result.add(fr);
            }
        }

        return result;

    }

    public SimpleArrayList<User> getFriendsOfUser(int userId) 
    {
        SimpleArrayList<User> result = new SimpleArrayList<>();
        User user = getUserById(userId);

        SimpleArrayList<Integer> ids = user.getFriendIds();

        for (int i = 0; i < ids.size(); i++) 
        {
            User u = getUserById(ids.get(i));

            if (u != null) 
                result.add(u);
        }

        return result;
    }

    public SimpleArrayList<FriendSuggestionEntry> getFriendSuggestions(int userId) 
    {
        SimpleArrayList<FriendSuggestionEntry> result = new SimpleArrayList<>();

        User user = getUserById(userId);

        SimpleArrayList<User> allUsers = usersById.values();

        for (int i = 0; i < allUsers.size(); i++) 
        {
            User other = allUsers.get(i);

            if (other.getId() == userId) 
                continue;

            if (user.isFriendWith(other.getId())) 
                continue;

            double similarity = calculateSimilarity(user, other);

            if (similarity > 40) 
            {
                PublicProfileSummary summary = getPublicProfileSummary(other.getId());
                result.add(new FriendSuggestionEntry(summary, similarity));
            }
        }

        for (int i = 0; i < result.size(); i++) 
        {
            for (int j = 0; j < result.size() - 1 - i; j++) 
            {
                if (result.get(j).getSimilarityPercent() < result.get(j + 1).getSimilarityPercent()) 
                {
                    FriendSuggestionEntry temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }

        return result;
    }

    private double calculateSimilarity(User a, User b)
    {
        double totalScore = 0.0;

        double personalityRaw = compatibility(a.getPersonalityProfile(),b.getPersonalityProfile());

        double personalityScore = (personalityRaw / 100.0) * 50;

        totalScore += personalityScore;

        SimpleArrayList<String> aHobbies = a.getProfile().getHobbies();
        SimpleArrayList<String> bHobbies = b.getProfile().getHobbies();

        int commonHobbies = countCommonStrings(aHobbies, bHobbies);
        int maxHobbies = Math.max(aHobbies.size(), bHobbies.size());

        double hobbyScore = 0;

        if (maxHobbies > 0)
        {
            hobbyScore = ((double) commonHobbies / maxHobbies) * 25;
        }

        totalScore += hobbyScore;

        SimpleArrayList<String> aSkills = a.getProfile().getSkills();
        SimpleArrayList<String> bSkills = b.getProfile().getSkills();

        int commonSkills = countCommonStrings(aSkills, bSkills);
        int maxSkills = Math.max(aSkills.size(), bSkills.size());

        double skillScore = 0;

        if (maxSkills > 0)
        {
            skillScore = ((double) commonSkills / maxSkills) * 25;
        }

        totalScore += skillScore;


        return totalScore;
    }


    private static double compatibility(PersonalityProfile a, PersonalityProfile b)
    {
        double score = 0;

        score += a.getExtrovertConfidence() * b.getAmbivertConfidence() * 1.2;
        score += a.getAmbivertConfidence() * b.getExtrovertConfidence() * 1.1;

        score += a.getIntrovertConfidence() * b.getAmbivertConfidence() * 1.2;
        score += a.getAmbivertConfidence() * b.getIntrovertConfidence() * 1.1;

        score += a.getAmbivertConfidence() * b.getAmbivertConfidence() * 1.3;

        score += a.getExtrovertConfidence() * b.getExtrovertConfidence() * 0.7;
        score += a.getIntrovertConfidence() * b.getIntrovertConfidence() * 0.7;


        return score * 100;
    }


    private int countCommonStrings(SimpleArrayList<String> list1, SimpleArrayList<String> list2) 
    {
        int count = 0;
        for (int i = 0; i < list1.size(); i++) 
        {
            String s = list1.get(i);

            for (int j = 0; j < list2.size(); j++) 
            {
                if (s != null && s.equalsIgnoreCase(list2.get(j))) 
                {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    public SimpleLinkedList<FriendAction> getFriendActionHistory(int userId) 
    {
        User user = getUserById(userId);

        return user.getActionHistory();
    }

    public boolean undoLastAction(int userId) 
    {
        User user = getUserById(userId);

        SimpleStack<FriendAction> undoStack = user.getUndoStack();

        if (undoStack.isEmpty())
            return false;

        FriendAction last = undoStack.pop();

        if (last == null) 
            return false;

      
        FriendRequest fr = findFriendRequestById(last.getFriendRequestId());

        if (fr.getStatus() == FriendRequestStatus.PENDING) 
        {
            friendRequestsById.remove(fr.getId());
            FriendRequestDAO.delete(last.getFriendRequestId());
            removeFriendRequestNotification(fr.getToUserId(), fr.getFromUserId());
        }

        
        return true;
    }

    private void removeFriendRequestNotification(int toUserId, int fromUserId)
    {
        User to = getUserById(toUserId);
        User from = getUserById(fromUserId);

        if (to == null || from == null)
            return;

        SimpleLinkedList<Notification> notifications = to.getNotifications();

        String expected = "You received a friend request from " + from.getUsername();

        SimpleLinkedList.Node<Notification> prev = null;

        SimpleLinkedList.Node<Notification> curr = notifications.head;

        while (curr != null)
        {
            Notification n = curr.value;

            if (!n.isRead() && n.getMessage().equals(expected))
            {
                if (prev == null)
                    notifications.head = curr.next;
                else
                    prev.next = curr.next;

                notifications.size--;

                NotificationDAO.delete(n.getId()); 
                return;
            }

            prev = curr;
            curr = curr.next;
        }
    }


    public void unfriend(int userId, int friendId)
    {
        User a = getUserById(userId);
        User b = getUserById(friendId);

        if (b == null)
            return;

        if (!a.isFriendWith(friendId)) 
            return;

        removeFriendship(a, b);

        FriendDAO.remove(userId, friendId);
        FriendDAO.remove(friendId, userId);

    }


    private void removeFriendship(User a, User b) 
    {
        SimpleArrayList<Integer> aFriends = a.getFriendIds();
        SimpleArrayList<Integer> bFriends = b.getFriendIds();

        int index = aFriends.indexOf(b.getId());

        if (index != -1) 
        {
            aFriends.removeAt(index);
        }

        index = bFriends.indexOf(a.getId());

        if (index != -1) 
        {
            bFriends.removeAt(index);
        }
    }

    private void recordFriendAction(FriendAction action) 
    {
        User user = getUserById(action.getActorUserId());

        user.getActionHistory().addFirst(action);

        ActionHistoryDAO.insert(action.getActorUserId(), action);

        if (action.getType() == ActionType.SEND_REQUEST)
        {
            user.getUndoStack().clear(); 
            user.getUndoStack().push(action);
        }

    }

    public SimpleArrayList<PersonalityQuestion> getQuizQuestions() 
    {
        return quizService.getQuestions();
    }


    public void submitQuizAnswers(int userId, SimpleHashMap<Integer, Integer> answers) 
    {
        User user = getUserById(userId);

        PersonalityProfile profile = quizService.evaluateProfile(answers);

        user.setPersonalityProfile(profile);

        UserDAO.updatePersonality(user);

    }

    public User getUserById(int id) 
    {
        return usersById.get(id);
    }

    public SimpleArrayList<User> getAllUsers() 
    {
        SimpleArrayList<User> list = new SimpleArrayList<>();

        SimpleArrayList<User> allUsers = usersById.values();

        for (int i = 0; i < allUsers.size(); i++) 
        {
            User user = allUsers.get(i);
            list.add(user);
        }

        return list;
    }

    private boolean matchesKeyword(User user, String keyword)
    {
        if (keyword == null || keyword.isEmpty())
            return false;

        keyword = keyword.toLowerCase();

        
        if (user.getName() != null && user.getName().toLowerCase().contains(keyword))
        {
            return true;
        }

        
        SimpleArrayList<String> hobbies = user.getProfile().getHobbies();

        for (int i = 0; i < hobbies.size(); i++)
        {
            if (hobbies.get(i).toLowerCase().contains(keyword))
                return true;
        }

        
        SimpleArrayList<String> skills = user.getProfile().getSkills();

        for (int i = 0; i < skills.size(); i++)
        {
            if (skills.get(i).toLowerCase().contains(keyword))
                return true;
        }

        String education = user.getProfile().getEducation();

        if (education != null && education.toLowerCase().contains(keyword))
        {
            return true;
        }

        return false;
    }

    public SimpleArrayList<PublicProfileSummary> searchUsers(String keyword, int currentUserId)
    {

        SimpleArrayList<PublicProfileSummary> result = new SimpleArrayList<>();

        if (keyword == null || keyword.isEmpty())
            return result;

        User currentUser = getUserById(currentUserId);

        SearchHistoryEntry entry = new SearchHistoryEntry(keyword, System.currentTimeMillis());

        currentUser.getSearchHistory().addFirst(entry);

        SearchHistoryDAO.insert(currentUserId, entry);

        SimpleArrayList<User> allUsers = usersById.values();

        for (int i = 0; i < allUsers.size(); i++)
        {
            User user = allUsers.get(i);

            
            if (user.getId() == currentUserId)
                continue;

            if (matchesKeyword(user, keyword))
            {
                PublicProfileSummary summary = getPublicProfileSummary(user.getId());

                result.add(summary);
            }
        }

        return result;
    }

    public SimpleLinkedList<SearchHistoryEntry> getSearchHistory(int userId)
    {
        User user = getUserById(userId);

        return user.getSearchHistory();
    }

    public boolean removeSearchHistoryEntry(int userId, int index) 
    {
        User user = getUserById(userId);
        SimpleLinkedList<SearchHistoryEntry> history = user.getSearchHistory();

        boolean removed = history.removeAt(index);

        if (removed) 
        {
            SearchHistoryDAO.deleteAtIndex(userId, index);
        }

        return removed;
    }


    public void clearSearchHistory(int userId)
    {
        User user = getUserById(userId);

        user.getSearchHistory().clear();

        SearchHistoryDAO.clear(userId);

    }

    public void clearActionHistory(int userId) 
    {
        User user = getUserById(userId);

        if (user == null)
            return;

        user.getActionHistory().clear();
        user.getUndoStack().clear();

        ActionHistoryDAO.clear(userId); 
    }


    public boolean removeActionHistory(int userId, int index) 
    {
        User user = getUserById(userId);

        SimpleLinkedList<FriendAction> history = user.getActionHistory();

        boolean removed = history.removeAt(index); 
        
        if (removed) 
        {
            ActionHistoryDAO.deleteAtIndex(userId, index);
        }

        return removed;
    }


    private String getChatKey(int a, int b)
    {
        if (a < b)
            return a + "_" + b;

        return b + "_" + a;
    }

    public ChatThread getOrCreateChatThread(int userAId, int userBId)
    {
        if (userAId == userBId)
            return null;

        User a = getUserById(userAId);
        User b = getUserById(userBId);

        if (b == null)
            return null;

        if (!a.isFriendWith(userBId))
            return null;

        String key = getChatKey(userAId, userBId);

        Integer chatId = chatKeyToChatId.get(key);

        if (chatId != null)
        {
            return chatsById.get(chatId);
        }

        ChatThread thread = new ChatThread(nextChatId++, userAId, userBId);

        ChatDAO.create(thread, key);

        chatsById.put(thread.getId(), thread);

        chatKeyToChatId.put(key, thread.getId());

        return thread;
    }

    public void sendMessage(int fromUserId, int toUserId, String content)
    {
        if (content == null || content.isEmpty())
            return;

        ChatThread thread = getOrCreateChatThread(fromUserId, toUserId);
        if (thread == null)
            return;

        ChatMessage message = new ChatMessage(nextMessageId++,fromUserId,toUserId,content,System.currentTimeMillis());

        ChatDAO.saveMessage(message, thread.getId());
        thread.getMessages().addLast(message);

        User sender = getUserById(fromUserId);
        User receiver = getUserById(toUserId);


        sender.getPersonalityProfile().reinforceExtroversion(0.015);

        
    }


    public SimpleLinkedList<ChatMessage> getChatHistory(int userId, int friendId)
    {
        String key = getChatKey(userId, friendId);
        Integer chatId = chatKeyToChatId.get(key);

        if (chatId == null)
            return null;

        ChatThread thread = chatsById.get(chatId);

        if (thread == null || !thread.involvesUser(userId))
            return null;

        User user = getUserById(userId);

        SimpleArrayList<Integer> deleted = user.getDeletedMessagesByChat().get(chatId);

        SimpleLinkedList<ChatMessage> visible = new SimpleLinkedList<>();

        for (int i = 0; i < thread.getMessages().size; i++)
        {
            ChatMessage msg = thread.getMessages().get(i);

            boolean isDeleted = false;

            if (deleted != null)
            {
                for (int j = 0; j < deleted.size(); j++)
                {
                    if (deleted.get(j) == msg.getId())
                    {
                        isDeleted = true;
                        break;
                    }
                }
            }

            if (!isDeleted)
                visible.addLast(msg);
        }

        return visible;
    }


    public void clearChatHistory(int userId, int friendId)
    {
        String key = getChatKey(userId, friendId);
        Integer chatId = chatKeyToChatId.get(key);

        if (chatId == null)
            return;

        ChatThread thread = chatsById.get(chatId);

        if (thread == null || !thread.involvesUser(userId))
            return;

        User user = getUserById(userId);

        SimpleArrayList<Integer> deleted = user.getDeletedMessagesByChat().get(chatId);

        if (deleted == null)
        {
            deleted = new SimpleArrayList<>();
            user.getDeletedMessagesByChat().put(chatId, deleted);
        }

        for (int i = 0; i < thread.getMessages().size; i++)
        {
            int msgId = thread.getMessages().get(i).getId();

            if (!deleted.contains(msgId)) 
            {
                deleted.add(msgId);
                MessageStateDAO.markDeleted(userId, chatId, msgId);
            }

        }
    }


    public void markChatAsRead(int userId, int friendId)
    {
        String key = getChatKey(userId, friendId);
        Integer chatId = chatKeyToChatId.get(key);

        if (chatId == null)
            return;

        ChatThread thread = chatsById.get(chatId);

        if (thread == null)
            return;

        SimpleLinkedList<ChatMessage> messages = thread.getMessages();

        if (messages.isEmpty())
            return;

        ChatMessage lastMessage = messages.get(messages.size - 1);

        User user = getUserById(userId);

        user.getLastReadMessageByChat().put(chatId, lastMessage.getId());

        int lastMessageId = lastMessage.getId();
        MessageStateDAO.updateRead(userId, chatId, lastMessageId);

    }

    public int getUnreadMessageCount(int userId, int friendId)
    {
        String key = getChatKey(userId, friendId);
        Integer chatId = chatKeyToChatId.get(key);

        if (chatId == null)
            return 0;

        ChatThread thread = chatsById.get(chatId);
        if (thread == null)
            return 0;

        User user = getUserById(userId);

        Integer lastReadId = user.getLastReadMessageByChat().get(chatId);

        SimpleLinkedList<ChatMessage> messages = thread.getMessages();

        int count = 0;

        for (int i = 0; i < messages.size; i++)
        {
            ChatMessage msg = messages.get(i);

            if (msg.getSenderId() == userId)
                continue;

            if (lastReadId == null || msg.getId() > lastReadId)
                count++;
        }

        return count;
    }


    public boolean deleteMessagesForMe(int userId, int friendId, SimpleArrayList<Integer> messageIds)
    {
        String key = getChatKey(userId, friendId);
        Integer chatId = chatKeyToChatId.get(key);

        if (chatId == null)
            return false;

        User user = getUserById(userId);

        SimpleArrayList<Integer> deleted = user.getDeletedMessagesByChat().get(chatId);

        if (deleted == null)
        {
            deleted = new SimpleArrayList<>();
            user.getDeletedMessagesByChat().put(chatId, deleted);
        }

        for (int i = 0; i < messageIds.size(); i++)
        {
            deleted.add(messageIds.get(i));
            MessageStateDAO.markDeleted(userId, chatId, messageIds.get(i));
        }

        return true;
    }

    public SimpleLinkedList<Notification> getUnreadNotifications(int userId)
    {
        User user = getUserById(userId);

        SimpleLinkedList<Notification> result = new SimpleLinkedList<>();

        for (int i = 0; i < user.getNotifications().size; i++)
        {
            Notification n = user.getNotifications().get(i);

            if (!n.isRead())
                result.addLast(n);
        }

        return result;
    }

    public boolean markNotificationAsRead(int userId, int notificationId)
    {
        User user = getUserById(userId);

        for (int i = 0; i < user.getNotifications().size; i++)
        {
            Notification n = user.getNotifications().get(i);

            if (n.getId() == notificationId)
            {
                n.markRead();
                NotificationDAO.markRead(notificationId);
                return true;
            }
        }

        return false;
    }

    public void _addUser(User u) 
    {
        usersById.put(u.getId(), u);
        usernameToId.put(u.getUsername(), u.getId());
    }

    public void _addFriendRequest(FriendRequest fr) 
    {
        friendRequestsById.put(fr.getId(), fr);
    }

    public void _addChat(ChatThread t, String key) 
    {
        chatsById.put(t.getId(), t);
        chatKeyToChatId.put(key, t.getId());
    }

    public ChatThread _getChat(int chatId) 
    {
        return chatsById.get(chatId);
    }

    public void _setCounters(int u, int fr, int ch, int msg, int notif) 
    {
        nextUserId = u;
        nextFriendReqId = fr;
        nextChatId = ch;
        nextMessageId = msg;
        nextNotificationId = notif;
    }
}