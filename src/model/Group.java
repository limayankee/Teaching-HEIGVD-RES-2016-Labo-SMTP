package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represent a group, each group has a master and slaves.
 * @author: Madolyne Dupraz & Julien Leroy
 */
public class Group {

    private String master = null;
    private ArrayList<String> slaves = new ArrayList<>();


    /**
     * When the group as no master, it's add the person to master.
     * And when he has one it add to the slaves
     * @param person the preson to add to the group
     */
    public void add(String person)
    {
        if (master == null)
            master = person;
        else
            addSlave(person);

    }


    /**
     * Add a person to the slaves
     * @param slave person to add to the slaves
     */
    public void addSlave(String slave)
    {
        slaves.add(slave);
    }


    /**
     * @return the master of the group
     */
    public String master()
    {
        return master;
    }


    /**
     * @return a table with the slaves
     */
    public String[] slaves()
    {
        return slaves.toArray(new String[slaves.size()]);
    }


    /**
     * This method generate "nbGroups" number of groups with the string in the personList
     * A person from the list can only be in one group.
     * It's shuffle the personList first
     * @param nbGroups number of groups to generates
     * @param personList List of String tu use
     * @return a table with the generated groups
     */
    public static Group[] generateGroups(int nbGroups, List<String> personList)
    {
        Collections.shuffle(personList);
        Group groups[] = new Group[nbGroups];

        for (int k = 0; k < nbGroups; ++k)
        {
            groups[k] = new Group();
        }

        for (int k = 0; k < personList.size(); ++k)
        {
            groups[k % nbGroups].add(personList.get(k));
        }

        return groups;
    }

}
