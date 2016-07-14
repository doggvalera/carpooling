package lv.ctco.controllers;

import lv.ctco.entities.User;

public class StandartBuilder {
    private static int mailid = 0;

    public static User buildUser() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("mail" + mailid++);

        return user;
    }
}
