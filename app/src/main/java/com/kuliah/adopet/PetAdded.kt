package com.kuliah.adopet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kuliah.adopet.adapter.PetListAdapter
import com.kuliah.adopet.database.DatabaseHandler
import com.kuliah.adopet.model.PetModelClass
import kotlinx.android.synthetic.main.activity_pet_added.*
import kotlinx.android.synthetic.main.activity_pet_added.backBtn
import kotlinx.android.synthetic.main.activity_pet_detail.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class PetAdded : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_added)

        val databaseHandler = DatabaseHandler(this)
        val accID = databaseHandler.checkAccount()

        var emp: List<PetModelClass> = databaseHandler.getPet("Account", accID!!.toString())
        mypet_listview.adapter = PetListAdapter(this,R.layout.pet_list, emp, accID)

        mypet_listview.setOnItemClickListener{parent, view, position, id ->
//            Log.d("CREATION", forum_id)
            val acc_id_fix = view.findViewById(R.id.acc_id) as TextView
            val id_fix = view.findViewById(R.id.pet_id) as TextView

            val intent = Intent(this, PetDetail::class.java)
            intent.putExtra("accID",acc_id_fix.text.toString());
            intent.putExtra("ID",id_fix.text.toString());
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}