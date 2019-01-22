package com.red;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class Command implements Serializable {

    public String name;
    public String description;
    public String commmandKey;
    public boolean common;

    public Command() {
    }

    public void trigger() {
    }

    public void trigger(String[] args) {
    }

}
