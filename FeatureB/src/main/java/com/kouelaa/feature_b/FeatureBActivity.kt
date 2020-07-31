package com.kouelaa.feature_b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feature_b.*

class FeatureBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_b)

        btn_finish.setOnClickListener {
            finish()
        }
    }
}