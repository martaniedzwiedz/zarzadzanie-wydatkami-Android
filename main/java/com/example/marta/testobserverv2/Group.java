package com.example.marta.testobserverv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group {

    public Group(String group_name) {
        this.group_name = group_name;
    }
    @SerializedName("id")
    @Expose
    private int Id;
    @SerializedName("group_name")
    @Expose
    private String group_name;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("username")
    @Expose
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGroupName() {
        return  group_name;
    }

    public void setGroupName(String name) {
        this. group_name =  group_name;
    }

    public String getUser() { return user;}

    public void setUser(String user) {this.user = user;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class BuilderGroup {

        private int id ;
        private String group_name ;
        private String name ;
        private String user ;
        private String username ;

        public BuilderGroup id(int id){
            this.id=id;
            return this;
        }
        public BuilderGroup group_name(String group_name){
            this.group_name=group_name;
            return this;
        }
        public BuilderGroup name(String name){
            this.name = name;
            return this;
        }
        public BuilderGroup user(String user){
            this.user=user;
            return this;
        }
        public BuilderGroup username(String username){
            this.username=username;
            return this;
        }

        public Group build(){
            return new Group(this);
        }
    }
    private Group(BuilderGroup builder){
        this.Id = builder.id;
        this.name = builder.name;
        this.group_name = builder.group_name;
        this.user = builder.user;
        this.username = builder.username;
    }
}

