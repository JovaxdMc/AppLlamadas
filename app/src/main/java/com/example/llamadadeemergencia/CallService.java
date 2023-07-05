package com.example.llamadadeemergencia;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

public class CallService extends IntentService {

    public CallService() {
        super("CallService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String phoneNumber = intent.getStringExtra("phone_number");

        if (phoneNumber != null) {
            Toast.makeText(this, "Llamada de emergencia iniciada", Toast.LENGTH_SHORT).show();

            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                    // Manejar caso de permisos de servicio en primer plano no concedidos
                    return;
                }
                startForegroundService(callIntent);
            } else {
                startService(callIntent);
            }
        }
    }
}
