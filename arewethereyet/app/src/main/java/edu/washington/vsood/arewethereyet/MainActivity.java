package edu.washington.vsood.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        final Button start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start.getText().equals("Start")) {
                    String message = getMessage();
                    String number = getPhone();
                    String minutes = ((EditText) findViewById(R.id.minutes)).getEditableText().toString();
                    int minutesApart;
                    try {
                        minutesApart = Integer.parseInt(minutes);
                    } catch (NumberFormatException nfe) {
                        minutesApart = 0;
                    }
                    if (minutesApart > 0 && !message.isEmpty() && !number.isEmpty()
                            && number.matches("[(]\\d{3}[)][ ]\\d{3}-\\d{4}")) {
                        alarmIntent.putExtra("message", message);
                        alarmIntent.putExtra("number", number);
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                        start.setText("Stop");
                        startAlarm(minutesApart);

                    } else {
                        if (message.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Message cannot be empty.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (minutesApart <= 0) {
                            Toast.makeText(getApplicationContext(), "Minutes must be greater than 0.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        if (number.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Phone number cannot be empty.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        if (!number.matches("[(]\\d{3}[)][ ]\\d{3}-\\d{4}")) {
                            Toast.makeText(getApplicationContext(), "Phone number format must be (555) 555-5555",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    start.setText("Start");
                    cancelAlarm();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAlarm(int minutesApart) {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long interval = minutesApart * 60 * 1000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
        }

    }

    public String getMessage() {
        return ((EditText) findViewById(R.id.message)).getEditableText().toString();
    }

    public String getPhone() {
        return ((EditText) findViewById(R.id.phoneNumber)).getEditableText().toString();
    }
}
