package com.example.marta.testobserverv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

   public Transaction( boolean constant){
        this.constant = constant;
    }

    private int group;
    private int id;
    private String CategoryName;
    private int category;
    private String title;
    private String description;
    private double value;
    private String created;
    private boolean constant;
    private int user;
    private String userName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

   public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }


    public static class BuilderTransaction{

        private int id;
        private String title;
        private String description;
        private String created;
        private boolean constant;
        private double value;
        private int user;
        private int group;
        private int category;
        private String categoryName;
        private String userName;
        public BuilderTransaction id(int id){
            this.id =id;
            return this;
        }
        public BuilderTransaction Title(String title){
            this.title =title;
            return this;
        }
        public BuilderTransaction Description(String description){
            this.description =description;
            return this;
        }
        public BuilderTransaction Created(String created){
            this.created =created;
            return this;
        }
        public BuilderTransaction Constant(Boolean constant){
            this.constant =constant;
            return this;
        }
        public BuilderTransaction Value(double value){
            this.value =value;
            return this;
        }
        public BuilderTransaction User(int user){
            this.user =user;
            return this;
        }
        public BuilderTransaction Group(int group){
            this.group =group;
            return this;
        }
        public BuilderTransaction Category(int category){
            this.category =category;
            return this;
        }
        public BuilderTransaction CategoryName(String categoryName){
            this.categoryName =categoryName;
            return this;
        }
        public BuilderTransaction UserName(String userName){
            this.userName = userName;
            return this;
        }
        public Transaction build(){
            return new Transaction(this);
        }
    }
    private Transaction(BuilderTransaction builder){
        this.title =  builder.title;
        this.description = builder.description;
        this.created = builder.created;
        this.constant = builder.constant;
        this.value = builder.value;
        this.user = builder.user;
        this.group = builder.group;
        this.category = builder.category;
    }


}
