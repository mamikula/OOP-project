package agh.ics.oop;

import java.io.*;

public class Files {
    //https://stackoverflow.com/questions/4614227/how-to-add-a-new-line-of-text-to-an-existing-file-in-java
    File myFile;

    public Files(String fileName) {
        this.myFile = new File(fileName);
        String newLine = "day, animals, grasses, energy, avgLife";
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(myFile), "UTF-8"));
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(int day, int animals, int grasses, int energy, int avgLife) {
        try {
            String newLine = day + "," + animals + "," + grasses + "," + energy + "," + avgLife + "\r\n";
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(myFile, true), "UTF-8"));

            writer.append(newLine);
            writer.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
//            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

