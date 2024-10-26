/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.CourseDAO.COURSE_FILE_NAME;
import dao.CourseDAOImpl;
import static dao.LearnerDAO.LEARNER_FILE_NAME;
import dao.LearnerDAOImpl;
import static dao.TopicDAO.TOPIC_FILE_NAME;
import dao.TopicDAOImpl;
import fileIO.CourseIO;
import fileIO.LearnerIO;
import fileIO.TopicIO;

/**
 *
 * @author maity
 */
public class Controller implements IController {

    TopicDAOImpl topicDAO = new TopicDAOImpl();
    TopicIO topicIO = new TopicIO();
    CourseIO courseIO = new CourseIO();
    LearnerIO learnerIO = new LearnerIO();
    CourseDAOImpl courseDAO = new CourseDAOImpl();
    LearnerDAOImpl learnerDAO = new LearnerDAOImpl();

    @Override
    public void topic() {
        topicDAO.manageTopics();
    }

    @Override
    public void course() {
        courseDAO.manageCourses();
    }

    @Override
    public void learner() {
        learnerDAO.manageLearner();
    }

    @Override
    public void search() {

        int choice;
        do {
            System.out.println("1. Search Topic");
            System.out.println("2. Search Course");
            System.out.println("3. Back to Main Menu");
            choice = utils.Validate.getInteger("Enter your choice: ", "Choice must be digits", 1, 3);
            switch (choice) {
                case 1:
                    topicDAO.search();
                    break;
                case 2:
                    courseDAO.searchCourse();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }    

    @Override
    public void save() {

        topicIO.writeToFile(TOPIC_FILE_NAME);
        courseIO.writeToFile(COURSE_FILE_NAME);
        learnerIO.writeToFile(LEARNER_FILE_NAME);
        System.out.println("RAM have been saved to file successfully!");
//    if(Validate.getBoolean("Do you want to go to the main menu?(y/n)", "please enter y or n.")){
//        return;
//        }
        return;
    }
}
