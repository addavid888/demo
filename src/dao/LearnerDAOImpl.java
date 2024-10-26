/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.CourseDAO.courses;
import dto.Course;
import dto.Learner;
import fileIO.LearnerIO;
import java.time.LocalDate;
import java.util.List;
import utils.Validate;

/**
 *
 * @author maity
 */
public class LearnerDAOImpl implements LearnerDAO {

    LearnerIO learnerIO = new LearnerIO();

    public LearnerDAOImpl() {
        learnerIO.readFromFile(LEARNER_FILE_NAME);
    }

    public List<Learner> getLearner() {
        return learners;
    }

    public void manageLearner() {
        int choice;
        do {
            System.out.println("1. Add Learner");
            System.out.println("2. Enter scores ");
            System.out.println("3. Display Learner information");
            System.out.println("4. Back to Main Menu");
            choice = utils.Validate.getInteger("Enter your choice: ", "Choice must be digits", 1, 4);
            switch (choice) {
                case 1:
                    addLearner();
                    break;
                case 2:
                    enterScore();
                    break;
                case 3:
                    Display();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private String generateNewId() {
        int count = learners.size(); // Get the current number of topics
        return String.format("L_%d", count + 1);
    }

    private boolean isDuplicateId(String id) {
        for (Learner leaner : learners) {
            if (leaner.getId().equals(id)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate found
    }

    private void addLearner() {
        CourseDAOImpl courseDAO = new CourseDAOImpl();
        while (true) {
            // Get course details from user
            String id = generateNewId();
            while (isDuplicateId(id)) {
                id = generateNewId();
            }
            System.out.println("Course id is " + id);

            String name = Validate.getString("Enter Learner name: ", "Name cannot be empty.",
                    "^(?!\\s*$).+");
            LocalDate dob = Validate.getDate("Enter date of birth (yyyy-mm-dd): ", "Wrong format (yyyy-mm-dd)");

            String courseId = courseDAO.chooseTopic();
            Learner newLearner = new Learner(id, name, dob, courseId);
            learners.add(newLearner);

            System.out.println("New product has been added successfully!");
            if (Validate.getBoolean("Do you want to return to the main menu? (y/n): ", "Please enter y or n.")) {
                break;
            }
        }
    }

    private void Display() {
        System.out.println(String.format("%-10s|%-20s|%-15s|%-15s|%-10s|%-10s",
                "ID", "Name", "DOB", "Score", "Status", "Course ID"));

        for (Learner learner : learners) {

            String GPA = learner.getScore() + "- GPA: " + String.format("%.2f", (learner.getScore() / 10.0) * 4);

            System.out.println(String.format("%-10s|%-20s|%-15s|%-15s|%-10s|%-10s",
                    learner.getId(),
                    learner.getName(),
                    learner.getDob(),
                    GPA,
                    learner.getStatus(),
                    learner.getCourseId()
            ));
        }

    }

    private void enterScore() {
        System.out.println("=== Update Topic ===");
        String learnerId = Validate.getString("Enter the code of the learner: ", "Code cannot be empty.", "^(?!\\s*$).+");
        Learner learnerToUpdate = null;
        for (Learner learner : learners) {
            if (learner.getId().equals(learnerId)) {
                learnerToUpdate = learner;
                break;
            }
        }

        if (learnerToUpdate == null) {
            System.out.println("The topic does not exist");
            if (Validate.getBoolean("Do you want to add new topic?(y/n)", "please enter y or n.")) {
                addLearner();
            }
            return;
        }

        double newScore = Validate.getDouble("Enter new score: ", "Invalid score", 0.0, 10.0); // Assuming 0-100 score range
        learnerToUpdate.setScore(newScore);
        System.out.println("Score updated successfully for Learner ID " + learnerId + ". New Status: " + learnerToUpdate.getStatus());
    }

    public int countLearner(String courseId) {
        int count = 0;
        for (Learner learner : learners) {
            if (learner.getCourseId().equals(courseId)) {
                
                count++;

            }
        }
        return count;

    }

    public int countP(String courseId) {
        int count = 0;
        for (Learner learner : learners) {
            if (learner.getCourseId().equals(courseId)) {
                if (learner.getStatus().equals("Pass")) {
                    count++;
                }

            }
        }
        return count;

    }
}