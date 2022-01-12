package com.kuliah.adopet

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kuliah.adopet.database.DatabaseHandler
import com.kuliah.adopet.model.PetModelClass
import kotlinx.android.synthetic.main.activity_pet_detail.*

class PetDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_detail)

        val accID: String? = intent.extras?.getString("accID")
        val ID: String? = intent.extras?.getString("ID")

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        var emp: List<PetModelClass> = databaseHandler.getPet("ID", ID!!)
        val logedID = databaseHandler.checkAccount()

        var picture = R.drawable.cat3

        when (emp[0].type) {
            "Persia" -> picture = R.drawable.cat_persia
            "Bengal" -> picture = R.drawable.cat_bengal
            "Siberia" -> picture = R.drawable.cat_siberia
            "Bulldog" -> picture = R.drawable.dog_bulldog
            "Husky" -> picture = R.drawable.dog_husky
            "Pudel" -> picture = R.drawable.dog_pudel
        }

        img.setImageResource(picture)
        petName.text = emp[0].name
        petVaccine.text = ("Vaccinated : " + emp[0].vaccine)
        petOwner.text = ("Owner : " + databaseHandler.getUname(accID!!.toInt()))
        petLocation.text = ("Location : " + emp[0].address)
        petAge.text = (emp[0].age + " months")
        petSex.text = emp[0].sex
        petWeight.text = (emp[0].weight + " kg")
        petSize.text = emp[0].size
        petDesc.text = emp[0].desc

        if(accID.toInt() != logedID) {
            btnSecondary.setOnClickListener {
                val intent = Intent(this, Map::class.java)
                intent.putExtra("x",emp[0].x);
                intent.putExtra("y",emp[0].y);
                startActivity(intent)
            }
            btnPrimary.setOnClickListener {
                val phone = databaseHandler.getPhone(accID.toInt())
                if (phone == "") {
                    Toast.makeText(this, "Nomor kontak tidak disediakan pemilik", Toast.LENGTH_SHORT).show();
                } else {
                    val Url: String = "https://api.whatsapp.com/send?phone=$phone"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(Url)
                    startActivity(intent)
                }
            }
        } else {
            btnSecondary.text = "Delete Pet"
            btnPrimary.text = "Edit Details"
            btnSecondary.setOnClickListener {
                // Membuat komponen alert
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Hapus Pet")
                builder.setMessage("Apakah kamu yakin ingin menghapus pet?")

                builder.setPositiveButton("YES") { dialog, which ->
                    databaseHandler.deletePet(ID.toInt())
                    Toast.makeText(getApplicationContext(), "Sukses menghapus pet", Toast.LENGTH_LONG).show();
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                builder.setNegativeButton("NO") { dialog, which -> }

                builder.show()
            }
            btnPrimary.setOnClickListener {
                val intent = Intent(this, PetCRUD::class.java)
                intent.putExtra("ID", ID);
                startActivity(intent)
            }
        }


        backBtn.setOnClickListener {
            finish()
        }
    }
}