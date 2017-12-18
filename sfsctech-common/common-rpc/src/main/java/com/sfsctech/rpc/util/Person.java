package com.sfsctech.rpc.util;

import java.io.Serializable;

/**
 * Class Person
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public class Person implements Serializable {

    private String name;
    private Faction faction;

    public Person() {
    }

    public Person(String name, Faction faction) {
        this.name = name;
        this.faction = faction;

    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
