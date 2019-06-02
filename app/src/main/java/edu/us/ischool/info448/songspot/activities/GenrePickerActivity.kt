package edu.us.ischool.info448.songspot.activities

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import edu.us.ischool.info448.songspot.R
import android.support.v7.widget.RecyclerView
import android.view.View

class GenrePickerActivity : AppCompatActivity() {

    // List of available music genres to take quizzes from (probably move to some kind of data repository).
    private val genreList : Array<String> = arrayOf("Pop", "Rap", "Electronic", "Indie", "the 70s", "the 80s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_picker)

        val viewManager = GridLayoutManager(this, 2) // GridLayoutManager 2 columns wide.
        val viewAdapter = GenrePickerAdapter(genreList) // Use custom adapter.

        val recyclerView = findViewById<RecyclerView>(R.id.genre_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(GenreItemDecoration(48))
    }

    /** For dynamically adjusting the margins/spacing between items in the RecyclerView. **/
    internal class GenreItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = space
            outRect.bottom = 0
            outRect.left = space

            if (parent.getChildLayoutPosition(view) % 2 == 0) outRect.right = 0 else outRect.right = space
        }
    }
}