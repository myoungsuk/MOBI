package com.example.mobi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingFragment : Fragment(R.layout.fragment_setting)
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