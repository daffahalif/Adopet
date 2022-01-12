package com.kuliah.adopet.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.kuliah.adopet.PetCRUD
import com.kuliah.adopet.PetDetail
import com.kuliah.adopet.R
import com.kuliah.adopet.adapter.PetListAdapter
import com.kuliah.adopet.database.DatabaseHandler
import com.kuliah.adopet.model.PetModelClass
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.pet_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())
        var listView = view.pet_listview

        var emp: List<PetModelClass> = databaseHandler.getPet()
        listView.adapter = PetListAdapter(requireContext(),R.layout.pet_list,emp, databaseHandler.checkAccount())
        Log.d("CREATION", "Bikin listview")

        view.ccat.setOnClickListener {
            emp = databaseHandler.getPet("Cat")
            listView.adapter = PetListAdapter(requireContext(),R.layout.pet_list,emp, databaseHandler.checkAccount())
            view.ccat.setTextColor(Color.parseColor("#ffffff"))
            view.ddog.setTextColor(Color.parseColor("#4B3588"))
            view.ddog.setBackgroundResource(R.drawable.btn_tertiary)
            view.ccat.setBackgroundResource(R.drawable.btn_primary)
        }
        view.ddog.setOnClickListener {
            emp = databaseHandler.getPet("Dog")
            listView.adapter = PetListAdapter(requireContext(),R.layout.pet_list,emp, databaseHandler.checkAccount())
            view.ddog.setTextColor(Color.parseColor("#ffffff"))
            view.ccat.setTextColor(Color.parseColor("#4B3588"))
            view.ccat.setBackgroundResource(R.drawable.btn_tertiary)
            view.ddog.setBackgroundResource(R.drawable.btn_primary)
        }

        listView.setOnItemClickListener{parent, view, position, id ->
//            Log.d("CREATION", forum_id)
            val acc_id_fix = view.findViewById(R.id.acc_id) as TextView
            val id_fix = view.findViewById(R.id.pet_id) as TextView

            val intent = Intent(context, PetDetail::class.java)
            intent.putExtra("accID",acc_id_fix.text.toString());
            intent.putExtra("ID",id_fix.text.toString());
            startActivity(intent)
        }
//
//        view.details.setOnClickListener{
//            val intent = Intent(context, PetDetail::class.java)
//            startActivity(intent)
//        }

        view.addBtn.setOnClickListener{
            val intent = Intent(context, PetCRUD::class.java)
            intent.putExtra("ID", "");
            startActivity(intent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}