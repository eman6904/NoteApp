package com.example.firebaseapp.myPackages.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.firebaseapp.R

class NoteAdapter(private val context: Context, private val list: ArrayList<NoteContent>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            as LayoutInflater

    override fun getCount(): Int {
       return list.size
    }

    override fun getItem(p0: Int): Any {
      return  list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
         val rowView = LayoutInflater.from(context).inflate(R.layout.note_model,p2,false)
         val item: NoteContent = getItem(p0) as NoteContent
         val title=rowView.findViewById<TextView>(R.id.title)
         val date=rowView.findViewById<TextView>(R.id.date)
         title.text=item.title
         date.text=item.date
        return rowView
    }

}
