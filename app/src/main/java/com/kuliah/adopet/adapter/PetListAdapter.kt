package com.kuliah.adopet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kuliah.adopet.R
import com.kuliah.adopet.model.PetModelClass
import kotlinx.android.synthetic.main.pet_list.view.*

class PetListAdapter(var mCtx: Context, var resource:Int, var items:List<PetModelClass>, var id:Int)
    : ArrayAdapter<PetModelClass>( mCtx , resource , items ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var forum: PetModelClass = items[position]

        val view: View = layoutInflater.inflate(resource, null)

        var picture = R.drawable.cat3

        when (forum.type) {
            "Persia" -> picture = R.drawable.cat_persia
            "Bengal" -> picture = R.drawable.cat_bengal
            "Siberia" -> picture = R.drawable.cat_siberia
            "Bulldog" -> picture = R.drawable.dog_bulldog
            "Husky" -> picture = R.drawable.dog_husky
            "Pudel" -> picture = R.drawable.dog_pudel
        }

        view.pet_id.text = forum.id.toString()
        view.acc_id.text = forum.accID.toString()
        view.pet_name.text = forum.name
        view.pet_photo.setImageResource(picture)
        if (forum.accID != id) {
            view.pet_person.visibility = View.INVISIBLE
        }
        if (forum.category == "Cat") {
            view.pet_category.setBackgroundResource(R.drawable.koceng)
        }
        if (forum.sex == "Male") {
            view.pet_sex.setImageResource((R.drawable.male))
        }
        view.pet_type.text = forum.type
        view.pet_age.text = (forum.age + " months")
        view.pet_address.text = forum.address

        return view
    }
}