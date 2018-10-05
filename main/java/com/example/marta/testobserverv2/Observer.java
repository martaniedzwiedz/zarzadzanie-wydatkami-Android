package com.example.marta.testobserverv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Observer {

    Subject mUserDataRepository = null ;
    void onUserDataChanged(User user);
    void onListChange(List<HashMap<String, String>> list);
    void onTransactionDataChanged(Transaction transaction);
    void onSumChange(SumCategory sum);
    void onCategoryDataChanged(ArrayList<Category> category);
}
