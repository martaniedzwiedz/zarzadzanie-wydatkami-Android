package com.example.marta.testobserverv2;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ControllerCall {
    private SharedPreference mApplicationPreference = SharedPreference.getInstance();
    private Call<ResponseBody> call;
    private Call<User> userCall;
    private Call<Transaction> transactionCall;
    private Call<Group>groupCall;
    private User user;
    private Group group;
    private Transaction transaction;
    private Builder retrofibuilder = new Builder();


    public void setDeleteUser(int userId){
        call = retrofibuilder.userClient.removeUsersFromGroup(mApplicationPreference.getToken(), mApplicationPreference.getGroupID(), userId);
    }
    public void setDeleteTransaction(int expensesId){
        call = retrofibuilder.userClient.removeTransaction(mApplicationPreference.getToken(), mApplicationPreference.getGroupID(), expensesId );
    }
    public void setUsersList(){
        call = retrofibuilder.userClient.getUsersList(mApplicationPreference.getToken(), mApplicationPreference.getGroupID());
    }
    public void setSum(){
        call = retrofibuilder.userClient.getSum(mApplicationPreference.getToken(), mApplicationPreference.getGroupID());
    }
    public void setTransactionsList(){
        call = retrofibuilder.userClient.getGroupTransaction(mApplicationPreference.getToken(), mApplicationPreference.getGroupID());
     }
    public void setCategoriesList(){
        call = retrofibuilder.userClient.getCategories(mApplicationPreference.getToken());
          }
    public void setRequestsList(){
        call = retrofibuilder.userClient.getGroupRequests(mApplicationPreference.getToken(), mApplicationPreference.getGroupID() );
    }
    public void setUser(int userId){
        call = retrofibuilder.userClient.getSelectedUserInformation(mApplicationPreference.getToken(), userId);

    }
    public void setTransaction(int expensesId){
        call = retrofibuilder.userClient.getTransactionDetail(mApplicationPreference.getToken(), mApplicationPreference.getGroupID(), expensesId);

    }
    public void setMe(){
        call = retrofibuilder.userClient.getUserInformation(mApplicationPreference.getToken());
        }
    public void setUser(String username, String password) {
        user = new User.BuilderUser()
                .username(username)
                .password(password)
                .build();
        userCall = retrofibuilder.userClient.login(user);
    }
    public void setUser(String username, String password, String first_name, String last_name, String email) {
        user = new User.BuilderUser()
                .firstName(first_name)
                .lastName(last_name)
                .Email(email)
                .username(username)
                .password(password)
                .build();
        userCall = retrofibuilder.userClient.register(user);
    }
    public void setTransaction(String title, String description, String created, int category, double value, boolean constant) {
          transaction = new Transaction.BuilderTransaction()
                        .Description(description)
                        .Title(title)
                        .Created(created)
                        .Value(value)
                        .Constant(constant)
                        .User(mApplicationPreference.getUserID())
                        .Group(mApplicationPreference.getGroupID())
                        .Category(category)
                        .build();
        transactionCall = retrofibuilder.userClient.postGroupTransaction(mApplicationPreference.getToken(),transaction, mApplicationPreference.getGroupID());
    }
    public void SetDeclineRequest(int reqId){
        call = retrofibuilder.userClient.declineRequest(mApplicationPreference.getToken(), mApplicationPreference.getGroupID(), reqId);
    }
    public void SetAcceptRequest(int reqId){
        call = retrofibuilder.userClient.acceptRequest(mApplicationPreference.getToken(), mApplicationPreference.getGroupID(), reqId);
    }
    public void SetGroupJoin(String GroupName){
            group = new Group.BuilderGroup()
                .group_name(GroupName)
                .build();
        groupCall = retrofibuilder.userClient.sendRequest(mApplicationPreference.getToken(), group);
    }
    public void SetCreateGroup(String GroupName){
        group = new Group.BuilderGroup()
                .name(GroupName)
                .build();
        groupCall = retrofibuilder.userClient.createGroup(mApplicationPreference.getToken(), group);
    }
    public void SetAddToGroup(String User){
        group = new Group.BuilderGroup()
                .username(User)
                .build();
        groupCall = retrofibuilder.userClient.addUserToGroup(mApplicationPreference.getToken(), group, mApplicationPreference.getGroupID() );
    }
    public void setEditMe(String username, String first_name, String last_name, String email){
        user = new User.BuilderUser()
                .username(username)
                .firstName(first_name)
                .lastName(last_name)
                .Email(email)
                .build();
        userCall = retrofibuilder.userClient.editMe(mApplicationPreference.getToken(), user, mApplicationPreference.getUserID() );
    }
    public void setEditTrans(boolean constance, int id){
        ConstantTransactionField transField = new ConstantTransactionField(false);
         call = retrofibuilder.userClient.changeConstantTransaction(mApplicationPreference.getToken(), transField, mApplicationPreference.getGroupID(),Integer.valueOf(id));
    }
    public Call<ResponseBody> getCall(){
        return call;
    }
    public Call<User> getUserCall(){
        return userCall;
    }
    public Call<Transaction> getTransactionCall(){return transactionCall;}
    public Call<Group> getGroupCall() {return groupCall;}
}
