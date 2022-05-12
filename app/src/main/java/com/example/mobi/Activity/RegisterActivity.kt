package com.example.mobi.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity()
{

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        findViewById<View>(R.id.signUpButton).setOnClickListener(onClickListener)
        findViewById<View>(R.id.gotoLoginButton).setOnClickListener(onClickListener)

    }

    private var onClickListener = View.OnClickListener { v ->
        when (v.id)
        {
            R.id.signUpButton -> initSignUpButton()
            R.id.gotoLoginButton -> myStartActivity(LoginActivity::class.java)
        }
    }

    //회원가입 버튼 함수
    private fun initSignUpButton()
    {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {

            val email = getInputEmail()
            val password = getInputPassword()
            val passwordCheck = getPasswordCheck()


            if (email.isNotEmpty() && password.isNotEmpty() && passwordCheck.isNotEmpty())
            {
                if (password == passwordCheck)
                {
                    val loaderLayout = findViewById<RelativeLayout>(R.id.loaderLayout)
                    loaderLayout.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful)
                            {
                                Toast.makeText(
                                    this,
                                    "회원가입에 성공했습니다.", Toast.LENGTH_SHORT
                                ).show()
                                myStartActivity(LoginActivity::class.java)
                            } else
                            {
                                Toast.makeText(
                                    this,
                                    "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                } else
                {
                    Toast.makeText(
                        this,
                        "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT
                    ).show()
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

    //xml 화면이동
    private fun myStartActivity(c: Class<*>)
    {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    // 이메일을 xml에서 가져오는 함수
    private fun getInputEmail(): String
    {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    //비밀번호를 xml에서 가져오는 함수
    private fun getInputPassword(): String
    {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }

    private fun getPasswordCheck(): String
    {
        return findViewById<EditText>(R.id.passwordCheckEditText).text.toString()
    }
}
