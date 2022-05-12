package com.example.mobi.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobi.Activity.LoginActivity
import com.example.mobi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home)
{

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        //fragment 로그아웃 구현

//        fragment 로그아웃 구현
//        findViewById<View>(R.id.btn_logout).setOnClickListener { // 로그아웃 하기
//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            FirebaseAuth.signOut()
//       } //로그아웃하기 끝


    }


}