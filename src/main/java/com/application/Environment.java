package com.application;

public class Environment {
    public enum State {
        MidSemester,
        Vacation,
        Registering
    }

    public State state;
    public boolean loadDefaults;
}
