package com.Maxim.view;

import com.Maxim.model.User;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserView {

    public void getUsers(List<User> users, HttpServletResponse response){
        Gson gson = new Gson();
    }
}
