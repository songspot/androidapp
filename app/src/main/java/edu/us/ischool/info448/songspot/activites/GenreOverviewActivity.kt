package edu.us.ischool.info448.songspot.activites

import android.graphics.Color
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
        val genreDescription = findViewById<TextView>(R.id.genre_description)
        val genreName = intent.getStringExtra("GENRE_NAME")

        genreLabel.text = genreName
        setGenreImage(genreName, genreAlbumArt)
        setGenreDescription(genreName, genreDescription)

        // Set countdown number animations.
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        countdownView.inAnimation = fadeIn
        countdownView.outAnimation = fadeOut

        // Show countdown then begin quiz activity.
        startButton.setOnClickListener {
            val timer = object: CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Tint background darker and show 3-2-1 text.
                    countdownView.setBackgroundColor(Color.parseColor("#90000000"))
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
            "Today's Pop Hits" -> "chainsmokers.jpg"
            "Rap Caviar" -> "drake.jpg"
            "Hot Country" -> "jason_aldean.jpg"
            "Ultimate Indie" -> "currents.jpg"
            "Rock Classics" -> "acdc.jpg"
            "All Out 70s" -> "fleetwood_mac.jpg"
            else -> ""
        }

        val imageUri : Uri = Uri.parse("file:///android_asset/$img")
        Glide
            .with(applicationContext)
            .load(imageUri)
            .into(imgView)
    }

    /** Set the genre description. **/
    private fun setGenreDescription(genre: String, textView: TextView) {
        textView.text = when(genre) {
            "Today's Pop Hits" -> "The most current pop grooves."
            "Rap Caviar" -> "Brand new rap served fresh."
            "Hot Country" -> "Today's top country hits!"
            "Ultimate Indie" -> "The best indie tracks right now."
            "Rock Classics" -> "Rock legends and epic songs spanning decades that continue to inspire generations."
            "All Out 70s" -> "From disco to soft rock, and funk to punk, the '70s had a little something for everyone."
            else -> ""
        }
    }
}