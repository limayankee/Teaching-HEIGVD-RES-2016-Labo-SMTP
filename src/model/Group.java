package model;

import java.util.ArrayList;

/**
 * Created by julien on 17.04.16.
 */
public class Group {

    private String sender = null;
    private ArrayList<String> victims = new ArrayList<>();

    public void add(String preson)
    {

        if (sender == null)
            sender = preson;
        else
            addVictim(preson);

    }

    public void addVictim(String victim)
    {
        victims.add(victim);
    }

    public String getSender()
    {
        return sender;
    }

    public String[] getDest()
    {
        return victims.toArray(new String[victims.size()]);
    }

}
