package edu.us.ischool.info448.songspot.activites

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import edu.us.ischool.info448.songspot.R
import android.widget.*
import android.view.animation.AnimationUtils

/** Shows an overview of a selected genre, including album art, a title, description, and button to start the quiz activity. **/
class GenreOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_overview)

        val startButton = findViewById<Button>(R.id.genre_overview_button)
        val countdownView = findViewById<TextSwitcher>(R.id.genre_overview_countdown)
        val genreLabel = findViewById<TextView>(R.id.genre_overview_label)
        val genreAlbumArt = findViewById<ImageView>(R.id.genre_album_art)
        val genreName = intent.getStringExtra("GENRE_NAME")

        genreLabel.text = genreName
        setGenreImage(genreName, genreAlbumArt)
        setGenreDescription(genreName)

        // Set countdown number animations.
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        countdownView.inAnimation = fadeIn
        countdownView.outAnimation = fadeOut

        // Show countdown then begin quiz activity.
        startButton.setOnClickListener {
            val timer = object: CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    countdownView.setText((millisUntilFinished / 1000 + 1).toString())
                }

                override fun onFinish() {
                    /** START QUESTION ACTIVITY HERE **/
                }
            }
            timer.start()
        }
    }

    /** Load and set the album art. **/
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
            .into(imgView)
    }

    private fun setGenreDescription(genre: String) {
        // Need to create quiz descriptions...
    }
}