package com.codefresher.allinone.fragment.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codefresher.allinone.R
import com.codefresher.allinone.databinding.ActivityDetailBinding
import com.codefresher.allinone.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        intent.getStringExtra("url")?.let { detailBinding.webView.loadUrl(it) }

    }
}