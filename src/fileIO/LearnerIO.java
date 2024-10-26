package fileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static dao.LearnerDAO.learners; // Giả sử bạn có một lớp LearnerDAO chứa danh sách learners
import dto.Learner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LearnerIO implements IFileIO {

    @Override
    public void readFromFile(String fileName) {
        learners.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\/");  // Đảm bảo file sử dụng dấu phân tách
                if (parts.length == 5) {  // Kiểm tra số lượng phần tử
                    String id = parts[0];
                    String name = parts[1];

                    // Chuyển đổi chuỗi ngày sinh thành LocalDate
                    LocalDate dob = LocalDate.parse(parts[2]);

                    // Chuyển đổi chuỗi điểm số thành double
                    double score = Double.parseDouble(parts[3]);

                    String courseId = parts[4];

                    // Tạo đối tượng Learner và thêm vào danh sách
                    Learner learner = new Learner(id, name, dob, score, courseId);
                    learners.add(learner);
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
            for (Learner learner : learners) {
                // Ghi tất cả thông tin của Learner vào file
                bw.write(learner.getId() + "/" + learner.getName() + "/" 
                         + learner.getDob().toString() + "/" 
                         + learner.getScore() + "/" 
                         + learner.getCourseId());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
