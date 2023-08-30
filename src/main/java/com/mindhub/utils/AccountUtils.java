package com.mindhub.utils;

public final class AccountUtils {
    public static int getRandomNumberAccount (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
