package com.hfad.joke;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";

    private Handler handler;

    public DelayedMessageService() {
        super("DelayedMessageServise");
    }

    // выглядит как грязный хак? "You should not override this method for your IntentService"
    // метод выполняется в главном потоке
    // создадим Handler в главном потоке, чтобы можно было обновить интерфейс в фоновом потоке
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    // выполняется в фоновом потоке
    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }

    private void showText(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
