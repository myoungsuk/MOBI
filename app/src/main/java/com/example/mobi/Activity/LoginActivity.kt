package com.example.mobi.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.mobi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.view_loader.*

class LoginActivity : AppCompatActivity()
{

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        findViewById<View>(R.id.loginButton).setOnClickListener(onClickListener)
        findViewById<View>(R.id.gotoPasswordResetButton).setOnClickListener(onClickListener)
        findViewById<View>(R.id.signUpButton).setOnClickListener(onClickListener)

    }

    private var onClickListener =
        View.OnClickListener { v ->
            when (v.id)
            {
                R.id.loginButton -> initLoginButton()
                R.id.gotoPasswordResetButton -> myStartActivity(PasswordResetActivity::class.java)
                R.id.signUpButton -> myStartActivity(RegisterActivity::class.java)
            }
        }
    //로그인 버튼 함수
    private fun initLoginButton()
    {

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

            //변수 선언
            val email = (findViewById<View>(R.id.emailEditText) as EditText).text.toString()
            val password = (findViewById<View>(R.id.passwordEditText) as EditText).text.toString()

            //firebase 리스너 호출
            if (email.isNotEmpty() && password.isNotEmpty())
            {
                showProgress()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        hideProgress()
                        if (task.isSuccessful)
                        {

                            Toast.makeText(
                                this,
                                "로그인에 성공하셨습니다", Toast.LENGTH_SHORT
                            ).show()
                            myStartActivity(MainActivity::class.java)
                        } else
                        {
                            Toast.makeText(
                                this,
                                "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
            } else
            {
                Toast.makeText(
                    this,
                    "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun showProgress() {
        findViewById<ProgressBar>(R.id.progressBar)?.isVisible = true
    }

    private fun hideProgress() {
        findViewById<ProgressBar>(R.id.progressBar)?.isVisible = false
    }

    // 액티비티 이동
    private fun myStartActivity(c: Class<*>)
    {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
