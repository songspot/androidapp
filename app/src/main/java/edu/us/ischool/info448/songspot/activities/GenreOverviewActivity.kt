package edu.us.ischool.info448.songspot.activities

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import edu.us.ischool.info448.songspot.R

class GenreOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_overview)

        val startButton = findViewById<Button>(R.id.genre_overview_button)
        val countdownView = findViewById<TextView>(R.id.genre_overview_countdown)
        val genreLabel = findViewById<TextView>(R.id.genre_overview_label)
        val genreAlbumArt = findViewById<ImageView>(R.id.genre_album_art)
        val genreName = intent.getStringExtra("GENRE_NAME")

        genreLabel.text = genreName
        setGenreImage(genreName, genreAlbumArt)
        setGenreDescription(genreName)

        // Show countdown then begin quiz activity.
        startButton.setOnClickListener {
            val timer = object: CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    countdownView.text = (millisUntilFinished / 1000 + 1).toString()
                }

                override fun onFinish() {
                    Log.i("CHRISTINA", "Start new activity!")
                }
            }
            timer.start()
        }
    }

    private fun setGenreImage(genre: String, imgView: ImageView) {
        val img = when(genre) {
            "Pop" -> "maroon5.png"
            "Rap" -> "gambino.jpg"
            "Electronic" -> "superdream.jpg"
            "Indie" -> "currents.jpg"
            "the 70s" -> "fleetwood_mac.jpg"
            "the 80s" -> "acdc.jpg"
            else -> ""
        }

        val imageUri : Uri = Uri.parse("file:///android_asset/$img")
        Glide
            .with(applicationContext)
            .load(imageUri)
            .centerCrop()
            .placeholder(R.drawable.abc_spinner_mtrl_am_alpha)
            .error(R.drawable.abc_ic_ab_back_material)
            .into(imgView)
    }

    private fun setGenreDescription(genre: String) {

    }
}