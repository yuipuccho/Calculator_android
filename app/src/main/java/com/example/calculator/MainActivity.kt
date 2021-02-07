package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /** 画面に表示されている数字 */
    var numberOnScreen: Long = 0
    /** 前回表示されていた数字 */
    var previousNumber: Long = 0
    /** 選択した演算子 */
    var tappedOperation: String = ""
    /** ラベルを編集できるかどうか */
    var canEditCalculatorText: Boolean = true
    /** 計算できるかどうか */
    var canCalculate: Boolean = true // 変数名変更する
    /** 数値が入力されたかどうかの判断 */
    var existsValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ビューバインディング
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onClickListener(view: View) {
        val tappedButton = view as Button
        val tappedButtonText = tappedButton.text

        when (tappedButtonText) {
            "C" -> {
                clearAllOperationButtonColor()
                clearCalculation()
            }

            "+", "-", "×", "÷" -> {
                clearAllOperationButtonColor()
                tappedButton.setBackgroundColor(applicationContext.getColor(R.color.purple_200))
                val result: Long
                var hasError = false
                result = calculation(tappedOperation) {
                    hasError = true
                    binding.calculatorText.text = "エラー"
                }
                if (hasError) return

                numberOnScreen = result
                binding.calculatorText.text = result.toString()

                println(result)

                if (existsValue) {
                    previousNumber = numberOnScreen
                }
                tappedOperation = tappedButtonText.toString()
                canCalculate = true
            }

            "=" -> {
                clearAllOperationButtonColor()
                val result = calculation(tappedOperation){}
                numberOnScreen = result
                binding.calculatorText.text = result.toString()
            }

            else -> {
                // TODO: 上限桁数のバリデーション追加
                // TODO: 0を押した時の処理追加
                clearAllOperationButtonColor()

                if (canEditCalculatorText) {
                    if (canCalculate) {
                        binding.calculatorText.text = tappedButtonText // ラベルに押した数字を表示
                        numberOnScreen = tappedButtonText.toString().toLong() // 表示されている数字を代入
                        canCalculate = false
                    } else {
                        binding.calculatorText.text = numberOnScreen.toString() + tappedButtonText // すでに表示されている数字にタップした数字を追加
                        numberOnScreen = binding.calculatorText.text.toString().toLong() // 表示されている数字を代入
                    }
                }
                existsValue = true
            }

        }
    }

    fun calculation(tappedOperation: String, errorCallback: () -> Unit): Long {
        return when (tappedOperation) {
            "÷" -> {
                try {
                    previousNumber / numberOnScreen
                } catch(e: Exception) {
                    errorCallback.invoke()
                    return -1
                }
            }
            "×" -> previousNumber * numberOnScreen
            "-" -> previousNumber - numberOnScreen
            "+" -> previousNumber + numberOnScreen
            else -> numberOnScreen
        }
    }

    fun clearAllOperationButtonColor() {
        binding.plusButton.setBackgroundColor(applicationContext.getColor(R.color.white))
        binding.minusButton.setBackgroundColor(applicationContext.getColor(R.color.white))
        binding.multiplicationButton.setBackgroundColor(applicationContext.getColor(R.color.white))
        binding.divisionButton.setBackgroundColor(applicationContext.getColor(R.color.white))
    }

    fun clearCalculation() {
        binding.calculatorText.text = "0"
        numberOnScreen = 0
        previousNumber = 0
        tappedOperation = ""
        canEditCalculatorText = true
        canCalculate = true
        existsValue = false
    }
}
