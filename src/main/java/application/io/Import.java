package application.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.*;

public class Import {

    private final String DELIMETER = ",";

    public Boolean readRecordsFromCSVFile(String file_name) throws IOException {

        /* Get a specific file */
        FileInputStream input = new FileInputStream(new File("../files/" + file_name + ".csv"));

        /* Handle the CSV formatting */
        CharsetDecoder charsetDecoder = Charset.forName("UTF-8").newDecoder();
        charsetDecoder.onMalformedInput(CodingErrorAction.IGNORE);

        /* Pass both file path and csv formatting to the input stream */
        InputStreamReader inputStreamReader = new InputStreamReader(input, charsetDecoder);

        /* Read file content*/
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String headers = bufferedReader.readLine();
        String data = "";

        HashMap<String, String> map = new HashMap<String, String>();

        List<String> splitted_headers = Arrays.asList(headers.split(this.DELIMETER));

        while (data != null) {
            data = bufferedReader.readLine();
            stringBuilder.append(data);
        }

        bufferedReader.close();
        return splitted_headers.contains("ClientWUID");
//        return stringBuilder.toString();
    }


    public String readV2(String file_name) throws IOException {

        FileInputStream inputStream = null;
        Scanner scanner = null;
        String lines = "";

        try {
            inputStream = new FileInputStream(new File("../files/" + file_name + ".csv"));
            scanner = new Scanner(inputStream, "UTF-8");

            /* Retrieve headers from a file and put them to an array */
            List<String> headers =  Arrays.asList(scanner.nextLine().split(this.DELIMETER));

            /* Map data from headers to our models */
            lines = mapInputToModels(headers);

            /* Get the rest of data line by line */
            while (scanner.hasNext()) {
                lines = scanner.nextLine();
            }

        } finally {

            if(inputStream != null) { inputStream.close(); }
            if(scanner != null) { scanner.close(); }
        }

        return lines;
    }

    private String mapInputToModels(List<String> data_array) {
        for (String element: data_array) {

            System.out.println(element);

            switch (element) {
                case "\"Select\"": System.out.println(element); break;
                case "\"Organisation\"": System.out.println(element);
                case "\"Unique Work Unit Code\"": System.out.println(element);
                case "\"Orgn Code\"": System.out.println(element);
                case "\"ClientWUID\"": System.out.println(element);
                case "\"Cell1\"": System.out.println(element);
                case "\"Level1\"": System.out.println(element);
                case "\"Level2\"": System.out.println(element);
                case "\"Level3\"": System.out.println(element);
                case "\"Level4\"": System.out.println(element);
                case "\"Level5\"": System.out.println(element);
                case "\"Matrix1\"": System.out.println(element);
                case "\"Matrix2\"": System.out.println(element);
                case "\"Matrix3\"": System.out.println(element);
                case "\"Matrix4\"": System.out.println(element);
                case "\"Matrix5\"": System.out.println(element);
            }
        }

        return "\n\nDone";
    }
}
