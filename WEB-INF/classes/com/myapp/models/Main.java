package com.myapp.models;
import java.util.Scanner;
import java.util.Date;
public class Main 
{
    public static void main(String[] args) 
    {
        Manager manager = new Manager();

        DataLoader.loadAll(manager);
        
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) 
        {
            if (currentUser == null) 
            {
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("1. Sign up");
                System.out.println("2. Sign in");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                int choice = readInt(scanner);

                while (choice < 0 || choice > 2) 
                {
                    System.out.print("Please choose between 0 and 2: ");
                    choice = readInt(scanner);
                }

                if (choice == 1) 
                {
                    currentUser = handleSignUp(manager, scanner);

                    if (currentUser != null) 
                    {
                        handleQuizFlow(manager, scanner, currentUser);
                        System.out.println("\n=== Complete Your Profile ===");
                        handleProfileCompletion(manager, scanner, currentUser);
                    }

                } 
                else if (choice == 2) 
                {
                    currentUser = handleSignIn(manager, scanner);

                } 
                else if (choice == 0) 
                {
                    System.out.println("Goodbye!");
                    break;
                }

            } 
            else 
            {
                System.out.println("\n=== USER MENU (Logged in as " + currentUser.getUsername() + ") ===");
                System.out.println("1. View & Edit Profile");
                System.out.println("2. View Friend Suggestions");
                System.out.println("3. Send Friend Request");
                System.out.println("4. View & Respond to Pending Requests");
                System.out.println("5. Undo Last Friend Action");
                System.out.println("6. View Friend Action History");
                System.out.println("7. View Restricted Public Profile of a User");
                System.out.println("8. View Full Profile of a Friend");
                System.out.println("9. Search Users");
                System.out.println("10. View Search History");
                System.out.println("11. Chat with a Friend");
                System.out.println("12. Delete Messages (Multiple)");
                System.out.println("13. Clear Chat History");
                System.out.println("14. View Notifications");
                System.out.println("15. Mark Notifications as Read");
                System.out.println("16. Unfriend a User");
                System.out.println("0. Logout");

                System.out.print("Choose: ");

                int choice = readInt(scanner);

                while (choice < 0 || choice > 16 )
                {
                    System.out.print("Please choose between 0 and 16: ");
                    choice = readInt(scanner);
                }




                if (choice == 1) 
                {
                    
                    System.out.println("\n1. View Profile ");
                    System.out.println("2. Edit Profile");
                    System.out.print("Choose: ");
                    int ch = readInt(scanner);

                     while (ch < 1 || ch > 2) 
                    {
                        System.out.print("Please choose between 1 and 2: ");
                        ch = readInt(scanner);
                    }


                    if(ch==1)
                    {
                            handleProfileView(currentUser);
                    }
                    else
                    {
                            handleProfileEdit(manager,scanner,currentUser);
                    }

                } 
                else if (choice == 2) 
                {
                    handleFriendSuggestions(manager, scanner, currentUser);
                } 
                else if (choice == 3) 
                {
                    handleSendFriendRequest(manager, scanner, currentUser);
                } 
                else if (choice == 4) 
                {
                    handlePendingRequests(manager, scanner, currentUser);
                } 
                else if (choice == 5) 
                {
                    handleUndo(manager, currentUser, manager);
                } 
                else if (choice == 6) 
                {
                    handleActionHistory(currentUser);
                } 
                else if (choice == 7) 
                {
                    handleRestrictedProfileView(manager, scanner, currentUser);
                } 
                else if (choice == 8) 
                {
                    handleFullProfileView(manager, scanner, currentUser);
                } 
                else if (choice == 9) 
                {
                    handleSearchUsers(manager, scanner, currentUser);
                }
                else if (choice == 10) 
                {
                    handleSearchHistory(manager, scanner, currentUser);
                }
                else if (choice == 11)
                {
                    handleChat(manager, scanner, currentUser);
                }
                else if (choice == 12)
                {
                    handleDeleteMessages(manager, scanner, currentUser);
                }
                else if (choice == 13)
                {
                    handleClearChat(manager, scanner, currentUser);
                }
                else if (choice == 14)
                {
                    handleViewNotifications(manager, scanner, currentUser);
                }
                else if (choice == 15)
                {
                    System.out.print("Enter Notification ID to mark as read: ");
                    int nid = readInt(scanner);

                    boolean ok = manager.markNotificationAsRead(currentUser.getId(), nid);

                    if (ok)
                        System.out.println("Notification marked as read.");
                    else
                        System.out.println("Notification not found.");

                }
                else if (choice == 16)
                {
                    handleUnfriend(manager, scanner, currentUser);
                }
                else if (choice == 0) 
                {
                    currentUser = null;
                }
            }
        }

        scanner.close();
    }

    private static int readInt(Scanner scanner) 
    {
        while (true) 
        {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } 
            catch (Exception e) 
            {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private static User handleSignUp(Manager manager, Scanner scanner) 
    {
        String username;

        while (true)
        {
            System.out.print("Choose a username: ");
            username = scanner.nextLine();

            if (!manager.isUsernameTaken(username))
            {
                break;
            }

            System.out.println("Username already taken. Try another one.");
        }

        String password;

        while (true)
        {
            System.out.print("Choose a password: ");
            password = scanner.nextLine();

            if (manager.isValidPassword(password))
            {
                break;
            }

            System.out.println("Password must be at least 6 characters and contain a number and an Alphabet.");
        }

        User user = manager.signUp(username, password);
        

        System.out.println("Sign up successful. Your user id is: " + user.getId());

        return user;
    }

    private static User handleSignIn(Manager manager, Scanner scanner) 
    {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = manager.signIn(username, password);

        if (user == null) 
        {
            System.out.println("Invalid credentials.");
        } 
        else 
        {
            System.out.println("Welcome back, " + user.getUsername() + "!");
        }

        return user;
    }

    private static void handleQuizFlow(Manager manager, Scanner scanner, User user) 
    {
        System.out.println("\n=== Personality Quiz ===");
        SimpleArrayList<PersonalityQuestion> questions = manager.getQuizQuestions();
        
        
        SimpleHashMap<Integer, Integer> answers = new SimpleHashMap<>();
        
        for (int i = 0; i < questions.size(); i++) 
        {
            PersonalityQuestion q = questions.get(i);
            System.out.println("\nQ" + q.getId() + ": " + q.getQuestionText());
            String[] opts = q.getOptions();

            for (int j = 0; j < opts.length; j++) 
            {
                System.out.println((j + 1) + ". " + opts[j]);
            }

            System.out.print("Your answer (1-4): ");
            int ans = readInt(scanner);

            while (ans < 1 || ans > 4) 
            {
                System.out.print("Please choose between 1 and 4: ");
                ans = readInt(scanner);
            }

            answers.put(q.getId(), ans); 
        }

        manager.submitQuizAnswers(user.getId(), answers);

        PersonalityProfile profile = user.getPersonalityProfile();

    }

    private static void handleProfileCompletion(Manager manager, Scanner scanner, User currentUser) 
    {
        Profile temp = new Profile();

        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        currentUser.setName(name);

        System.out.print("Enter your age: ");
        int age = readInt(scanner);
        currentUser.setAge(age);

        System.out.print("Enter your gender: ");
        String gender = scanner.nextLine();
        currentUser.setGender(gender);

        System.out.print("Enter your date of birth (dd-mm-yyyy): ");
        String dob = scanner.nextLine();
        currentUser.setDateOfBirth(dob);

        System.out.print("About you: ");
        temp.setAbout(scanner.nextLine());

        System.out.print("Education: ");
        temp.setEducation(scanner.nextLine());

        System.out.print("Profile picture path (just write any text): ");
        temp.setProfilePicturePath(scanner.nextLine());

        System.out.println("Enter your skills (type 'done' to finish):");

        while (true) 
        {
            String skill = scanner.nextLine();

            if (skill.equalsIgnoreCase("done")) 
                break;

            temp.getSkills().add(skill);
        }

        System.out.println("Enter your hobbies (type 'done' to finish):");

        while (true) 
        {
            String hobby = scanner.nextLine();

            if (hobby.equalsIgnoreCase("done")) 
                break;

            temp.getHobbies().add(hobby);
        }

        manager.CompleteProfile(currentUser.getId(), temp);
        System.out.println("Profile updated.");
    }

    private static void handleFriendSuggestions(Manager manager, Scanner scanner, User currentUser) 
    {
        SimpleArrayList<FriendSuggestionEntry> suggestions = manager.getFriendSuggestions(currentUser.getId());

        if (suggestions.size() == 0) 
        {
            System.out.println("No suggestions available yet.");
            return;
        }

        System.out.println("=== Friend Suggestions ===");

        for (int i = 0; i < suggestions.size(); i++) 
        {
            FriendSuggestionEntry entry = suggestions.get(i);
            PublicProfileSummary s = entry.getSummary();
            System.out.println((i + 1) + ". ID: " + s.getUserId() +", Name: " + s.getName() + ", Similarity: " + String.format("%.1f", entry.getSimilarityPercent()) + "%");
        }
    }

    private static void handleSendFriendRequest(Manager manager, Scanner scanner, User currentUser) 
    {
        System.out.print("Enter the user ID you want to send request to: ");
        int toId = readInt(scanner);
        FriendRequest fr = manager.sendFriendRequest(currentUser.getId(), toId);

        if (fr == null) 
        {
            System.out.println("Could not send friend request. Maybe already friends or pending.");
        } 
        else 
        {
            System.out.println("Friend request sent. Request ID: " + fr.getId());
        }
    }

    private static void handlePendingRequests(Manager manager, Scanner scanner, User currentUser) 
    {
        SimpleArrayList<FriendRequest> pending = manager.getPendingRequestsForUser(currentUser.getId());

        if (pending.size() == 0) 
        {
            System.out.println("No pending requests.");
            return;
        }

        System.out.println("=== Pending Friend Requests ===");

        for (int i = 0; i < pending.size(); i++) 
        {
            FriendRequest fr = pending.get(i);
            User from = manager.getUserById(fr.getFromUserId());
            System.out.println("Request ID: " + fr.getId() + " from User ID: " + fr.getFromUserId() +" (" + from.getUsername() + ")" );

        }

        System.out.print("Enter Request ID to respond (or 0 to cancel): ");
        int id = readInt(scanner);

        if (id == 0) 
            return;

        System.out.print("1 = Accept, 2 = Reject: ");

        int action = readInt(scanner);

        while (action < 1 || action > 2) 
        {
            System.out.print("Please choose between 1 and 2: ");
            action = readInt(scanner);
        }


        if (action == 1) 
        {
            manager.acceptFriendRequest(id, currentUser.getId());
            System.out.println("Request accepted.");
        } 
        else if (action == 2) 
        {
            manager.rejectFriendRequest(id, currentUser.getId());
            System.out.println("Request rejected.");
        }

    }

    private static void handleUndo(Manager manager, User currentUser, Manager mgr) 
    {
        boolean success = mgr.undoLastAction(currentUser.getId());

        if (success) 
        {
            System.out.println("Last action undone (if possible).");
        } 
        else 
        {
            System.out.println("Nothing to undo.");
        }

    }

    private static void handleActionHistory(User currentUser) 
    {
        SimpleLinkedList<FriendAction> history = currentUser.getActionHistory();

        if (history == null || history.size == 0) 
        {
            System.out.println("No actions yet.");
            return;
        }

        System.out.println("\n=== Friend Action History ===");
        SimpleLinkedList.Node<FriendAction> node = history.head;

        while (node != null) 
        {
            FriendAction a = node.value;
            System.out.println("Action: " + a.getType() + ", with user ID: " + a.getOtherUserId() + ", request ID: " + a.getFriendRequestId());
            node = node.next;
        }
    }

    private static void handleRestrictedProfileView(Manager manager, Scanner scanner, User currentUser) 
    {
        System.out.print("Enter user ID to view public profile: ");
        int id = readInt(scanner);
        PublicProfileSummary summary = manager.getPublicProfileSummary(id);

        if (summary == null) 
        {
            System.out.println("User not found.");
            return;
        }


        System.out.println("=== Public Profile ===");
        System.out.println("User ID: " + summary.getUserId());
        System.out.println("Name: " + summary.getName());
        System.out.println("About: " + summary.getAbout());
        System.out.println("Friends count: " + summary.getFriendsCount());

    }

    private static void handleFullProfileView(Manager manager, Scanner scanner, User currentUser) 
    {
        System.out.print("Enter friend user ID to view full profile: ");
        int id = readInt(scanner);
        Profile profile = manager.getFullProfile(currentUser.getId(), id);

        if (profile == null) 
        {
            System.out.println("Either user not found or not your friend.");
            return;
        }

        User friend = manager.getUserById(id);

        System.out.println("\n=== Full Profile ===");
        System.out.println("Name: " + friend.getName());
        System.out.println("Age: " + friend.getAge());
        System.out.println("Gender: " + friend.getGender());
        System.out.println("DOB: " + friend.getDateOfBirth());
        System.out.println("About: " + profile.getAbout());
        System.out.println("Education: " + profile.getEducation());
        System.out.println("Skills:");
        SimpleArrayList<String> skills = profile.getSkills();

        for (int i = 0; i < skills.size(); i++) 
        {
            System.out.println("- " + skills.get(i));
        }

        System.out.println("Hobbies:");
        SimpleArrayList<String> hobbies = profile.getHobbies();

        for (int i = 0; i < hobbies.size(); i++) 
        {
            System.out.println("- " + hobbies.get(i));
        }

    }

    private static void handleProfileView(User currentUser) 
    {
        Profile profile = currentUser.getProfile();

        System.out.println("\n=== Full Profile ===");
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Age: " + currentUser.getAge());
        System.out.println("Gender: " + currentUser.getGender());
        System.out.println("DOB: " + currentUser.getDateOfBirth());
        System.out.println("About: " + profile.getAbout());
        System.out.println("Education: " + profile.getEducation());
        System.out.println("Skills:");
        SimpleArrayList<String> skills = profile.getSkills();

        for (int i = 0; i < skills.size(); i++) 
        {
            System.out.println("- " + skills.get(i));
        }

        System.out.println("Hobbies:");
        SimpleArrayList<String> hobbies = profile.getHobbies();

        for (int i = 0; i < hobbies.size(); i++) 
        {
            System.out.println("- " + hobbies.get(i));
        }
    }

    private static void handleProfileEdit(Manager manager, Scanner scanner, User currentUser)
    {
        while (true)
        {
            System.out.println("\n=== Edit Profile ===");
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Age");
            System.out.println("3. Edit About");
            System.out.println("4. Edit Education");
            System.out.println("5. Edit Skills");
            System.out.println("6. Edit Hobbies");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt(scanner);

            while (choice < 0 || choice > 6) 
            {
                System.out.print("Please choose between 0 and 6: ");
                choice = readInt(scanner);
            }


            if (choice == 0) 
            {
                return;
            }
            else if(choice==1)
            {
                System.out.print("New name: ");
                manager.editName(currentUser.getId(), scanner.nextLine());
            }
            else if(choice==2)
            {
                System.out.print("New age: ");
                manager.editAge(currentUser.getId(), readInt(scanner));
            }
            else if(choice==3)
            {
                System.out.print("New About: ");
                manager.editAbout(currentUser.getId(), scanner.nextLine());
            }
            else if(choice==4)
            {
                System.out.print("New Education: ");
                manager.editEducation(currentUser.getId(), scanner.nextLine());
            }
            else if(choice==5)
            {
                handleSkillEdit(manager, scanner, currentUser);
            }
            else if(choice==6)
            {
                handleHobbyEdit(manager, scanner, currentUser);
            }
        

            System.out.println("Updated successfully.");
        }
    }

    private static void handleSkillEdit(Manager manager, Scanner scanner, User user)
    {
        System.out.println("1. Add Skill");
        System.out.println("2. Remove Skill");
        System.out.print("Choose: ");

        int ch = readInt(scanner);

        while (ch < 1 || ch > 2) 
        {
            System.out.print("Please choose between 1 and 2: ");
            ch = readInt(scanner);
        }


        System.out.print("Enter skill: ");
        String skill = scanner.nextLine();

            if (ch == 1)
            {
                manager.addSkill(user.getId(), skill);
            }
            else if (ch == 2)
            {
                manager.removeSkill(user.getId(), skill);
            }

    }

     private static void handleHobbyEdit(Manager manager, Scanner scanner, User user)
    {
        System.out.println("1. Add Hobby");
        System.out.println("2. Remove Hobby");
        System.out.print("Choose: ");

        int ch = readInt(scanner);

        while (ch < 1 || ch > 2) 
        {
            System.out.print("Please choose between 1 and 2: ");
            ch = readInt(scanner);
        }

        System.out.print("Enter Hobby: ");
        String hobby = scanner.nextLine();

            if (ch == 1)
            {
                manager.addHobby(user.getId(), hobby);
            }
            else if (ch == 2)
            {
                manager.removeHobby(user.getId(), hobby);
            }
            

           
    }

    private static void handleSearchUsers(Manager manager, Scanner scanner, User currentUser) 
    {
        System.out.print("Enter keyword to search for (name, skill, hobby): ");
        String keyword = scanner.nextLine();

        SimpleArrayList<PublicProfileSummary> results = manager.searchUsers(keyword, currentUser.getId());

        if (results.size() == 0) 
        {
            System.out.println("No users found matching that keyword.");
            return;
        }

        System.out.println("\n=== Search Results ===");

        for (int i = 0; i < results.size(); i++) 
        {
            PublicProfileSummary summary = results.get(i);
            System.out.println((i + 1) + ". ID: " + summary.getUserId() + ", Name: " + summary.getName() + ", About: " + summary.getAbout() + ", Friends: " + summary.getFriendsCount());
        }
    }

    private static void handleSearchHistory(Manager manager, Scanner scanner, User currentUser) 
    {
        SimpleLinkedList<SearchHistoryEntry> history = manager.getSearchHistory(currentUser.getId());

        if (history == null || history.size == 0) 
        {
            System.out.println("No search history yet.");
            return;
        }

        System.out.println("\n=== Search History ===");

        SimpleLinkedList.Node<SearchHistoryEntry> node = history.head;
        int index = 0;

        while (node != null) 
        {
            SearchHistoryEntry entry = node.value;
            long ts = entry.getTimestamp();
            Date date = new Date(ts);
            System.out.println((index+1) + ". " + entry.getQuery() + " (searched at: " + date + ")");
            node = node.next;
            index++;
        }

        System.out.println("\nOptions:");
        System.out.println("1. Remove one entry");
        System.out.println("2. Clear all search history");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int choice = readInt(scanner);

        while (choice < 0 || choice > 2) 
        {
            System.out.print("Please choose between 1 and 2: ");
            choice = readInt(scanner);
        }


        if (choice == 1)
        {
            System.out.print("Enter index to remove (starting from 0): ");
            int removeIndex = readInt(scanner);

            if (removeIndex >= 0 && removeIndex < history.size)
            {
                boolean removed = manager.removeSearchHistoryEntry(currentUser.getId(), removeIndex);
                if (removed)
                    System.out.println("Entry removed.");
                else
                    System.out.println("Failed to remove entry.");
            }
            else
            {
                System.out.println("Invalid index.");
            }
        }
        else if (choice == 2)
        {
            manager.clearSearchHistory(currentUser.getId());
            System.out.println("Search history cleared.");
        }

    }

    private static void handleChat(Manager manager, Scanner scanner, User currentUser)
    {
        System.out.print("Enter friend user ID to chat with: ");
        int friendId = readInt(scanner);

        User friend = manager.getUserById(friendId);

        if (friend == null)
        {
            System.out.println("User not found.");
            return;
        }

        if (!currentUser.isFriendWith(friendId))
        {
            System.out.println("You can only chat with friends.");
            return;
        }

        int unread = manager.getUnreadMessageCount(currentUser.getId(), friendId);

        if (unread > 0)
        {
            System.out.println("You have " + unread + " unread messages.");
        }


        while (true)
        {
            System.out.println("\n=== Chat with " + friend.getUsername() + " ===");

            SimpleLinkedList<ChatMessage> messages = manager.getChatHistory(currentUser.getId(), friendId);

            if (messages != null && messages.size > 0)
            {
                SimpleLinkedList.Node<ChatMessage> node = messages.head;

                while (node != null)
                {
                    ChatMessage msg = node.value;

                    String senderName;

                    if (msg.getSenderId() == currentUser.getId())
                    {
                        senderName = "You";
                    }
                    else
                    {
                        senderName = friend.getUsername();
                    }

                    System.out.println(senderName + ": " + msg.getContent());
                    node = node.next;
                }

                manager.markChatAsRead(currentUser.getId(), friendId);

            }
            else
            {
                System.out.println("No messages yet.");
            }

            System.out.println("\nOptions:");
            System.out.println("1. Send message");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt(scanner);

            if (choice == 0)
                return;

            if (choice == 1)
            {
                System.out.print("Enter message: ");
                String text = scanner.nextLine();

                manager.sendMessage(currentUser.getId(), friendId, text);
            }
        }
    }

    private static void handleDeleteMessages(Manager manager, Scanner scanner, User currentUser)
    {
        System.out.print("Enter friend ID: ");
        int friendId = readInt(scanner);

        System.out.print("Enter message IDs to delete (comma separated): ");
        String input = scanner.nextLine();

        SimpleArrayList<Integer> ids = new SimpleArrayList<>();

        String[] parts = input.split(",");

        for (int i = 0; i < parts.length; i++)
        {
            if (parts[i].length() > 0)
            {
                ids.add(Integer.parseInt(parts[i]));
            }
        }

        boolean result = manager.deleteMessagesForMe(currentUser.getId(), friendId, ids);

        if (result)
            System.out.println("Messages deleted successfully.");
        else
            System.out.println("Failed to delete messages.");
    }


    private static void handleClearChat(Manager manager, Scanner scanner, User currentUser)
    {
        System.out.print("Enter friend ID to clear chat: ");
        int friendId = readInt(scanner);

        manager.clearChatHistory(currentUser.getId(), friendId);

        System.out.println("Chat cleared for you.");
    }

    private static void handleViewNotifications(Manager manager, Scanner scanner, User currentUser)
    {
        SimpleLinkedList<Notification> notifications = manager.getUnreadNotifications(currentUser.getId());

        if (notifications == null || notifications.size == 0)
        {
            System.out.println("No new notifications.");
            return;
        }

        System.out.println("\n=== Unread Notifications ===");

        SimpleLinkedList.Node<Notification> node = notifications.head;

        while (node != null)
        {
            Notification n = node.value;
            System.out.println("ID: " + n.getId() + " | " + n.getMessage());
            node = node.next;
        }
    }

    private static void handleUnfriend(Manager manager, Scanner scanner, User currentUser)
    {
        SimpleArrayList<User> friends = manager.getFriendsOfUser(currentUser.getId());

        if (friends.size() == 0)
        {
            System.out.println("You have no friends to unfriend.");
            return;
        }

        System.out.println("\n=== Your Friends ===");

        for (int i = 0; i < friends.size(); i++)
        {
            User f = friends.get(i);
            System.out.println((i + 1) + ". ID: " + f.getId() + " | " + f.getUsername());
        }

        System.out.print("Enter friend ID to unfriend (or 0 to cancel): ");
        int friendId = readInt(scanner);

        if (friendId == 0)
            return;

        manager.unfriend(currentUser.getId(), friendId);

        System.out.println("User unfriended successfully.");
    }





}