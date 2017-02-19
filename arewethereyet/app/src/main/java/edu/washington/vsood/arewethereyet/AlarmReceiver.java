package edu.washington.vsood.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by vishesh on 2/18/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("number") + ": " + intent.getStringExtra("message");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
