package edu.us.ischool.info448.songspot.activites

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import edu.us.ischool.info448.songspot.R


class GenreOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_overview)

        val genreName = intent.getStringExtra("GENRE_NAME")
        Log.i("CHRISTINA", "overviewActivity $genreName")
        val genreLabel = findViewById<TextView>(R.id.genre_overview_label)
        genreLabel.text = genreName
    }
}