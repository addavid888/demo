/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileIO;


import static dao.TopicDAO.topics;
import dto.Topic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author maity
 */
public class TopicIO implements IFileIO{
     
    @Override
     public void readFromFile(String fileName) {
    topics.clear();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\/");
            if (parts.length == 5) { // Đảm bảo số phần tử phù hợp với lớp Topic
                String id = parts[0];
                String name = parts[1];
                
                // Chuyển chuỗi "long-term" hoặc "short-term" thành boolean
                boolean type = parts[2].equalsIgnoreCase("long-term");
                
                String title = parts[3];
                
                // Chuyển đổi chuỗi thành int cho duration
                int duration = Integer.parseInt(parts[4]);

                // Tạo đối tượng Topic
                Topic topic = new Topic(id, name, type, title, duration);
                
                // Thêm vào danh sách topics
                topics.add(topic);
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the file: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Invalid number format: " + e.getMessage());
    }
}



   

    @Override
    public void writeToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Topic topic : topics) {
                // Ghi tất cả các thuộc tính của Topic vào file
                bw.write(topic.getId() + "/" + topic.getName() + "/" 
                         + (topic.isType() ? "long-term" : "short-term") + "/"
                         + topic.getTitle() + "/" + topic.getDuration());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    
}
