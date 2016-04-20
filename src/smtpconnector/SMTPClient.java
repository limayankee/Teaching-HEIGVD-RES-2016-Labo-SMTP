/**
 *
 * @author: Madolyne Dupraz & Julien Leroy
 */

package smtpconnector;

import model.Email;
import java.io.*;
import java.net.Socket;

/**
 * The goal of the EmailSender class is to wrapp all the smpt protocole in one classe
 */
public class SMTPClient
{
    private String serverErorMsg = "Wrong Server Responce";
    private int port;
    private String host;
    private String hostName; // For the EHLO command
    private Socket socket;
    private BufferedWriter outStream;
    private BufferedReader inStream;
    private boolean isConnected;

    public SMTPClient(String host, int port, String hostName)
    {
        isConnected = false;
        this.port = port;
        this.host = host;
        this.hostName = hostName;
    }

    public void write(String s)
    {
        try
        {
            outStream.write(s + "\r\n");
            outStream.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String read()
    {
        try
        {
            return inStream.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void connect()
    {
        try
        {
            socket = new Socket(host, port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String str = read();
        System.out.println(str);

        if (!str.contains("220 "))
        {
            throw new RuntimeException(serverErorMsg);
        }

        write("EHLO " + hostName);

        while (true)
        {
            str = read();
            System.out.println(str);

            if (str.contains("250 "))
            {
                break;
            }
        }
        isConnected = true;
    }

    public void close()
    {
        write("quit\r\n");

        String str = read();
        System.out.println(str);
        if (!str.contains("221 "))
            throw new RuntimeException(serverErorMsg);


        try
        {
            outStream.close();
            inStream.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        isConnected = false;
    }

    public void sendMail(Email email)
    {
        if (!isConnected)
            throw new RuntimeException("SMTP server not connected");

        write("MAIL From: " + email.sender());

        String str = read();
        System.out.println(str);


        if (!str.contains("250 "))
        {
            throw new RuntimeException(serverErorMsg);
        }

        for (String s : email.dest())
        {
            write("RCPT To: " + s);
            str = read();
            System.out.println(str);
            if (!str.contains("250 "))
            {
                throw new RuntimeException(serverErorMsg);
            }
        }

        write("DATA");
        str = read();
        System.out.println(str);
        if (!str.contains("354 "))
        {
            throw new RuntimeException(serverErorMsg);
        }

        write("From: " + email.sender());

        String to =  "";
        for (int i = 0; i < email.dest().length; ++i)
        {
            to += email.dest()[i];
            if (i != email.dest().length - 1)
            {
                to += ",";
            }
        }

        write("To: " + to);

        write("Subject: " + email.object());

        write("");

        write(email.data());

        write("");

        write(".");

        str = read();
        System.out.println(str);
        if (!str.contains("250 "))
        {
            throw new RuntimeException(serverErorMsg);
        }
    }

}
