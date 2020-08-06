package com.kouelaa.feature_a

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManager
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManagerFactory
import kotlinx.android.synthetic.main.activity_feature_a.*
import java.lang.Exception

class FeatureAActivity : AppCompatActivity(),
    SplitInstallStateUpdatedListener {

    private lateinit var manager: SplitInstallManager
    private var mySessionId = 0



    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // Emulates installation of on demand modules using SplitCompat.
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_a)



        // TODO-(05/08/20)-kheirus:  We can later use DI to inject this
        manager = SplitInstallManagerFactory.create(this)

        // TODO-(05/08/20)-kheirus:  Here we have to test if the module is already downloaded or not before calling it, otherwise the app crashes
        // TODO-(05/08/20)-kheirus:  find solution to request the name of the feature from an asset and not hard code it

        go_feature_b.setOnClickListener {
            val request = SplitInstallRequest.newBuilder()
                .addModule("FeatureB")
                .build()

            manager.registerListener (this )

            manager.startInstall(request)
                .addOnSuccessListener{ sessionId -> mySessionId = sessionId }
                .addOnFailureListener{ exception -> failureInstallingFeatureB(exception) }

        }
        manager.unregisterListener(this)
    }


    private fun failureInstallingFeatureB(e: Exception) {
        Toast.makeText(this, "Error loading Feature B from Play Store: $e", Toast.LENGTH_LONG)
            .show()
    }

    private fun showProgress(progressPercent: Long) {
        go_feature_b.isEnabled = false
        progress.visibility = View.VISIBLE
        progress_tv.visibility = View.VISIBLE

        progress.progress = progressPercent.toInt()
    }

    private fun startFeatureB() {
        progress.visibility = View.GONE
        progress_tv.visibility = View.GONE
        go_feature_b.isEnabled = true

        startActivity(
            Intent().setClassName(
                "com.kouelaa.pocdynamicfeatures",
                "com.kouelaa.feature_b.FeatureBActivity"
            )
        )
    }

    override fun onStateUpdate(state : SplitInstallSessionState) {
        if (state.status() == SplitInstallSessionStatus.FAILED)
            {
            // // TODO-(06/08/20)-kheirus: Make a dialog or anything else to Retry the request.
            return
        }
        if (state.sessionId() == mySessionId) {
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    val totalBytes = state.totalBytesToDownload()
                    val progress = state.bytesDownloaded()
                    showProgress(progress*100/totalBytes)
                }
                SplitInstallSessionStatus.INSTALLED -> {

                    // After a module is installed, you can start accessing its content or
                    // fire an intent to start an activity in the installed module.
                    // For other use cases, see access code and resources from installed modules.

                    // If the request is an on demand module for an Android Instant App
                    // running on Android 8.0 (API level 26) or higher, you need to
                    // update the app context using the SplitInstallHelper API.

                    startFeatureB()
                }

                else -> Unit
            }
        }
    }

}