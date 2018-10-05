package com.example.marta.testobserverv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("group")
    @Expose
    private int group;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String Email;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sent_request")
    @Expose
    private boolean sendRequest;
    @SerializedName("password")
    @Expose
    private String password;
    private String token;
    private String groupName;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getGroup() {
        return group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSendRequest() {
        return sendRequest;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getGroupName() {
        return groupName;
    }



    public static class BuilderUser {

        private int id = 0;
        private String username = null;
        private int group = 0;
        private String firstName = null;
        private String lastName = null;
        private String Email = null;
        private String status = null;
        private boolean sendRequest = false;
        private String password = null;
        private String token = null;
        private String groupName = null;

        public BuilderUser id(int id){
            this.id=id;
            return this;
        }
        public BuilderUser username(String username){
            this.username=username;
            return this;
        }
        public BuilderUser group(int group){
            this.id=id;
            return this;
        }
        public BuilderUser firstName(String firstName){
            this.firstName=firstName;
            return this;
        }
        public BuilderUser lastName(String lastName){
            this.lastName=lastName;
            return this;
        }
        public BuilderUser Email(String Email){
            this.Email=Email;
            return this;
        }
        public BuilderUser status(String status){
            this.status=status;
            return this;
        }
        public BuilderUser sendRequest(boolean sendRequest){
            this.sendRequest=sendRequest;
            return this;
        }
        public BuilderUser password(String password){
            this.password=password;
            return this;
        }
        public BuilderUser token(String token){
            this.token=token;
            return this;
        }
        public BuilderUser groupName(String groupName){
            this.groupName=groupName;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
    private User(BuilderUser builder){
        this.id = builder.id;
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.Email = builder.Email;
        this.group = builder.group;
        this.groupName = builder.groupName;
        this.password = builder.password;
        this.sendRequest = builder.sendRequest;
        this.status = builder.status;
        this.token = builder.token;
    }
}
