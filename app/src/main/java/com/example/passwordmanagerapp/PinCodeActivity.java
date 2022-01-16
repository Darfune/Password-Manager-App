package com.example.passwordmanagerapp;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class PinCodeActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private TextView pinCodeTextView;
    private EditText editTextNumberPassword1;
    private EditText editTextNumberPassword2;
    private EditText editTextNumberPassword3;
    private EditText editTextNumberPassword4;
    private Button enterButton;
    private Boolean reEnter = false;
    private String digits;
    private String reDigits;
    private SharePrefEncryption pinCode;
    private static final String filename = "SecureDataFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        pinCodeTextView = findViewById(R.id.pinCodeTextView);
        editTextNumberPassword1 = findViewById(R.id.editTextNumberPassword1);
        editTextNumberPassword2 = findViewById(R.id.editTextNumberPassword2);
        editTextNumberPassword3 = findViewById(R.id.editTextNumberPassword3);
        editTextNumberPassword4 = findViewById(R.id.editTextNumberPassword4);
        enterButton = findViewById(R.id.enterButton);

        pinCode = new SharePrefEncryption(this,filename);
        String pin = pinCode.fetchPin();
        Toast.makeText(this,pin,Toast.LENGTH_SHORT).show();

        if (pin.isEmpty()){
            newPinCode();
        }
        else{
            getPinCode(pin);
        }

    }

    private void fingerprintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to verify your identity with your fingerprint?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pinCode.biometricsVerification(true);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        pinCode.biometricsVerification(false);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getPinCode(String pin) {

        if (pinCode.isBiometricsAllowed()){
            biometricAuth();
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                digits = String.valueOf(editTextNumberPassword1.getText())
                        + String.valueOf(editTextNumberPassword2.getText())
                        + String.valueOf(editTextNumberPassword3.getText())
                        + String.valueOf(editTextNumberPassword4.getText());
                if (digits.equals(pin)){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"the Pin Code is incorrect",Toast.LENGTH_SHORT).show();
                    editTextNumberPassword1.setText("");
                    editTextNumberPassword2.setText("");
                    editTextNumberPassword3.setText("");
                    editTextNumberPassword4.setText("");
                }
            }
        });
    }

    private void newPinCode() {

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reEnter){
                    digits = String.valueOf(editTextNumberPassword1.getText())
                            + String.valueOf(editTextNumberPassword2.getText())
                            + String.valueOf(editTextNumberPassword3.getText())
                            + String.valueOf(editTextNumberPassword4.getText());
                    editTextNumberPassword1.setText("");
                    editTextNumberPassword2.setText("");
                    editTextNumberPassword3.setText("");
                    editTextNumberPassword4.setText("");
                    pinCodeTextView.setText("Enter your Pin code again");
                    reEnter = true;
                }
                else{
                    reDigits = String.valueOf(editTextNumberPassword1.getText())
                            + String.valueOf(editTextNumberPassword2.getText())
                            + String.valueOf(editTextNumberPassword3.getText())
                            + String.valueOf(editTextNumberPassword4.getText());
                    if (reDigits.equals(digits)){
                        pinCode.addPinCode(digits);
                        fingerprintDialog();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Pin Codes don't match",Toast.LENGTH_SHORT).show();
                        editTextNumberPassword1.setText("");
                        editTextNumberPassword2.setText("");
                        editTextNumberPassword3.setText("");
                        editTextNumberPassword4.setText("");
                        pinCodeTextView.setText("Enter your Pin code");
                        reEnter= false;
                    }
                }
            }
        });

    }

    private void biometricAuth() {

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this,"Device doesn't have fingerprint",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this,"Fingerprint hardware not working",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this,"No FingerPrint Assigned",Toast.LENGTH_SHORT).show();
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent,1);
                break;
        }


        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(PinCodeActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("FingerPrint Authentication")
                .setDescription("Use FingerPrint to Login")
                .setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

    }


}