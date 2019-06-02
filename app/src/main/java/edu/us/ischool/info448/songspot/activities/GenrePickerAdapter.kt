package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.us.ischool.info448.songspot.R

class GenrePickerAdapter(private val genreList: Array<String>) :
    RecyclerView.Adapter<GenrePickerAdapter.GenreViewHolder>() {


    /** Reference to the views for each genre item. **/
    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context : Context = itemView.context
        val textView : TextView = itemView.findViewById(R.id.genre_text_view)

        init {
            itemView.setOnClickListener {
                Log.i("CHRISTINA", "$textView.text clicked")
                val i = Intent(context, GenreOverviewActivity::class.java)
                i.putExtra("GENRE_NAME", textView.text)
                context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GenrePickerAdapter.GenreViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return GenreViewHolder(itemView)
    }

    // Replace the contents of a view with our GenreList items.
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.textView.text = genreList[position]
    }

    override fun getItemCount() = genreList.size
}