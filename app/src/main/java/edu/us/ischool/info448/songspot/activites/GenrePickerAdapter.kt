package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import edu.us.ischool.info448.songspot.R
import edu.us.ischool.info448.songspot.api.App

/** Custom RecyclerView adapter to display song genres. **/
class GenrePickerAdapter(private val genreList: Array<String>) :
    RecyclerView.Adapter<GenrePickerAdapter.GenreViewHolder>() {

    /** Reference to the views for each genre item. **/
    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context : Context = itemView.context


        val textView : TextView = itemView.findViewById(R.id.genre_text_view)
        val cardView : CardView = itemView.findViewById(R.id.genre_card_view)
        var genresView : RecyclerView = itemView.findViewById(R.id.genre_recycler_view)
        var loginButton : ProgressBar = itemView.findViewById(R.id.progressBar6)

        init {
            itemView.setOnClickListener {
                val i = Intent(context, GenreOverviewActivity::class.java)
                val genreName = textView.text.toString()
                genresView.visibility = View.INVISIBLE
                loginButton.visibility = View.VISIBLE

                i.putExtra("GENRE_NAME", genreName)

                App.sharedInstance.songRepository.fetchCategorySongs(genreName) {
                    println("SUCCESSFULLY FETCHED SONGS")
                    context.startActivity(i)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenrePickerAdapter.GenreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item, parent, false) as View
        return GenreViewHolder(itemView)
    }

    // Colors of the cards in the GenrePickerActivity.
    private val cardColors : Array<Int> = arrayOf(
        Color.parseColor("#FFA726"), // Pop
        Color.parseColor("#303F9F"), // Rap
        Color.parseColor("#80CBC4"), // Electronic
        Color.parseColor("#4CAF50"), // Indie
        Color.parseColor("#EF5350"), // The 70s
        Color.parseColor("#7E57C2")) // The 80s

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        // Replace the contents of a view with our GenreList items.
        holder.textView.text = genreList[position]
        holder.cardView.setCardBackgroundColor(cardColors[position])
    }

    override fun getItemCount() = genreList.size
}