package com.example.marta.testobserverv2;

import android.content.DialogInterface;

public interface Subject {
    void registerObserver(Observer repositoryObserver);

    void removeObserver(Observer repositoryObserver);
    void notifyObservers();
    void notifyCategoryObserver();
    void get();
    void get(int ID);
    void post(String username, String password);
    void post(String title, String description, String created, int category, double value, boolean constant);
    void post(String username, String password, String first_name, String last_name, String email, boolean flag);
    void delete(int Id);

}