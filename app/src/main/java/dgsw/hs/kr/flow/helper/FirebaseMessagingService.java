package dgsw.hs.kr.flow.helper;

/**
 * Created by Administrator on 2018-06-19.
 */

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.activity.LoginActivity;
import dgsw.hs.kr.flow.activity.MainActivity;
import dgsw.hs.kr.flow.activity.NoticeActivity;
import dgsw.hs.kr.flow.activity.OutGoActivity;
import dgsw.hs.kr.flow.activity.OutgoDocListActivity;
import dgsw.hs.kr.flow.activity.OutsleepDocListActivity;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
//    final Context context = getApplicationContext();
    private int go_out_idx;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> Info = am.getRunningTasks(1);
        ComponentName topActivity = Info.get(0).topActivity;
        String topactivityname = topActivity.getClassName();

        if(topactivityname.equals("dgsw.hs.kr.flow.activity.LoginActivity")){
            //아무것도안함
        }else{

            if(remoteMessage.getData().get("type") == "go_out"){
                go_out_idx = Integer.parseInt( remoteMessage.getData().get("go_out_idx") );
            }

            sendNotification(remoteMessage.getData().get("type"),remoteMessage.getNotification().getBody());
        }


    }

    private void sendNotification(String type, String messageBody) {
        Intent intent = null;
        switch (type) {
            case "go_out":
                DatabaseOpenHelper helper = new DatabaseOpenHelper(this, DatabaseOpenHelper.outGoTableName, null, 1);
                SQLiteDatabase database = helper.getWritableDatabase();
                helper.acceptOutgo(database, go_out_idx);
                System.out.println("외출승인");

                intent = new Intent(this, OutgoDocListActivity.class);
                break;
            case "sleep_out":
                intent = new Intent(this, OutsleepDocListActivity.class);
                break;
            case "notice":
                intent = new Intent(this, NoticeActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Push Test")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}





