package com.nep.examenandroid;

public interface AuthListener {
    void onStarted();
    void onSuccess();
    void onFailure(String message);
}
