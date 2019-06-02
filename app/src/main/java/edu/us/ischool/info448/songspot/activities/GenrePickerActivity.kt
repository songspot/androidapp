package edu.us.ischool.info448.songspot.activites

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import edu.us.ischool.info448.songspot.R
import android.support.v7.widget.RecyclerView

class GenrePickerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val genreList : Array<String> = arrayOf("Pop", "Rap", "Electronic", "Indie", "the 70s", "the 80s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_picker)

        viewManager = GridLayoutManager(this, 2) // GridLayoutManager 2 columns wide.
        viewAdapter = GenrePickerAdapter(genreList) // Use custom adapter.

        recyclerView = findViewById<RecyclerView>(R.id.genre_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}