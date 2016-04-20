package smtpclient;

import model.Email;
import java.io.*;
import java.net.Socket;

/**
 * The goal of the SMTPClient class is to wrap all the SMTP protocol in one class
 * @author Madolyne Dupraz & Julien Leroy
 */
public class SMTPClient
{
    private String serverErorMsg = "Wrong Server Responce";
    private int port;
    private String host;
    private String hostName; // For the EHLO command "EHLO + hostName"
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

    /**
     * Write the String s on the socket, ended all the lines with <CR><LF> and flush
     *
     * @param s String to write
     * @throws IOException problem when writing on the stream
     */
    private void write(String s) throws IOException
    {
        outStream.write(s + "\r\n");
        outStream.flush();
    }

    /**
     * Read on the socket and return it
     *
     * @return the read String
     * @throws IOException problem when reading on the stream
     */
    private String read() throws IOException
    {
        return inStream.readLine();
    }


    /**
     * Connect the socket and open the streams
     *
     * @throws IOException problem when opening/creating the socket and the streams
     */
    public void connect() throws IOException
    {
        socket = new Socket(host, port);
        outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String str = read();
        System.out.println(str);

        if (!str.contains("220 "))
        {
            throw new IOException(serverErorMsg);
        }

        write("EHLO " + hostName);

        // The server can respond n lines begining with "250-", so we need to loop until we got "250 "
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


    /**
     * Close the socket and the streams
     *
     * @throws IOException problem while closing the socket or streams
     */
    public void close() throws IOException
    {
        write("quit\r\n");

        String str = read();
        System.out.println(str);
        if (!str.contains("221 "))
            throw new RuntimeException(serverErorMsg);

        outStream.close();
        inStream.close();
        socket.close();

        isConnected = false;
    }


    /**
     * Send all the data of an email to the SMTP server
     *
     * @param email the email to send
     * @throws IOException thrown when there is a communication's problem
     */
    public void sendMail(Email email) throws IOException
    {
        if (!isConnected)
            throw new RuntimeException("SMTP server not connected");

        write("MAIL From: " + email.sender());

        String str = read();
        System.out.println(str);
        if (!str.contains("250 "))
        {
            throw new IOException(serverErorMsg);
        }


        for (String s : email.recipients())
        {
            write("RCPT To: " + s);
            str = read();
            System.out.println(str);
            if (!str.contains("250 "))
            {
                throw new IOException(serverErorMsg);
            }
        }

        write("DATA");
        str = read();
        System.out.println(str);
        if (!str.contains("354 "))
        {
            throw new IOException(serverErorMsg);
        }

        write("From: " + email.sender());

        String to = "";
        for (int i = 0; i < email.recipients().length; ++i)
        {
            to += email.recipients()[i];
            if (i != email.recipients().length - 1)
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
            throw new IOException(serverErorMsg);
        }
    }
}
