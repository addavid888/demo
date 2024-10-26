/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;

import utils.Validate;

/**
 *
 * @author maity
 */
public class RAMMenu extends ArrayList<String> implements I_Menu {

    public RAMMenu() {
        super();
    }

    @Override
    public void addItem(String s) {
        this.add(s);
    }

    @Override
    public int getChoice() {
        
        return Validate.getInteger("Enter your choice: ",
                "Choice must be digits", 1, 6);
    }

    @Override
    public void showMenu() {
        System.out.println("Courses Program Management System");
        System.out.println("=============================");
        for (String item : this) {
            System.out.println(item);
        }
        System.out.println("=============================");
    }

   

    @Override
    public void addOptions() {
        addItem("1. Manage the Topics");
        addItem("2. Manage the Course");
        addItem("3. Manage the Learner");
        addItem("4. Search information");
        addItem("5. Save data to file");
        addItem("6. Quit");
    }
}
