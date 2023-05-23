package com.example.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalproject.Calculations.Constant;
import com.example.finalproject.DBUtils.DbUtils;
import com.example.finalproject.Entities.Voter;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SMSActivity extends AppCompatActivity {
    private EditText digit1, digit2, digit3, digit4, digit5, digit6;
    private ImageButton submitButton;
    private Random random = new Random();
    private int randomNumber;
    private String id, phoneNumber;
    private FirebaseAuth mAuth;
    private FirebaseAuthSettings firebaseAuthSettings;
    private String mVerificationId;
    private EditText[] verificationCodeFields;
    private DbUtils dbUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        findviews();
        randomNumber = random.nextInt(899999) + 100000;
        id = getIntent().getStringExtra("id");
        phoneNumber = getIntent().getStringExtra("phoneNumber");


        sendSMS(phoneNumber);
        initViews();
    }



    private void findviews() {
        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);
//        digitBoxesBehavior();
        edtxtBehavior();
        submitButton = findViewById(R.id.submitButton);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
        verificationCodeFields = new EditText[]{digit1, digit2, digit3, digit4, digit5, digit6};
        dbUtils = new DbUtils();
    }

    private void initViews() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = getCodeFromEditTexts();
                if(code.isEmpty() || code.length() < 6){
                    Toast.makeText(SMSActivity.this, "The code is not valid, please trt again", Toast.LENGTH_LONG).show();
                    digit1.requestFocus();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

        private void sendSMS(String phoneNumber) {
        Log.d("ptttttttt_phone", "" + phoneNumber);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.d("VerificationLogs:", "onCodeSent:" + verificationId);
            super.onCodeSent(verificationId, token);
            mVerificationId = verificationId;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                autoCompleteEditTexts(code);
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("VerificationLogs:", "onVerificationFailed", e);
            String msg = e.getMessage();
            if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                msg = "Something goes worng, please try again later";
            }
            // Show a message and update the UI
            Toast.makeText(SMSActivity.this, msg, Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String phoneNumber) {
            super.onCodeAutoRetrievalTimeOut(phoneNumber);
            Log.d("VerificationLogs:", "onCodeAutoRetrievalTimeOut: Phone Number: " + phoneNumber);
        }

    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dbUtils.getVoterById(Constant.DataBaseName, Constant.VotersCollection, id, (success, error) -> {
                                if(success != null){
                                    Voter voter = (Voter) success;
                                    Intent intent = new Intent(SMSActivity.this, VoteActivity.class);
                                    intent.putExtra("VoterId", voter.getVoterId());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.d("VerificationLogs:", "signInWithCredential:failure", task.getException());
                            String msg = task.getException().getMessage();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                msg = "incorrect code";
                            }
                            Toast.makeText(SMSActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void autoCompleteEditTexts(String code) {
        final Handler handler = new Handler();
        for (int i = 0; i < verificationCodeFields.length; i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    verificationCodeFields[finalI].setText(Character.toString(code.charAt(finalI)));
                }
            }, i * 1000); // delay by 1 sec between each field
        }
    }

    private String getCodeFromEditTexts() {
        String code = "";
        for (int i = 0; i < verificationCodeFields.length; i++) {
            code += verificationCodeFields[i].getText();
        }
        return code;
    }

    private void edtxtBehavior() {
        setEditTextBehavior(digit1, digit2);
        setEditTextBehavior(digit2, digit3);
        setEditTextBehavior(digit3, digit4);
        setEditTextBehavior(digit4, digit5);
        setEditTextBehavior(digit5, digit6);
        setLastEditTextBehavior(digit6);
    }

    private void setEditTextBehavior(final EditText currentDigit, final EditText nextDigit) {
        currentDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    currentDigit.clearFocus();
                    nextDigit.requestFocus();
                }
            }
        });
    }

    private void setLastEditTextBehavior(final EditText lastDigit) {
        lastDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    lastDigit.clearFocus();
                }
            }
        });
    }


//    private void digitBoxesBehavior() {
//        digit6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit6.clearFocus();
//                    digit5.requestFocus();
//                }
//            }
//        });
//        digit5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit5.clearFocus();
//                    digit4.requestFocus();
//                }
//            }
//        });
//        digit4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit4.clearFocus();
//                    digit3.requestFocus();
//                }
//            }
//        });
//        digit3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit3.clearFocus();
//                    digit2.requestFocus();
//                }
//            }
//        });
//        digit2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit2.clearFocus();
//                    digit1.requestFocus();
//                }
//            }
//        });
//        digit1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 1) {
//                    digit1.clearFocus();
//                }
//            }
//        });
//
//    }
}
