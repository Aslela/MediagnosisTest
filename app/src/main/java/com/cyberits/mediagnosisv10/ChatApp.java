package com.cyberits.mediagnosisv10;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by w7 on 25/11/2015.
 */
public class ChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "mtjIFRH1Hq4JRCW4cb36XjX2xDK4W3w1u962uEck", "AsigPsdCAwOmyhchd0WszHHFnK4qAtFeUrLMIxuA");
    }
}
