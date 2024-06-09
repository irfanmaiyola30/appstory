package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.AppStory.R
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_viewVectory
import com.dicoding.AppStory.databinding.ActivityMainBinding
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewadd.Add_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMaps.Maps
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewWelcome.Welcome_Activity

class MainActivity : AppCompatActivity() {

    private var token = ""
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: di_viewVectory
    private lateinit var storyAdapter: Story_List_Adapter
    private val mainViewModel: Main_Model by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        setupAdapter()
        setupAction()
        setupUser()
    }

    private fun setupAction() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, Add_Story::class.java))
        }
    }

    private fun setupUser() {
        showLoading()
        mainViewModel.getSession().observe(this@MainActivity) { session ->
            token = session.token
            if (!session.isLogin) {
                moveActivity()
            } else {
                val userName = session.name
                val helloUserText = getString(R.string.username, userName)
                supportActionBar?.title = helloUserText
                setupData()
            }
        }
        showToast()
    }

    private fun setupData() {
        mainViewModel.getListStories.observe(this@MainActivity) { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun setupAdapter() {
        storyAdapter = Story_List_Adapter()
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter.withLoadStateFooter(
                footer = Loading_State {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun setupView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupViewModel() {
        factory = di_viewVectory.getInstance(this)
    }

    private fun showLoading() {
        mainViewModel.isLoading.observe(this@MainActivity) {
            binding.pbHome.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        mainViewModel.toastText.observe(this@MainActivity) {
            it.consumeContent()?.let { toastText ->
                Toast.makeText(
                    this@MainActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun moveActivity() {
        startActivity(Intent(this@MainActivity, Welcome_Activity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_map -> {
                val intent = Intent(this, Maps::class.java)
                startActivity(intent)
                true
            }
            R.id.btn_logout -> {
                mainViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}