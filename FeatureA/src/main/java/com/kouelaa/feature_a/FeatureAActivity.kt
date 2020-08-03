package com.kouelaa.feature_a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feature_a.*

class FeatureAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_a)

        go_feature_b.setOnClickListener {
            startActivity(
                Intent().setClassName(
                    "com.kouelaa.pocdynamicfeatures",
                    "com.kouelaa.feature_b.FeatureBActivity"
                )
            )
        }
    }
}