package com.kvalifika.javaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kvalifika.sdk.KvalifikaSDK;
import com.kvalifika.sdk.KvalifikaSDKCallback;
import com.kvalifika.sdk.KvalifikaSDKError;
import com.kvalifika.sdk.KvalifikaSDKLocale;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private KvalifikaSDK sdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appId = "YOU APP ID";
        sdk = new KvalifikaSDK.Builder(this, appId)
                .locale(KvalifikaSDKLocale.GE)
                .development(true)
                .build();

        sdk.callback(new KvalifikaSDKCallback() {
            @Override
            public void onInitialize() {
                Log.d("MainActivity", "initialized");
            }

            @Override
            public void onStart(@NotNull String sessionId) {
                Log.d("MainActivity", "started");
            }

            @Override
            public void onFinish(@NotNull String sessionId) {
                Log.d("MainActivity", "finished");

                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Verification finished with session id "+sessionId, Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onError(@NotNull KvalifikaSDKError error, String message) {
                if (error == KvalifikaSDKError.INVALID_APP_ID) {
                    Log.d("MainActivity", "Invalid App ID");
                }

                if (error == KvalifikaSDKError.USER_CANCELLED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "User cancelled", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.TIMEOUT) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Timeout", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.SESSION_UNSUCCESSFUL) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Session failed", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.ID_UNSUCCESSFUL) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "ID scan failed", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.CAMERA_PERMISSION_DENIED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Camera permission denied", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.LANDSCAPE_MODE_NOT_ALLOWED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Landscape mode is not allowed", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.REVERSE_PORTRAIT_NOT_ALLOWED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Reverse portrait is not allowed", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.FACE_IMAGES_UPLOAD_FAILED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Could not upload face images", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.DOCUMENT_IMAGES_UPLOAD_FAILED) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Could not upload Id card or passport images", Toast.LENGTH_LONG).show());
                }

                if (error == KvalifikaSDKError.NO_MORE_ATTEMPTS) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    });
                }

                if (error == KvalifikaSDKError.UNKNOWN_INTERNAL_ERROR) {
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Unknown error happened", Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    public void onVerificationPress(View view) {
        sdk.startSession();
    }
}