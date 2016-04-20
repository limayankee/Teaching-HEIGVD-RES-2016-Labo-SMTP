

package model;

/**
 * This calss is a representation of an email, it has a sender, a destionators table, an object and the message/data
 * @author Madolyne Dupraz & Julien Leroy
 */
public class Email
{
    private String sender;
    private String[] recipients;
    private String object;
    private String data;

    public Email(String sender, String[] recipients, String object, String data)
    {
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.data = data;
    }

    /**
     * @return render
     */
    public String sender()
    {
        return sender;
    }

    /**
     * @return dest table
     */
    public String[] recipients() { return recipients; }

    /**
     * @return object
     */
    public String object() { return object; }


    /**
     * @return data
     */
    public String data() { return data; }

}
