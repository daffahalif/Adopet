package com.kuliah.adopet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.kuliah.adopet.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val databaseHandler = DatabaseHandler(this)

        loginBtn.setOnClickListener {
            val status = databaseHandler.loginAccount(emailForm.text.toString(), passForm.text.toString())
            if (status) {
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(getApplicationContext(), "Sukses login", Toast.LENGTH_SHORT).show();
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(getApplicationContext(), "Pastikan username dan password anda benar", Toast.LENGTH_SHORT).show();
            }
        }

        signupBtn.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}