package ru.graduation.voting.controller;

import ru.graduation.voting.model.AbstractEntity;

public class SecurityUtil {

    private static int id = AbstractEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

}