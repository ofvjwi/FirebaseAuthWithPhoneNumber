package com.example.firebaselesson1extra.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaselesson1extra.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var phoneNumberEditText: EditText
    private lateinit var smsCodeEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var enterButton: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var verificationCodeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

    }

    private fun initViews() {

        firebaseAuth = FirebaseAuth.getInstance()

        phoneNumberEditText = findViewById(R.id.phone_number)
        smsCodeEditText = findViewById(R.id.sms_code)
        loginButton = findViewById(R.id.login_btn)
        enterButton = findViewById(R.id.enter_btn)

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {}
            override fun onVerificationFailed(p0: FirebaseException) {}
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                verificationCodeId = p0
            }
        }

        enterButton.setOnClickListener {
            val number = phoneNumberEditText.text.toString()
            sendVerificationCode(number, callback)
        }

        loginButton.setOnClickListener {
            verifyCode(smsCodeEditText.text.toString())
        }
    }

    // send sms code to this number
    private fun sendVerificationCode(number: String, callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // these two functions work when sms code will come
    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationCodeId, code)
        signInWithCredential(credential)
    }
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                toast("Successfully")
            } else {
                toast("Failed")
            }
        }
    }


    private fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


