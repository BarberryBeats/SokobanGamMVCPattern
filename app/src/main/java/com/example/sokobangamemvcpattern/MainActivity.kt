package com.example.sokobangamemvcpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.KeyEvent.*
import android.widget.Toast
import com.example.sokobangamemvcpattern.databinding.ActivityMainBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    companion object {
        const val MIN_PATH = 150
    }

    val controller: Controller = Controller(this)
    private lateinit var binding: ActivityMainBinding
    lateinit var gestureDetector: GestureDetector

    lateinit var model: Model

    var x1: Float = 0.0f
    var x2: Float = 0.0f
    var y2: Float = 0.0f
    var y1: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestureDetector = GestureDetector(this, this)

        binding.apply {
            startBtn.setOnClickListener {
                model = Model(this@MainActivity)
                model.initialization()

                startBtn.visibility = View.GONE
                txtCanvas.visibility = View.VISIBLE
                txtLevel.visibility = View.VISIBLE

                txtCanvas.text = printCanvas(model.getCanvas())
                txtLevel.text = "Level: " + model.getCurrentLevel().toString()

                btnNextLvl.setOnClickListener {
                    model.chooseNextLvl()
                    model.initialization()
                    txtLevel.text = model.getCurrentLevel().toString()
                    txtCanvas.text = printCanvas(model.getCanvas())
                }

                btnPrevLvl.setOnClickListener {
                    model.choosePrevLvl()
                    model.initialization()
                    txtLevel.text = model.getCurrentLevel().toString()
                    txtCanvas.text = printCanvas(model.getCanvas())
                }

                btnRestart.setOnClickListener {
                    model.initialization()
                    txtCanvas.text = printCanvas(model.getCanvas())
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event?.action) {

            0 -> {
                x1 = event.x
                y1 = event.y
            }

            1 -> {
                x2 = event.x
                y2 = event.y

                val valueX: Float = x2 - x1
                val valueY: Float = y2 - y1

                if (abs(valueX) > MIN_PATH && abs(valueY) < MIN_PATH) {
                    if (x2 > x1) {
                        model.moveRight()
                        model.winGame()
                        model.isFinishGame()
                        makeToast("go right")
                    } else {
                        model.moveLeft()
                        model.winGame()
                        model.isFinishGame()
                        makeToast("go left")
                    }
                } else if (abs(valueY) > MIN_PATH && abs(valueX) < MIN_PATH) {
                    if (y2 > y1) {
                        model.moveDown()
                        model.winGame()
                        model.isFinishGame()

                        makeToast("go down")
                    } else {
                        model.moveUp()
                        model.winGame()
                        model.isFinishGame()

                        makeToast("go up")
                    }

                }

                binding.txtLevel.text = "Level: " + model.getCurrentLevel().toString()
                binding.txtCanvas.text = printCanvas(model.getCanvas())

                if (model.getGameFinished()) {
                    model.chooseNextLvl()
                    binding.txtCanvas.text = printCanvas(model.getCanvas())
                }
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    fun printCanvas(canvas: Array<Array<Int>>): String {
        var text: String = ""

        for (i in canvas.indices) {
            text += "${canvas[i].contentToString()}\n"
        }

        return text
    }
}
