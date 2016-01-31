package edu.uci.stacks.easybudget.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.MainActivity;
import edu.uci.stacks.easybudget.data.BudgetConfig;

public class NotificationReminderService extends IntentService {

    private final static String CHECK_NOTIFICATIONS_ACTION = "CHECK_NOTIFICATIONS_ACTION";

    @Inject
    BudgetConfig budgetConfig;

    public NotificationReminderService() {
        this(NotificationReminderService.class.getSimpleName());
    }

    public NotificationReminderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BudgetApplication.getComponent().inject(this);
        String action = intent.getAction();
        if (CHECK_NOTIFICATIONS_ACTION.equals(action)) {
            if (budgetConfig.shouldRemindNoEntries()) {
                sendReminderNotification();
                budgetConfig.setReminded();
            }
        } else {
            budgetConfig.clearReminded();
            Intent serviceIntent = new Intent(getApplicationContext(), NotificationReminderService.class);
            serviceIntent.setAction(CHECK_NOTIFICATIONS_ACTION);
            if ((PendingIntent.getService(getApplicationContext(), 0, serviceIntent,
                    PendingIntent.FLAG_NO_CREATE) != null)) {
                AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, serviceIntent, 0);
                alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
            }
        }
    }

    private void sendReminderNotification() {
        int numDays = budgetConfig.getReminderNumDays();
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(String.format("You have not added any purchases in %s days.", numDays));

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
