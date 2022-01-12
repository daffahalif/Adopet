package com.kuliah.adopet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kuliah.adopet.database.DatabaseHandler
import com.kuliah.adopet.databinding.ActivityPetCrudBinding
import com.kuliah.adopet.model.PetModelClass
import kotlinx.android.synthetic.main.activity_pet_crud.*
import kotlinx.android.synthetic.main.activity_pet_crud.Female
import kotlinx.android.synthetic.main.activity_pet_crud.L
import kotlinx.android.synthetic.main.activity_pet_crud.M
import kotlinx.android.synthetic.main.activity_pet_crud.Male
import kotlinx.android.synthetic.main.activity_pet_crud.Pet_sex
import kotlinx.android.synthetic.main.activity_pet_crud.Pet_size
import kotlinx.android.synthetic.main.activity_pet_crud.Pet_vaksin
import kotlinx.android.synthetic.main.activity_pet_crud.S
import kotlinx.android.synthetic.main.activity_pet_crud.address
import kotlinx.android.synthetic.main.activity_pet_crud.btnPrimary
import kotlinx.android.synthetic.main.activity_pet_crud.descri
import kotlinx.android.synthetic.main.activity_pet_crud.fullname
import kotlinx.android.synthetic.main.activity_pet_crud.latitude
import kotlinx.android.synthetic.main.activity_pet_crud.longitude
import kotlinx.android.synthetic.main.activity_pet_crud.pet_ag
import kotlinx.android.synthetic.main.activity_pet_crud.pet_we
import kotlinx.android.synthetic.main.activity_pet_crud.vaksin_no
import kotlinx.android.synthetic.main.activity_pet_crud.vaksin_yes
import java.util.*

class PetCRUD : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_crud)
        val ID: String? = intent.extras?.getString("ID")

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val accID = databaseHandler.checkAccount()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this!!)

        var category:String = ""

        val binding = ActivityPetCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arrayList_cat = arrayListOf<String>("Persia", "Bengal", "Siberia")
        val arrayList_dog = arrayListOf<String>("Bulldog", "Pudel", "Husky")

        val gpsButton = findViewById<TextView>(R.id.gpsBtn)
        gpsButton.setOnClickListener {
            Toast.makeText(this, "Mendapatkan lokasi terkini", Toast.LENGTH_SHORT).show();
            getLastKnownLocation()
        }
        catBtn.setOnClickListener {
            catBtn.setBackgroundResource(R.drawable.btn_secondary)
            dogBtn.setBackgroundResource(R.drawable.btn_tertiary)
            category = "Cat"
            binding.pettype.adapter = ArrayAdapter(applicationContext, R.layout.spinner_text, arrayList_cat)
        }
        dogBtn.setOnClickListener {
            catBtn.setBackgroundResource(R.drawable.btn_tertiary)
            dogBtn.setBackgroundResource(R.drawable.btn_secondary)
            category = "Dog"
            binding.pettype.adapter = ArrayAdapter(applicationContext, R.layout.spinner_text, arrayList_dog)
        }

        var sex_checked:Boolean = false
        var sex:String = ""
        var size_checked:Boolean = false
        var size:String = ""
        var vaccine_checked:Boolean = false
        var vaccine:String = ""
        Pet_sex.setOnCheckedChangeListener { group, checkedId ->
            sex_checked = true
            when (checkedId) {
                R.id.Male -> { sex = "Male" }
                R.id.Female -> { sex = "Female" }
            }
        }
        Pet_size.setOnCheckedChangeListener { group, checkedId ->
            size_checked = true
            when (checkedId) {
                R.id.S -> { size = "S" }
                R.id.M -> { size = "M" }
                R.id.L -> { size = "L" }
            }
        }
        Pet_vaksin.setOnCheckedChangeListener { group, checkedId ->
            vaccine_checked = true
            when (checkedId) {
                R.id.vaksin_yes -> { vaccine = "Yes" }
                R.id.vaksin_no -> { vaccine = "No" }
            }
        }

        val mySpinner = findViewById<View>(R.id.pettype) as Spinner

        if(ID != "") {
            var emp: List<PetModelClass> = databaseHandler.getPet("ID", ID!!)

            topbar.text = "UPDATE YOUR PET"
            btnPrimary.text = "UPDATE"
            fullname.setText(emp[0].name)
            address.setText(emp[0].address)
            latitude.setText(emp[0].x)
            longitude.setText(emp[0].y)
            pet_ag.setText(emp[0].age)
            pet_we.setText(emp[0].weight)
            descri.setText(emp[0].desc)

            when (emp[0].category) {
                "Cat" ->  {
                    catBtn.setBackgroundResource(R.drawable.btn_secondary)
                    binding.pettype.adapter = ArrayAdapter(applicationContext, R.layout.spinner_text, arrayList_cat)
                    category = "Cat"
                }
                "Dog" -> {
                    dogBtn.setBackgroundResource(R.drawable.btn_secondary)
                    binding.pettype.adapter = ArrayAdapter(applicationContext, R.layout.spinner_text, arrayList_dog)
                    category = "Dog"
                }
            }
            when (emp[0].sex) {
                "Male" -> Male.isChecked = true
                "Female" -> Female.isChecked = true
            }
            when (emp[0].size) {
                "S" -> S.isChecked = true
                "M" -> M.isChecked = true
                "L" -> L.isChecked = true
            }
            when (emp[0].vaccine) {
                "Yes" -> vaksin_yes.isChecked = true
                "No" -> vaksin_no.isChecked = true
            }
            when (emp[0].type) {
                "Persia" -> pettype.setSelection(0)
                "Bengal" -> pettype.setSelection(1)
                "Siberia" -> pettype.setSelection(2)
                "Bulldog" -> pettype.setSelection(0)
                "Pudel" -> pettype.setSelection(1)
                "Husky" -> pettype.setSelection(2)
            }
        }

        btnPrimary.setOnClickListener {
            var name = fullname.text.toString()
            var location = address.text.toString()
            var x = latitude.text.toString()
            var y = longitude.text.toString()
            var age = pet_ag.text.toString()
            var weight = pet_we.text.toString()
            var desc = descri.text.toString()
            if (name.trim().isNotEmpty() && location.trim().isNotEmpty() && x.trim().isNotEmpty() && y.trim().isNotEmpty()
                && age.trim().isNotEmpty() && weight.trim().isNotEmpty() && desc.trim().isNotEmpty() && sex_checked && size_checked && vaccine_checked) {
                    if(ID != "") {
                        databaseHandler.createAdopt(ID.toInt(), name, category, location, x, y, sex, size, age, weight, mySpinner.selectedItem.toString(), vaccine, desc, "UPDATE")
                        Toast.makeText(this, "Sukses mengupdate data", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        if (databaseHandler.createAdopt(accID, name, category, location, x, y, sex, size, age, weight, mySpinner.selectedItem.toString(), vaccine, desc)) {
                            Toast.makeText(this, "Sukses menambah data", Toast.LENGTH_SHORT).show();
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error, harap coba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }
            } else {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
            }
        }

        backBtn.setOnClickListener {
            finish()
        }
        cancel_button.setOnClickListener {
            finish()
        }
    }

    private fun getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
            if (location != null) {
                latitude.setText(location.latitude.toString())
                longitude.setText(location.longitude.toString())

                var addresses: List<Address>
                val geocoder: Geocoder
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )

                val addressValue = addresses[0].getFeatureName() + ", " + addresses[0].getLocality() + ", " + addresses[0].getAdminArea() + ", " + addresses[0].getCountryName() + " " + addresses[0].getPostalCode()
                address.setText(addressValue)

            }

        }

    }
}