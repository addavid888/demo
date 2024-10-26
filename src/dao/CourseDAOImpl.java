/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Course;
import dto.Learner;
import fileIO.CourseIO;
import fileIO.TopicIO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utils.Validate;

/**
 *
 * @author maity
 */
public class CourseDAOImpl extends ArrayList<Course> implements CourseDAO {

    CourseIO courseIO = new CourseIO();

    public CourseDAOImpl() {
        courseIO.readFromFile(COURSE_FILE_NAME);
    }

    public List<Course> getCourse() {
        return courses;
    }

    public void manageCourses() {
        int choice;
        do {
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Display All Courses");
            System.out.println("5. Back to Main Menu");
            choice = utils.Validate.getInteger("Enter your choice: ", "Choice must be digits", 1, 5);
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    updateCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    displayAllCourses();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private String generateNewId() {
        int count = courses.size(); // Get the current number of topics
        return String.format("C_%d", count + 1);
    }

    private boolean isDuplicateId(String id) {
        for (Course course : courses) {
            if (course.getId().equals(id)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate found
    }

    private void addCourse() {
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        while (true) {
            // Get course details from user
            String id = generateNewId();
            while (isDuplicateId(id)) {
                id = generateNewId();
            }
            System.out.println("Course id is " + id);

            String name = Validate.getString("Enter course name: ", "Name cannot be empty.",
                    "^(?!\\s*$).+");
            boolean type = Validate.getBoolean("Enter type (y=Online/n=Offline) ", "pls enter y or n");
            String title = Validate.getString("Enter title: ", "title cannot be empty.",
                    "^(?!\\s*$).+");

            LocalDate endDate;
            LocalDate beginDate = Validate.getDate("Enter Begin Date (yyyy-mm-dd): ", "Wrong format (yyyy-mm-dd)");
            while (true) {
                endDate = Validate.getDate("Enter End Date (yyyy-mm-dd): ", "Wrong format (yyyy-mm-dd)");

                if (!endDate.isBefore(beginDate)) { // Check if end date is after or same as begin date
                    break; // Valid end date, exit the loop
                } else {
                    System.out.println("End Date must be the same as or after the Begin Date. Please enter again.");
                }
            }
            double tuitionFee = Validate.getDouble("Enter Tuition fee", "Fee cannot be empty.", 0, Double.MAX_VALUE);

            String topicId = topicDAO.chooseTopic();
            Course newCourse = new Course(id, name, type, title, beginDate, endDate, tuitionFee, topicId);
            courses.add(newCourse);

            System.out.println("New product has been added successfully!");
            if (Validate.getBoolean("Do you want to return to the main menu? (y/n): ", "Please enter y or n.")) {
                break;
            }
        }
    }

    // 2.2 Update Course
    private void updateCourse() {
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        System.out.println("=== Update Topic ===");
        String courseId = Validate.getString("Enter the code of the topic: ", "Code cannot be empty.", "^(?!\\s*$).+");
        Course courseToUpdate = null;
        for (Course course : courses) {
            if (course.getId().equals(courseId)) {
                courseToUpdate = course;
                break;
            }
        }

        if (courseToUpdate == null) {
            System.out.println("The topic does not exist");
            if (Validate.getBoolean("Do you want to add new topic?(y/n)", "please enter y or n.")) {
                addCourse();
            }
            return;
        }

        System.out.println("Updating Course Information:");

        // Update each field if the user provides new information
        System.out.println("Current Name: " + courseToUpdate.getName());
        String newName = Validate.getString("Enter new Course Name (leave blank to keep current): ", "Invalid name", "^$|^\\S+$");
        if (!newName.isEmpty()) {
            courseToUpdate.setName(newName);
        }

        System.out.println("Current Type: " + (courseToUpdate.isType() ? "online" : "offline"));
        boolean newType = Validate.getBoolean("Enter Course Type (y=online/n=offline): ", "Please enter 'y' or 'n'");
        courseToUpdate.setType(newType);

        System.out.println("Current Begin Date: " + courseToUpdate.getBeginDate());
        String newBeginDate = Validate.getString("Enter new Begin Date (yyyy-mm-dd) (leave blank to keep current): ", "Invalid date format", "^$|\\d{4}-\\d{2}-\\d{2}$");
        if (!newBeginDate.isEmpty()) {
            courseToUpdate.setBeginDate(LocalDate.parse(newBeginDate));
        }

        System.out.println("Current End Date: " + courseToUpdate.getEndDate());
        String newEndDate = Validate.getString("Enter new End Date (yyyy-mm-dd) (leave blank to keep current): ", "Invalid date format", "^$|\\d{4}-\\d{2}-\\d{2}$");
//        if (!newEndDate.isEmpty()) {
//            courseToUpdate.setEndDate(LocalDate.parse(newEndDate));
//        }
        while (true) {

            if (!newEndDate.isEmpty()) {
                LocalDate newEndDate2 = LocalDate.parse(newEndDate);
                if (!newEndDate2.isBefore(LocalDate.parse(newBeginDate))) {  
                    courseToUpdate.setEndDate(newEndDate2);
                    break;  
                } else {
                    System.out.println("End Date must be the same as or after the Begin Date. Please enter again.");
                }
            } else {
                break; // Exit if the input is empty, retaining the current end date
            }
        }
        System.out.println("Current Tuition Fee: " + courseToUpdate.getTuitionFee());
        String newTuitionFeeStr = Validate.getString("Enter new Tuition Fee (leave blank to keep current): ", "Invalid tuition fee", "^$|\\d+(\\.\\d{1,2})?$");
        if (!newTuitionFeeStr.isEmpty()) {
            courseToUpdate.setTuitionFee(Double.parseDouble(newTuitionFeeStr));
        }
        System.out.println("Current TopicId: " + courseToUpdate.getTopicId());
        String newTopicid = topicDAO.chooseTopic("Choose new Topic:");
        if (!newTuitionFeeStr.isEmpty()) {
            courseToUpdate.setTopicId(newTopicid);
        }

        System.out.println("Course updated successfully.");
        System.out.println("Updated Course: " + courseToUpdate);
    }

    // 2.3 Delete Course
    public void deleteCourse() {

        while (true) {
            String id = Validate.getString("Enter the code: ", "Code cannot be empty.", "^(?!\\s*$).+");
            Course courseToDelete = null;
            for (Course course : courses) {
                if (course.getId().equals(id)) {
                    courseToDelete = course;
                    break;
                }
            }
            if (courseToDelete == null) {
                System.out.println("Topic does not exist");
            } else {
                System.out.println("current detail: " + courseToDelete);
                if (Validate.getBoolean("Are you sure you want to delete this RAM?(y/n)", "please enter y or n.")) {
                    courses.remove(courseToDelete);
                    System.out.println("RAM marked as inactive successfully.");
                    courseIO.writeToFile(COURSE_FILE_NAME);
                }

            }
            if (Validate.getBoolean("Do you want to go to the main menu?(y/n)", "please enter y or n.")) {
                return;
            }
        }
    }

    private void displayAllCourses() {
        LearnerDAOImpl learnerDAO = new LearnerDAOImpl();
        LocalDate currentDate = LocalDate.now();
        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getBeginDate().compareTo(c2.getBeginDate());
            }
        });

        // Print the header
        System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20s|%-10s|%-10s|%-10s|%-18s|%-10s",
                "ID", "Name", "Type", "Begin Date", "End Date", "Fee", "TopicId", "Incomes", "Status", "Passed"));

        // Iterate and display each course
        for (Course course : courses) {
            String type = course.isType() ? "online" : "offline";
            System.out.println(String.format("%-10s|%-20s|%-20s|%-20s|%-20s|%-10s|%-10s|%-10s|%-18s|%-10s",
                    course.getId(),
                    course.getName(),
                    type,
                    course.getBeginDate(),
                    course.getEndDate(),
                    course.getTuitionFee(),
                    course.getTopicId(),
                    learnerDAO.countLearner(course.getId()) * course.getTuitionFee(),
                    currentDate.isBefore(course.getBeginDate())
                            ? "Not started yet"
                            : (currentDate.isAfter(course.getEndDate()) ? "Ended" : "Active"),
                    learnerDAO.countP(course.getId()) + "/" + learnerDAO.countLearner(course.getId())
            ));
        }
    }

    public String chooseTopic(String prompt) {
        System.out.println(prompt);
        System.out.println("Choose a courses:");
        for (int i = 0; i < courses.size(); i++) {
            Course topic = courses.get(i);
            System.out.println((i + 1) + ". " + topic.getId() + "(" + topic.getName() + ")");
        }
        System.out.println((courses.size() + 1) + ". Keep current courses");

        int choice = Validate.getInteger("Enter the number of the course: ", "Invalid choice.", 1, courses.size() + 1);
        if (choice == courses.size() + 1) {
            return null; // Giữ nguyên thương hiệu hiện tại
        } else {
            return courses.get(choice - 1).getId();
        }
    }

    public String chooseTopic() {
        System.out.println("Choose a courses:");
        for (int i = 0; i < courses.size(); i++) {
            Course topic = courses.get(i);
            System.out.println((i + 1) + ". " + topic.getName());
        }

        int choice = Validate.getInteger("Enter the number of the Course: ", "Invalid choice.", 1, courses.size());
        return courses.get(choice - 1).getId();
    }

    public void searchCourse() {
        int choice;
        do {
            System.out.println("1. Search Course by Name");
            System.out.println("2. Search Course by Topic");
            System.out.println("3. Back to Main Menu");
            choice = utils.Validate.getInteger("Enter your choice: ", "Choice must be digits", 1, 3);
            switch (choice) {
                case 1:
                    searchName();
                    break;
                case 2:
                    searchTopic();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void searchName() {
        while (true) {
            LearnerDAOImpl learnerDAO = new LearnerDAOImpl();
            LocalDate currentDate = LocalDate.now();
            String id = Validate.getString("Enter the name: ", "name cannot be empty.", "^(?!\\s*$).+");

            int count = 0;
            System.out.println(String.format("%-10s|%-20s|%-10s|%-10s|%-20s|%-10s",
                    "ID", "Name", "Fee", "Incomes", "Status", "Passed"));

            for (Course topic : courses) {

                if (topic.getName().toLowerCase().contains(id.toLowerCase())) {

                    // Print each topic in formatted columns
                    System.out.println(String.format("%-10s|%-20s|%-10s|%-10s|%-20s|%-10s",
                            topic.getId(),
                            topic.getName(),
                            topic.getTuitionFee(),
                            learnerDAO.countLearner(topic.getId()) * topic.getTuitionFee(),
                            currentDate.isBefore(topic.getBeginDate())
                                    ? "Not started yet"
                                    : (currentDate.isAfter(topic.getEndDate()) ? "Ended" : "Active"),
                            learnerDAO.countP(topic.getId()) + "/" + learnerDAO.countLearner(topic.getId())
                    ));
                    count++;
                }

            }
            if (count == 0) {
                System.err.println("Topic does not exist");
                if (Validate.getBoolean("Do you want to return to the main menu? (y/n): ", "Please enter y or n.")) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    private void searchTopic() {
        LearnerDAOImpl learnerDAO = new LearnerDAOImpl();
        LocalDate currentDate = LocalDate.now();

        String id = chooseTopic();

        System.out.println(String.format("%-10s|%-20s|%-10s|%-10s|%-20s|%-10s",
                "ID", "Name", "Fee", "Incomes", "Status", "Passed"));

        Course courseToDelete = null;
        for (Course course : courses) {
            if (course.getTopicId().equals(id)) {
                courseToDelete = course;
                break;
            }
        }
        if (courseToDelete == null) {
            System.out.println("Course does not exist");
        } else {

            System.out.println(String.format("%-10s|%-20s|%-10s|%-10s|%-20s|%-10s",
                    courseToDelete.getId(),
                    courseToDelete.getName(),
                    courseToDelete.getTuitionFee(),
                    learnerDAO.countLearner(courseToDelete.getId()) * courseToDelete.getTuitionFee(),
                    currentDate.isBefore(courseToDelete.getBeginDate())
                            ? "Not started yet"
                            : (currentDate.isAfter(courseToDelete.getEndDate()) ? "Ended" : "Active"),
                    learnerDAO.countP(courseToDelete.getId()) + "/" + learnerDAO.countLearner(courseToDelete.getId())
            ));
        }

    }
}
