package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.AppStory.R
import com.dicoding.AppStory.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupData()
    }

    private fun setupData() {
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val photo = intent.getStringExtra("photo")

        binding.apply {
            tvNameDetail.text = name
            tvDescDetail.text = description

            Glide.with(this@DetailActivity)
                .load(photo)
                .fitCenter()
                .apply(
                    RequestOptions
                        .placeholderOf(R.drawable.loading_ic)
                        .error(R.drawable.eror_ic)
                )
                .into(ivStoryDetail)
        }
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.story_detail)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val PICTURE = "PICTURE"

        var id: String = ""
        var name: String = ""
        var description: String? = null
        var picture: String? = null
    }
}
