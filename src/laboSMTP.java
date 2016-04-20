import model.Email;
import model.Group;
import model.Prank;

import smtpclient.SMTPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This class is the main class of the project.
 * This class read the email addresses (2 arg argv[1]) and the pranks (3 arg, argv[2]) from the files
 * With the email addresses it generates groups with one sender and minimum 2 recipients. The number of groups is the first
 * arg of the application.
 * The host address is the argv[3], the port number is the argv[4] and the domain for the EHLO is argv[5]
 *
 * @author Madolyne Dupraz & Julien Leroy
 */
public class LaboSMTP
{
    private int nbGroups;

    private List<String> emailAddrs = new ArrayList<>(); // Table with the email addresses read from the file

    private List<Prank> pranks = new ArrayList<>(); // Table with all the pranks

    private Email emails[]; // Table with email's content: sender, recipients, object and prank

    private Group groups[]; // Table with the groups

    private LaboSMTP(int nbGroups)
    {
        this.nbGroups = nbGroups;
    }


    /**
     * This method read the email addresses from the file
     * @param file file to read from
     * @throws IOException problem with the reading
     */
    private void readEmailAddresses(BufferedReader file) throws IOException
    {
        String tmp;
        while (true)
        {
            tmp = file.readLine();

            if (tmp == null) // In the case we reach the end of the file
                break;

            if (tmp.equals("")) // Ignore all the blank lines
                continue;

            emailAddrs.add(tmp);
        }
        checkGroupsNb();
    }


    /**
     * This method checks if there is enough email addresses for the number of groups
     */
    private void checkGroupsNb()
    {
        if (nbGroups * 3 > emailAddrs.size())
            throw new RuntimeException("Too many groups for the number of emails, you need minumum (nbGroups * 3) emails");
    }


    /**
     * This method read the pranks from the file
     * @param file file to read from
     * @throws IOException problem with the reading
     */
    private void readPrank(BufferedReader file) throws IOException
    {
        pranks = Prank.readFromFile(file);
    }


    /**
     * This method makes emails with groups as sender and receivers and the pranks as object and data
     */
    private void makeEmails()
    {
        emails = new Email[nbGroups];

        Random rand = new Random(1);

        int index = rand.nextInt(pranks.size());

        for (int i = 0; i < nbGroups; ++i)
            emails[i] = new Email(groups[i].master(), groups[i].slaves(), pranks.get(index).object(), pranks.get(index).prank());
    }


    /**
     * This method send all the emails
     * @param sender the SMTP client to use
     * @throws IOException problem with the SMTP client server communication
     */
    private void sendEmails(SMTPClient sender) throws IOException
    {
        for (Email e : emails)
            sender.sendMail(e);
    }


    /**
     * The method call the groups generator.
     */
    private void generateGroups()
    {
        groups = Group.generateGroups(nbGroups, emailAddrs);
    }


    public static void main(String argv[]) throws IOException
    {
        LaboSMTP laboSMTP = new LaboSMTP(Integer.parseInt(argv[0]));

        BufferedReader file = new BufferedReader(new java.io.FileReader(argv[1]));
        laboSMTP.readEmailAddresses(file);
        file.close();

        file = new BufferedReader(new java.io.FileReader(argv[2]));
        laboSMTP.readPrank(file);
        file.close();

        laboSMTP.generateGroups();

        laboSMTP.makeEmails();

        SMTPClient client = new SMTPClient(argv[3], Integer.parseInt(argv[4]), argv[5]);

        client.connect();

        laboSMTP.sendEmails(client);

        client.close();
    }
}
