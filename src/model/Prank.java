package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class corresponds to a prank in the file, each prank got an object and message/data.
 * @author Madolyne Dupraz & Julien Leroy
 */
public class Prank
{
    private String object;
    private String prank;

    public Prank(String object, String prank)
    {
        this.object = object;
        this.prank = prank;
    }

    /**
     * @return object
     */
    public String object() { return object; }

    /**
     * @return prank data
     */
    public String prank() { return prank; }

    /**
     * This method read from the file and make a pranks table
     * @param file file to read from
     * @return A table with the pranks
     * @throws IOException problem when reading from the file
     */
    public static List<Prank> readFromFile(BufferedReader file) throws IOException
    {
        List<Prank> pranks = new ArrayList<>();

        if (!(file.readLine().equals("##EMAIL")))
            throw new RuntimeException("Not good file format");

        String prank = "";
        String title = "";

        while (true)
        {
            String tmp = file.readLine();

            if (tmp == null)
                break;

            if (tmp.contains("object= "))
            {
                title = tmp.substring(8);
                continue;
            }

            if (tmp.equals("##EMAIL"))
            {
                pranks.add(new Prank(title, prank));
                prank = ""; title = "";
            }
            prank += tmp + "\n";
        }
        return pranks;
    }
}
