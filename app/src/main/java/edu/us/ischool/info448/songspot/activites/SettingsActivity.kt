package edu.us.ischool.info448.songspot.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import edu.us.ischool.info448.songspot.R

/** Personal user stats & settings. **/
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.settings_back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, GenrePickerActivity::class.java)
            startActivity(intent)
        }

        val scoresList = findViewById<ListView>(R.id.scores_list)
        // Replace hard-coded score values with real ones pulled from Firebase.
        val listAdapter = ScoresAdapter(this, arrayOf("Pop", "Rap", "Electronic", "Indie", "the 70s", "the 80s"), arrayOf(20, 110, 70, 47, 83, 92))
        scoresList.adapter = listAdapter

        val saveButton = findViewById<Button>(R.id.save_settings_button)
        saveButton.setOnClickListener {
            /** SAVE ANY SETTINGS TO FIREBASE HERE **/
        }
    }

    class ScoresAdapter(context: Context, private val genreList: Array<String>, private val scoreList: Array<Int>) : BaseAdapter() {
        private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int { return genreList.size }

        override fun getItem(p0: Int): Any { return genreList[p0] }

        override fun getItemId(p0: Int): Long { return p0.toLong() }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.settings_list_item, p2, false)
            val genreView = rowView.findViewById<TextView>(R.id.settings_list_item_name)
            val scoreView = rowView.findViewById<TextView>(R.id.settings_list_item_score)

            genreView.text = genreList[p0]
            scoreView.text = scoreList[p0].toString()

            return rowView
        }
    }
}
