package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewLogin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.AppStory.R
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_viewVectory
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_ModelSesion
import com.dicoding.AppStory.databinding.ActivityMasukBinding
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain.MainActivity

class Login_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMasukBinding
    private lateinit var factory: di_viewVectory
    private val loginViewModel: Login_Model by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitleLogin, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.tv2ndtitleLogin, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvEmailLogin, View.ALPHA, 1f).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.tlEmailLogin, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tvPasswordLogin, View.ALPHA, 1f).setDuration(500)
        val passwordEdit = ObjectAnimator.ofFloat(binding.tlPasswordLogin, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, message, email, emailEdit, password, passwordEdit, login)
            startDelay = 500
        }.start()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (edtEmailLogin.length() == 0 || edtPasswordLogin.length() == 0) {
                    edtEmailLogin.error = getString(R.string.required_field)
                    edtPasswordLogin.error = getString(R.string.required_field)
                } else if (edtEmailLogin.length() != 0 && edtPasswordLogin.length() != 0) {
                    showLoading()
                    postText()
                    showToast()
                    moveActivity()
                }
            }
        }
    }


    private fun setupView() {
        binding = ActivityMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.title_login)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupViewModel() {
        factory = di_viewVectory.getInstance(this)
    }

    private fun showToast() {
        loginViewModel.toastText.observe(this@Login_Activity) {
            it.consumeContent()?.let { toastText ->
                Toast.makeText(
                    this@Login_Activity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this@Login_Activity) {
            binding.pbLogin.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun postText() {
        binding.apply {
            loginViewModel.postLoginUserApp(
                edtEmailLogin.text.toString(),
                edtPasswordLogin.text.toString()
            )
        }

        loginViewModel.loginResponse.observe(this@Login_Activity) { response ->
            saveSession(
                Pref_ModelSesion(
                    response.loginResult?.name.toString(),
                    binding.edtEmailLogin.text.toString(),
                    (response.loginResult?.token.toString()),
                    true
                )
            )
        }
    }

    private fun moveActivity() {
        loginViewModel.loginResponse.observe(this@Login_Activity) { response ->
            if (!response.error) {
                startActivity(Intent(this@Login_Activity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun saveSession(session: Pref_ModelSesion){
        loginViewModel.saveSession(session)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}