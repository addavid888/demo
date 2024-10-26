/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Topic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maity
 */
public interface TopicDAO {

    public final String TOPIC_FILE_NAME = "TOPIC.txt";

    public static List<Topic> topics = new ArrayList<>();

    void manageTopics();

    void addTopic();

    void updateTopic();

    void deleteTopic();

    void displayAllTopics();


}
