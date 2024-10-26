/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileIO;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static dao.CourseDAO.courses;
import dto.Course;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author maity
 */
public class CourseIO implements IFileIO{
    
     @Override
     public void readFromFile(String fileName) {
        courses.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\/");  // Chắc chắn file sử dụng dấu chấm để phân tách
                if (parts.length == 8) {
                    String id = parts[0];
                    String name = parts[1];
                    boolean type = parts[2].equalsIgnoreCase("online"); // Chuyển chuỗi thành boolean
                    String title = parts[3];
                    

                    LocalDate beginDate = LocalDate.parse(parts[4]);
                    LocalDate endDate = LocalDate.parse(parts[5]);
                    

                    double tuitionFee = Double.parseDouble(parts[6]);
                    
                    String topicId = parts[7];
                    Course course = new Course(id, name, type, title, beginDate, endDate, tuitionFee, topicId);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Course course : courses) {
                // Ghi toàn bộ thông tin của Course vào file
                bw.write(course.getId() + "/" + course.getName() + "/" 
                         + (course.isType() ? "online" : "offline") + "/" 
                         + course.getTitle() + "/" 
                         + course.getBeginDate().toString() + "/" 
                         + course.getEndDate().toString() + "/" 
                         + course.getTuitionFee() + "/" 
                         + course.getTopicId());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
