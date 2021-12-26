package agh.ics.oop;

import java.io.*;

public class Files {
    File myFile;

    public Files(String fileName) {
        this.myFile = new File(fileName);
    }



    public void WriteToFile(int day, int animals, int grasses) {
        try {
            String newLine = day + "   " + animals + "   " + grasses + "\r\n";
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(myFile, true), "UTF-8"));

            writer.append(newLine);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

