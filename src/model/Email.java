package model;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

/**
 * Created by julien on 18.04.16.
 */
public class Email
{
    private String sender;
    private String[] dest;
    private String object;
    private String data;

    public Email(String sender, String[] dest, String object, String data)
    {
        this.sender = sender;
        this.dest = dest;
        this.object = object;
        this.data = data;
    }


    public String sender()
    {
        return sender;
    }

    public String[] dest() { return dest; }

    public String object() { return object; }

    public String data() { return data; }

}
