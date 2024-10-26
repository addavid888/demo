/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileIO;

/**
 *
 * @author maity
 * @param <T>
 */
public interface IFileIO<T> {

    public static final String RAM_FILE_NAME = "RAM.dat";

    void readFromFile(String fileName);
   
    void writeToFile(String fileName);

}