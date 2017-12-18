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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", faction=" + faction +
                '}';
    }

}
