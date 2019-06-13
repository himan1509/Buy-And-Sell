package ems.erp.mmdu.com.forfirebasestorage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map map = remoteMessage.getData();
        showNotification(map);

    }

    public void showNotification(Map<String,String> payload) {

        Intent resultIntent = new Intent(this, homepage.class);
        resultIntent.putExtra("fragment","bids");
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.bids)
                .setContentTitle(payload.get("username"))
                .setContentText(payload.get("email"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .build();

        notificationManager.notify(1,notification);
    }
//    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//        FirebaseUser  mUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore mFirebase = FirebaseFirestore.getInstance();
//        DocumentReference notebookRef = mFirebase.collection("Users").document(mUser.getUid());
//
//        notebookRef.update("userToken",s);
//    }
}
