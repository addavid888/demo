/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//
//import controller.RAMController;
import controller.Controller;
import dao.TopicDAOImpl;
import utils.Validate;

/**
 *
 * @author maity
 */
public class RAMManagement {

    public static void main(String[] args) {
        I_Menu menu = new RAMMenu();
      Controller Controller = new Controller();

      

        boolean quit = false;

        menu.addOptions();
        while (!quit) {
            menu.showMenu();
            int choice = menu.getChoice();

            switch (choice) {
                case 1:
                    Controller.topic();
                    break;
                case 2:
                    Controller.course();
                    break;
                case 3:
                    Controller.learner();
                    break;
                case 4:
                    Controller.search();
                    break;
                case 5:
                    Controller.save();
                    break;
                case 6:
//                    if (Validate.getBoolean("Do you want to go to save before exiting the system?(y/n)", "please enter y or n.")) {             
////                    ramController.saveRAMToFile();}
//                    else{System.out.println("check ko luu");}
                    quit = true; 
                    System.out.println("Thank you for using Courses Program Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
