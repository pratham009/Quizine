package com.example.quizine

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.media.session.MediaSession.QueueItem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

@Suppress("KotlinConstantConditions")
class questionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private var mUserName: String? = null
    private var progressBar: ProgressBar? = null
    private var tvprogress: TextView? = null
    private var tvQuestion: TextView? = null
    private var tvImage: ImageView? = null
    private var mCorrectAnswers: Int = 0
    private var tv_optionOne: TextView? = null
    private var tv_optionTwo: TextView? = null
    private var tv_optionThree: TextView? = null
    private var tv_optionFour: TextView? = null
    private var btnSubmit: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        mUserName  = intent.getStringExtra(Constants.USER_NAME)
        progressBar = findViewById(R.id.progressbar)
        tvprogress = findViewById(R.id.tv_progression)
        tvQuestion = findViewById(R.id.tv_question)
        tvImage = findViewById(R.id.tv_image)
        tv_optionOne = findViewById(R.id.optionOne)
        tv_optionTwo = findViewById(R.id.optionTwo)
        tv_optionThree = findViewById(R.id.optionThree)
        tv_optionFour = findViewById(R.id.optionFour)
        btnSubmit = findViewById(R.id.btn_submit)

        tv_optionOne?.setOnClickListener(this)
        tv_optionTwo?.setOnClickListener(this)
        tv_optionThree?.setOnClickListener(this)
        tv_optionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)



        mQuestionList = Constants.getQuestions()
        setQuestion()
        defaultOptionsView()
    }

    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        tvImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvprogress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tv_optionOne?.text = question.optionOne
        tv_optionTwo?.text = question.optionTwo
        tv_optionThree?.text = question.optionThree
        tv_optionFour?.text = question.optionFour

        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tv_optionOne?.let {
            options.add(0, it)
        }
        tv_optionTwo?.let {
            options.add(1, it)
        }
        tv_optionThree?.let {
            options.add(2, it)
        }
        tv_optionFour?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor(("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_border_bg
            )
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNumber

        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_opt_border_bg
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.optionOne -> {
                tv_optionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.optionTwo -> {
                tv_optionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.optionThree -> {
                tv_optionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.optionFour -> {
                tv_optionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {

                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }
                        else ->{
                            val intent = Intent(this,lastPage::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUE,mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if(question!!.CorrectAns != mSelectedOptionPosition) {
                        ansView(mSelectedOptionPosition, R.drawable.wrong_border_bg)

                    }else{
                        mCorrectAnswers++
                    }
                        ansView(question.CorrectAns,R.drawable.correct_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit?.text = "Finish"
                    }else{
                        btnSubmit?.text = "Next"
                    }
                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun ansView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tv_optionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tv_optionTwo?.background = ContextCompat.getDrawable(
                    this@questionsActivity,
                    drawableView
                )
            }
            3 -> {
                tv_optionThree?.background = ContextCompat.getDrawable(
                    this@questionsActivity,
                    drawableView
                )
            }
            4 -> {
                tv_optionFour?.background = ContextCompat.getDrawable(
                    this@questionsActivity,
                    drawableView
                )
            }
        }
    }
}