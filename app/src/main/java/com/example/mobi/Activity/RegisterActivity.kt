package com.example.mobi.Activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mobi.Friend
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import com.example.mobi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import androidx.activity.result.ActivityResult as ActivityResult


private lateinit var auth: FirebaseAuth
lateinit var database: DatabaseReference

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private var imageUri: Uri? = null

    //이미지 등록
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result:
                                                                                      ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data //이미지 경로 원본
                registration_iv.setImageURI(imageUri) //이미지 뷰를 바꿈
                Log.d("이미지", "성공")
            } else {
                Log.d("이미지", "실패")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        database = Firebase.database.reference

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )

        val email = emailEditText.text
        val password = passwordEditText.text
        val name = findViewById<EditText>(R.id.et_registration_name).text
        val button = findViewById<Button>(R.id.signUpButton)
        val profile = findViewById<ImageView>(R.id.registration_iv)
        var profileCheck = false

        profile.setOnClickListener {
            val intentImage = Intent(Intent.ACTION_PICK)
            intentImage.type = MediaStore.Images.Media.CONTENT_TYPE
            getContent.launch(intentImage)
            profileCheck = true
        }

        findViewById<View>(R.id.gotoLoginButton).setOnClickListener(onClickListener)


        val intent = Intent(this, LoginActivity::class.java)

        button.setOnClickListener {

            showProgress()

            if(email.isEmpty() && password.isEmpty() && name.isEmpty() && profileCheck)  {
                Toast.makeText(this, "아이디와 비밀번호, 프로필 사진을 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("Email", "$email, $password")

                hideProgress()
            }

            else{
                if(!profileCheck){
                    Toast.makeText(this, "프로필사진을 등록해주세요.", Toast.LENGTH_SHORT).show()
                    hideProgress()
                } else{
                    auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = Firebase.auth.currentUser
                                val userId = user?.uid
                                val userIdSt = userId.toString()

                                FirebaseStorage.getInstance()
                                    .reference.child("userImages").child("$userIdSt/photo").putFile(imageUri!!).addOnSuccessListener {
                                        var userProfile: Uri? = null
                                        FirebaseStorage.getInstance().reference.child("userImages").child("$userIdSt/photo").downloadUrl
                                            .addOnSuccessListener {
                                                userProfile = it
                                                Log.d("이미지 URL", "$userProfile")
                                                val friend = Friend(email.toString(), name.toString(), userProfile.toString(), userIdSt)
                                                database.child("users").child(userId.toString()).setValue(friend)
                                            }
                                    }
                                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                hideProgress()
                                Log.e(TAG, "$userId")
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                                hideProgress()
                            }
                        }
                }
            }
        }
    }
    private var onClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.gotoLoginButton -> myStartActivity(LoginActivity::class.java)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    //xml 화면이동
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    // 이메일을 xml에서 가져오는 함수
    private fun getInputEmail(): String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    //비밀번호를 xml에서 가져오는 함수
    private fun getInputPassword(): String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }

    private fun getPasswordCheck(): String {
        return findViewById<EditText>(R.id.passwordCheckEditText).text.toString()
    }

    private fun showProgress() {
        findViewById<ProgressBar>(R.id.progressBar).isVisible = true
    }

    private fun hideProgress() {
        findViewById<ProgressBar>(R.id.progressBar).isVisible = false
    }

}