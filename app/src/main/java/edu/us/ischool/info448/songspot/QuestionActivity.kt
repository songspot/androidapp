package edu.us.ischool.info448.songspot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.us.ischool.info448.songspot.activites.GenrePickerActivity

class QuestionActivity : AppCompatActivity(), QuestionFragment.OnNextQuestionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // by default starts Question Fragment
        val questionFragment = QuestionFragment.newInstance(1,2, 0)
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
            commit()
        }
    }

    override fun onNextQuestion(questionNumber: Int, questionsCount:Int, points: Int) {
        if (questionNumber < (questionsCount + 1)) {
            Log.d("debugging", "Reached redirect!")
            val questionFragment = QuestionFragment.newInstance(questionNumber, questionsCount, points)
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, questionFragment, "QUESTION_FRAGMENT")
                commit()
            }
        } else {
            val database: DatabaseReference = FirebaseDatabase.getInstance().reference
            val user = User("getusername", points)
            database.child("scores").child("genres").child("get_topic").child("username").setValue(user)

            val intent = Intent(baseContext, GenrePickerActivity::class.java)
            startActivity(intent)
        }
    }
}

data class User(var username: String = "", var score: Int = 0)


