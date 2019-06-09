package edu.us.ischool.info448.songspot

import android.content.Context

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Interpolator
import android.widget.Button

import edu.us.ischool.info448.songspot.api.App
import edu.us.ischool.info448.songspot.models.Song


class QuestionFragment : Fragment() {
    private var questionsCount: Int? = null
    private var questionNumber: Int? = null
    private var secondsLeft: Int = 11
    private var readySecondsLeft: Int = 12
    private var listener: OnNextQuestionListener? = null
    private var correctButton: Button? = null
    private var points: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt("QUESTION_NUMBER")
            questionsCount = it.getInt("QUESTIONS_COUNT")
            points = it.getInt("POINTS")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val songURI = setButtons(view)

        // get references to all four answer choices
        val answer1 = view.findViewById<Button>(R.id.answer1)
        val answer2 = view.findViewById<Button>(R.id.answer2)
        val answer3 = view.findViewById<Button>(R.id.answer3)
        val answer4 = view.findViewById<Button>(R.id.answer4)

        // reference to timer display
        val timerDisplay = view.findViewById<TextView>(R.id.timer)
        val timer = Timer()
        val readyTimer = Timer()
        readyTimer.schedule(object : TimerTask() {
            override fun run() {

                // start "Ready Period" Timer
                activity.runOnUiThread(Runnable {
                    // tasks to be done every 1000 milliseconds
                    if (readySecondsLeft > 9) {
                        timerDisplay.text = "Ready?"
                    } else if (readySecondsLeft > 0) {
                        timerDisplay.text = "Set.."
                    } else if (readySecondsLeft == 0){
                        timerDisplay.text = "Go!"
                    }
                    if (readySecondsLeft == 0) {
                        Log.d("debugging", songURI)
                        App.sharedInstance.spotifyRemote.playSong("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
                        answer1.setEnabled(true)
                        answer2.setEnabled(true)
                        answer3.setEnabled(true)
                        answer4.setEnabled(true)

                        // cancels "ready Period" timer
                        readyTimer.cancel()

                        // starts question timer as soon as "Ready Period" is over
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                activity.runOnUiThread(Runnable {
                                    // tasks to be done every 1000 milliseconds
                                    secondsLeft--
                                    timerDisplay.text = secondsLeft.toString()
                                    if (secondsLeft == 0) {
                                        // tasks to be done if time runs out
                                        disableButtons(answer1, answer2, answer3, answer4)
                                        timer.cancel()
                                        activateBlinking(answer1, false)
                                        activateBlinking(answer2, false)
                                        activateBlinking(answer3, false)
                                        activateBlinking(answer4, true)
                                        App.sharedInstance.spotifyRemote.pauseSong()
                                        Log.d("debugging", points.toString())

                                    }
                                })
                            }
                        }, 1000, 1000)
                    }
                    readySecondsLeft--
                })

            }
        }, 1000, 1000)


        // sets onclicklisteners for all buttons
        answer1.setOnClickListener {
            timer.cancel()
            disableButtons(answer1, answer2, answer3, answer4)
            activateBlinking(answer1, true)
        }

        answer2.setOnClickListener {
            activateBlinking(answer2, true)
            disableButtons(answer1, answer2, answer3, answer4)
            timer.cancel()
        }
        answer3.setOnClickListener {
            activateBlinking(answer3, true)
            disableButtons(answer1, answer2, answer3, answer4)
            timer.cancel()
        }
        answer4.setOnClickListener {
            activateBlinking(answer4, true)
            disableButtons(answer1, answer2, answer3, answer4)
            timer.cancel()
        }
        return view
    }

    // takes in a button, and a boolean representing whether the answer is correct
    // starts a blinking animation that is green if the answer is correct, red if otherwise
    // also handles transitions between questions
    private fun activateBlinking(button: Button, willContinue: Boolean) {
        App.sharedInstance.spotifyRemote.pauseSong()
        var time = 0
        val timer2 = Timer()
        timer2.schedule(object : TimerTask() {
            override fun run() {
                // start "Ready Period" Timer
                activity.runOnUiThread(Runnable {
                    if (time == 0) {
                        if (!button.equals(correctButton)) {
                            button.backgroundTintList = (ContextCompat.getColorStateList(activity, R.color.colorRed));
                        } else {
                            button.backgroundTintList = (ContextCompat.getColorStateList(activity, R.color.colorGreen));
                            points = points!! + setPoints()

                        }
                        val mAnimation = AlphaAnimation(1f, 0f)
                        mAnimation.duration = 200
                        mAnimation.interpolator = LinearInterpolator()
                        mAnimation.repeatCount = 1
                        mAnimation.repeatMode = Animation.REVERSE
                        button.startAnimation(mAnimation)
                    }
                    if (time == 3) {
                        timer2.cancel()
                    }
                    if (time == 3 && willContinue) {
                        questionNumber = questionNumber!! + 1
                        Log.d("debugging", points.toString())
                        listener!!.onNextQuestion(questionNumber!!, questionsCount!!, points!!)
                        timer2.cancel()
                    }
                    time++
                })
            }

        }, 0, 1000)
    }



    // helper function to disable all buttons
    private fun disableButtons(button1: Button, button2: Button, button3: Button, button4:Button) {
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
    }

    // Chooses a random song to play/ set as correct answer,
    // randomly populates buttons with answer choices
    private fun setButtons(view: View): String {
        val songs:Array<Song> = App.sharedInstance.songRepository.getQuestionSongs()
        Log.d("debug", songs.toString())
        val correctTitle = songs.random().title
        Log.d("debugging", correctTitle)
        var takenOptions: Set<Int> = setOf(5)
        var randButtonIndex = 5
        var songURI = ""
        for (i in 0..3) {
            while (takenOptions.contains(randButtonIndex)) {
                randButtonIndex = (1..4).random()
            }
            takenOptions = takenOptions.plus(randButtonIndex)

            val targetID = resources.getIdentifier("answer" + randButtonIndex, "id", "edu.us.ischool.info448.songspot")
            Log.d("debugging", randButtonIndex.toString())
            val targetButton = view.findViewById<Button>(targetID)
            targetButton.text = songs[i].title
            Log.d("debugging", songs[i].title)
            // sets correct answer
            if (songs[i].title.equals(correctTitle)) {
                correctButton = targetButton
                songURI = songs[i].spotifyUri
            }

        }
        return songURI
    }

    // helper function to calculate score based on seconds left
    // can be changed later for different scoring techniques
    private fun setPoints(): Int {
        return secondsLeft * secondsLeft
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNextQuestionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNextQuestionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnNextQuestionListener {
        // takes in a question index and transitions to the next question fragment if the final question
        // hasn't been reached.
        fun onNextQuestion(questionNumber:Int, questionsCount: Int, points: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(questionNumber: Int, questionsCount: Int, points: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt("QUESTION_NUMBER", questionNumber)
                    putInt("QUESTIONS_COUNT", questionsCount)
                    putInt("POINTS", points)
                }
            }
    }
}
