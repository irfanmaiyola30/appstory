package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewWelcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.AppStory.databinding.ActivityWelcomeBinding
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewLogin.Login_Activity
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.register.RegisterActivity

class Welcome_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        val loginAnim = ObjectAnimator.ofFloat(binding.btnLoginWelcome, View.ALPHA, 1f).setDuration(500)
        val signupAnim = ObjectAnimator.ofFloat(binding.btnRegisterWelcome, View.ALPHA, 1f).setDuration(500)
        val titleAnim = ObjectAnimator.ofFloat(binding.tvTitleWelcome, View.ALPHA, 1f).setDuration(500)
        val descAnim = ObjectAnimator.ofFloat(binding.tvDescWelcome, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(titleAnim, descAnim)
            playTogether(loginAnim, signupAnim)
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            btnLoginWelcome.setOnClickListener {
                startActivity(Intent(this@Welcome_Activity, Login_Activity::class.java))
            }
            btnRegisterWelcome.setOnClickListener {
                startActivity(Intent(this@Welcome_Activity, RegisterActivity::class.java))
            }
        }
    }

    private fun setupView() {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
