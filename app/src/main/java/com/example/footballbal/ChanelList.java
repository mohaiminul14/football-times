package com.example.footballbal;

public class ChanelList {

    private  String Name;
    private String link;


    public ChanelList() {
    }

    public ChanelList(String name, String link) {
        Name = name;
        this.link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
