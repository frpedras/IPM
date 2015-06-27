package iscte.ipm.ipm;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Filipe on 28/05/2015.
 */
public class Alert {
    String name;
    int value;

    Alert(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
