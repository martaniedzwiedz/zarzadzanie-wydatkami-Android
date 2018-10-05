package com.example.marta.testobserverv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private int Id;
    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static class BuilderCategory {
        private int Id = 0;
        private String name= null;
        public BuilderCategory id(int id){
            this.Id=id;
            return this;
        }
        public BuilderCategory Name(String name){
            this.name = name;
            return this;
        }
        public Category build(){
            return new Category(this);
        }
    }
    private Category(BuilderCategory builder){
        this.Id = builder.Id;
        this.name =  builder.name;

    }
}
