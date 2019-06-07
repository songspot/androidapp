package edu.us.ischool.info448.songspot.activites

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import edu.us.ischool.info448.songspot.R
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton

/** Displays a list of quiz categories (music genres) to choose from, and a settings button. **/
class GenrePickerActivity : AppCompatActivity() {

    // List of available music genres to take quizzes from (probably move to some kind of data repository).
    private val genreList : Array<String> = arrayOf("Pop", "Rap", "Electronic", "Indie", "the 70s", "the 80s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_picker)

        val settingsButton = findViewById<ImageButton>(R.id.settings_button)
        settingsButton.setOnClickListener {
            Log.i("CHRISTINA", "Start settings activity")
        }

        val viewManager = GridLayoutManager(this, 2) // GridLayoutManager 2 columns wide.
        val viewAdapter = GenrePickerAdapter(genreList) // Use custom adapter.

        val recyclerView = findViewById<RecyclerView>(R.id.genre_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 48))
    }

    /** For dynamically adjusting the margins/spacing between items in the RecyclerView. **/
    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // Item position
            val column = position % spanCount // Item column

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { outRect.top = spacing } // Top edge
            outRect.bottom = spacing // Item bottom
        }
    }
}