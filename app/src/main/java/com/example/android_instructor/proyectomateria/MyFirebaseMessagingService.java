package com.example.android_instructor.proyectomateria;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Android-Instructor on 4/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String LOG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("Mensaje recibido",remoteMessage.getData().toString());
    }
}
