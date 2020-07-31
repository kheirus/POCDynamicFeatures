package com.kouelaa.pocdynamicfeatures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go_feature_a.setOnClickListener {
            startActivity(
                    Intent().setClassName(
                            "com.kouelaa.pocdynamicfeatures",
                            "com.kouelaa.feature_a.FeatureAActivity"
                    )
            )
        }
    }
}