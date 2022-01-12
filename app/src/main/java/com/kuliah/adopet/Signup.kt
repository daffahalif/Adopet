package com.kuliah.adopet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kuliah.adopet.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val databaseHandler = DatabaseHandler(this)

        signupBtn.setOnClickListener {
            if (emailForm.text.toString().trim().isNotEmpty() && passForm.text.toString().trim().isNotEmpty() && unameForm.text.toString().trim().isNotEmpty()) {
                val status = databaseHandler.createAccount(emailForm.text.toString(), passForm.text.toString(), unameForm.text.toString())
                Log.d("CREATION", "Create account mail,pass,uname - " + emailForm.text.toString() + passForm.text.toString() + unameForm.text.toString())
                if (status) {
                    Toast.makeText(this, "Sukses mendaftar", Toast.LENGTH_LONG).show();
                    finish()
                } else {
                    Toast.makeText(this, "Username/email sudah dipakai", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
            }
        }

        loginBtn.setOnClickListener{
            finish()
        }
    }
}