package com.application;

import java.io.Serializable;

public class Environment implements Serializable {
    public enum State implements Serializable {
        MidSemester,
        Vacation,
        Registering
    }

    public State state;
    public boolean loadDefaults;
    public long lastUserId;
    public int lastGroupId;
}
