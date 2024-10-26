/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Topic;

import fileIO.TopicIO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import utils.Validate;

/**
 *
 * @author maity
 */
public class TopicDAOImpl extends ArrayList<Topic> implements TopicDAO {

    TopicIO topicIO = new TopicIO();

    public TopicDAOImpl() {
        topicIO.readFromFile(TOPIC_FILE_NAME);
    }

    public List<Topic> getTopic() {
        return topics;
    }

    @Override
    public void manageTopics() {
        int choice;
        do {
            System.out.println("1. Add Topic");
            System.out.println("2. Update Topic");
            System.out.println("3. Delete Topic");
            System.out.println("4. Display All Topics");
            System.out.println("5. Back to Main Menu");
            choice = utils.Validate.getInteger("Enter your choice: ",
                    "Choice must be digits", 1, 5);
            switch (choice) {
                case 1:
                    addTopic();
                    break;
                case 2:
                    updateTopic();
                    break;
                case 3:
                    deleteTopic();
                    break;
                case 4:
                    displayAllTopics();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private boolean isDuplicateId(String id) {
        for (Topic topic : topics) {
            if (topic.getId().equals(id)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate found
    }

    @Override
    public void addTopic() {
        while (true) {

            String name = Validate.getString("Enter product name: ", "Product name cannot be empty.",
                    "^(?!\\s*$).+");
            boolean type = Validate.getBoolean("Enter type (y=Long-term/n=Short-term) ", "pls enter y or n");
            String title = Validate.getString("Enter title: ", "title cannot be empty.",
                    "^(?!\\s*$).+");
            int duration = Validate.getInteger("Enter duration (hours): ", "not valid", 1, 9999);

            String id = generateNewId();
            while (isDuplicateId(id)) {
                int n = 1;
                id = generateNewId() + n;
                n++;
            }
            System.out.println(" toipc id is " + id);

            Topic newTopic = new Topic(id, name, type, title, duration);
            topics.add(newTopic);

            System.out.println("New product has been added successfully!");
            if (Validate.getBoolean("Do you want to return to the main menu? (y/n): ", "Please enter y or n.")) {
                break;
            }
        }
    }

    private String generateNewId() {
        int count = topics.size(); // Get the current number of topics
        return String.format("T_%d", count + 1);
    }

    @Override
    public void updateTopic() {

        System.out.println("=== Update Topic ===");

        String id = Validate.getString("Enter the code of the topic: ", "Code cannot be empty.", "^(?!\\s*$).+");

        Topic topicToUpdate = null;
        for (Topic topic : topics) {
            if (topic.getId().equals(id)) {
                topicToUpdate = topic;
                break;
            }
        }

        if (topicToUpdate == null) {
            System.out.println("The topic does not exist");
            if (Validate.getBoolean("Do you want to add new topic?(y/n)", "please enter y or n.")) {
                addTopic();
            }
            return;
        }

        System.out.println("Current Name: " + topicToUpdate.getName());// + "\nEnter new Name (leave blank to keep current): ");
        String newName = Validate.getString("Enter new Name (leave blank to keep current)", "invalid", "^$|^\\S+$");
        if (!newName.isEmpty()) {
            topicToUpdate.setName(newName);
        }

        System.out.println("Current Type: " + (topicToUpdate.isType() ? "long-term" : "short-term" + ""));
        boolean newType = Validate.getBoolean("Enter type (y=Long-term/n=Short-term) ", "pls enter y or n");

        topicToUpdate.setType(newType);

        System.out.println("Current Title: " + topicToUpdate.getTitle());
        String newTitle = Validate.getString("Enter new bus (leave blank to keep current)", "invalid bus", "^$|^\\S+$");
        if (!newTitle.isEmpty()) {
            topicToUpdate.setTitle(newTitle);
        }

        System.out.println("Current Duration: " + topicToUpdate.getDuration());
        String newDurationStr = Validate.getString("Enter new duration (leave blank to keep current) ", "invalid duration ", "^$|^(?:[1-9]|[1-9]\\d|[1-9]\\d{2}|1000)$");
        if (!newDurationStr.isEmpty()) {
            int newDuration = Integer.parseInt(newDurationStr);
            topicToUpdate.setDuration(newDuration);
        }

        System.out.println("Topic updated successfully.");
        System.out.println("Updated Topic: " + topicToUpdate);
    }

    @Override
    public void deleteTopic() {

        while (true) {
            String id = Validate.getString("Enter the code: ", "Code cannot be empty.", "^(?!\\s*$).+");
            Topic topicToDelete = null;
            for (Topic topic : topics) {
                if (topic.getId().equals(id)) {
                    topicToDelete = topic;
                    break;
                }
            }
            if (topicToDelete == null) {
                System.out.println("Topic does not exist");
            } else {
                System.out.println("current detail: " + topicToDelete);
                if (Validate.getBoolean("Are you sure you want to delete this RAM?(y/n)", "please enter y or n.")) {
                    topics.remove(topicToDelete);
                    System.out.println("RAM marked as inactive successfully.");
                    topicIO.writeToFile(TOPIC_FILE_NAME);
                }

            }
            if (Validate.getBoolean("Do you want to go to the main menu?(y/n)", "please enter y or n.")) {
                return;
            }
        }
    }

    @Override
    public void displayAllTopics() {

        Collections.sort(topics, new Comparator<Topic>() {
            @Override
            public int compare(Topic t1, Topic t2) {
                return t1.getName().compareToIgnoreCase(t2.getName());
            }
        });

        // Print the header
        System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20s",
                "Code", "Name", "Type", "Title", "Duration"));

        // Iterate over the list of topics and print each one
        for (Topic topic : topics) {
            String type = topic.isType() ? "long-term" : "short-term";

            // Print each topic in formatted columns
            System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20d",
                    topic.getId(),
                    topic.getName(),
                    type,
                    topic.getTitle(),
                    topic.getDuration()));
        }
    }

    public String chooseTopic(String prompt) {
        System.out.println(prompt);
        System.out.println("Choose a topic:");
        for (int i = 0; i < topics.size(); i++) {
            Topic topic = topics.get(i);
            System.out.println((i + 1) + ". " + topic.getId() + "(" + topic.getName() + ")");
        }
        System.out.println((topics.size() + 1) + ". Keep current topic");

        int choice = Validate.getInteger("Enter the number of the topic: ", "Invalid choice.", 1, topics.size() + 1);
        if (choice == topics.size() + 1) {
            return null; // Giữ nguyên thương hiệu hiện tại
        } else {
            return topics.get(choice - 1).getId();
        }
    }

    public String chooseTopic() {
        System.out.println("Choose a topic:");
        for (int i = 0; i < topics.size(); i++) {
            Topic topic = topics.get(i);
            System.out.println((i + 1) + ". " + topic.getName());
        }

        int choice = Validate.getInteger("Enter the number of the topic: ", "Invalid choice.", 1, topics.size());
        return topics.get(choice - 1).getId();
    }

    public void search() {
        while (true) {
            String id = Validate.getString("Enter the name: ", "name cannot be empty.", "^(?!\\s*$).+");

            int count = 0;
            System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20s",
                    "Code", "Name", "Type", "Title", "Duration"));

            for (Topic topic : topics) {

                if (topic.getName().toLowerCase().contains(id.toLowerCase())) {

                    String type = topic.isType() ? "long-term" : "short-term";

                    // Print each topic in formatted columns
                    System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20d",
                            topic.getId(),
                            topic.getName(),
                            type,
                            topic.getTitle(),
                            topic.getDuration()));

                    count++;
                }

            }
            if (count == 0) {
                System.err.println("Topic does not exist");
                if (Validate.getBoolean("Do you want to return to the main menu? (y/n): ", "Please enter y or n.")) {
                    break;
                }
            }else{break;}
        }
    }
}
