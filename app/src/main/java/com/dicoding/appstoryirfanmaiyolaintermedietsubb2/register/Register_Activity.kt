package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.AppStory.R
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_viewVectory
import com.dicoding.AppStory.databinding.ActivityDaftarBinding
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewLogin.Login_Activity


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding
    private lateinit var factory: di_viewVectory
    private val registerViewModel: Register_Model by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivRegister, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitleRegister, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.tvNameRegister, View.ALPHA, 1f).setDuration(500)
        val nameEdit = ObjectAnimator.ofFloat(binding.tlNameRegister, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvEmailRegister, View.ALPHA, 1f).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.tlEmailRegister, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tvPasswordRegister, View.ALPHA, 1f).setDuration(500)
        val passwordEdit = ObjectAnimator.ofFloat(binding.tlPasswordRegister, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, name, nameEdit, email, emailEdit, password, passwordEdit, register)
            startDelay = 500
        }.start()
    }

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (edtNameRegister.length() == 0 && edtEmailRegister.length() == 0 && edtPasswordRegister.length() == 0) {
                    edtNameRegister.error = getString(R.string.required_field)
                    edtEmailRegister.error = getString(R.string.required_field)
                    edtPasswordRegister.setError(getString(R.string.required_field), null)
                } else if (edtNameRegister.length() != 0 && edtEmailRegister.length() != 0 && edtPasswordRegister.length() != 0) {
                    showLoading()
                    postText()
                    showToast()
                    moveActivity()
                }
            }
        }
    }

    private fun setupViewModel() {
        factory = di_viewVectory.getInstance(this)
    }

    private fun setupView() {
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.title_register)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showLoading() {
        registerViewModel.isLoading.observe(this@RegisterActivity) {
            binding.pbRegister.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        registerViewModel.toastText.observe(this@RegisterActivity) {
            it.consumeContent()?.let { toastText ->
                Toast.makeText(
                    this@RegisterActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun moveActivity() {
        registerViewModel.registerResponse.observe(this@RegisterActivity) { response ->
            if (response?.error == false) {
                startActivity(Intent(this@RegisterActivity, Login_Activity::class.java))
                finish()
            }
        }
    }

    private fun postText() {
        binding.apply {
            registerViewModel.postRegister(
                edtNameRegister.text.toString(),
                edtEmailRegister.text.toString(),
                edtPasswordRegister.text.toString()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}