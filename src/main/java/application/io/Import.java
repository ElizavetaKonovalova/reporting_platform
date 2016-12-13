package application.io;

import java.io.BufferedReader;
import java.io.FileReader;

public class Import {

    private String DELIMETER = ",";

    public void importCSV(String filepath) {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            if(filepath.contains("Number")) {

            }

            if(filepath.contains("Text")) {

            }

            if(filepath.contains("Demo")) {

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
