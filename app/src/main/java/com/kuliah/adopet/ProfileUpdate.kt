package com.kuliah.adopet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_profile_update.*
import android.location.Geocoder
import android.util.Log
import com.kuliah.adopet.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_pet_crud.*
import kotlinx.android.synthetic.main.activity_pet_detail.*
import kotlinx.android.synthetic.main.activity_profile_update.backBtn
import kotlinx.android.synthetic.main.activity_profile_update.cancel_button
import java.util.*

class ProfileUpdate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val accID = databaseHandler.checkAccount()

        uname.setText(databaseHandler.getUname(accID))
        password.setText(databaseHandler.getPass(accID))
        phone.setText(databaseHandler.getPhone(accID))

        updateBtn.setOnClickListener {
            val uname = uname.text.toString()
            val password = password.text.toString()
            val phone = phone.text.toString()
            if (uname.trim().isNotEmpty() && password.trim().isNotEmpty() && phone.trim().isNotEmpty()) {
                if (databaseHandler.updateAccount(accID, password, uname, phone)) {
                    Toast.makeText(this, "Sukses update profil", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error, harap coba lagi", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
            }
        }
        cancel_button.setOnClickListener {
            finish()
        }
        backBtn.setOnClickListener {
            finish()
        }
    }
}