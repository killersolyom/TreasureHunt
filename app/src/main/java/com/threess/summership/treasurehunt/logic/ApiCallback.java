package com.threess.summership.treasurehunt.logic;

public interface ApiCallback<T> {

    void Call(T object);
    void Error(int errorCode);

}