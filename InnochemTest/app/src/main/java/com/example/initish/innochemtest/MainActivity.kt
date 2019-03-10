package com.example.initish.innochemtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.annotation.Nullable
import android.view.View
import com.github.gabrielbb.cutout.CutOut.*
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore

class MainActivity : AppCompatActivity() {


    //To show the demo for the first time only
    lateinit var sharedPref: SharedPreferences


    //To store the preference key
    companion object{
        const val PREF_KEY = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating shared preferences to show demo for first time
        sharedPref = applicationContext.getSharedPreferences("com.example.initish.innochemtest",Context.MODE_PRIVATE)
        sharedPref?.edit()?.putBoolean(PREF_KEY,true)?.apply()


        //Adding a image to change background
        fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                imageModif()
            }
        })
    }


    // Function to remove the background from image
    fun imageModif() {
        if(sharedPref.getBoolean(PREF_KEY,true))               //User opens app for first time, show the demo
        {
            activity().intro().noCrop().start(this)
            sharedPref.edit().putBoolean(PREF_KEY,false).apply()
        }
        else
            activity().noCrop().start(this)                        //Don't show the demo
    }


    //callback function to display the edited image to the user
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (requestCode == CUTOUT_ACTIVITY_REQUEST_CODE.toInt()) {

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val imageUri = getUri(data)
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

                    imageView.setImageBitmap(bitmap)
                }
            }

        }
    }
}
