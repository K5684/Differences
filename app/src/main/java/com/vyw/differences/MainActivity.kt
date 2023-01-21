package com.vyw.differences

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.media.MediaPlayer;
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigateview: NavigationView
    lateinit var auth: FirebaseAuth
    lateinit var fstore :FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Differences"
        drawerLayout = findViewById(R.id.my_drawer_layout)
        navigateview = findViewById(R.id.navigate_view)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        var mMediaPlayer: MediaPlayer? = null

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        btnspeech.setOnClickListener{
                mMediaPlayer = MediaPlayer.create(this, R.raw.speechtextconverter)
                mMediaPlayer!!.start()

            val intent = Intent(this, Speechtotext::class.java)
            // start your next activity
            startActivity(intent)
        }
        btnttsmain.setOnClickListener {
            mMediaPlayer = MediaPlayer.create(this, R.raw.texttospeech)
            mMediaPlayer!!.start()
            val intent =Intent(this, Texttospeech::class.java)
            startActivity(intent)
        }
        arduinobutton.setOnClickListener{
            mMediaPlayer = MediaPlayer.create(this, R.raw.blindsys)
            mMediaPlayer!!.start()
            val intent =Intent(this, Arduino_activity::class.java)
            startActivity(intent)
        }
       btnSignlanguage.setOnClickListener{
           mMediaPlayer = MediaPlayer.create(this, R.raw.signlanguageconverter)
           mMediaPlayer!!.start()
            val intent =Intent(this, Sign_Language::class.java)
            startActivity(intent)
        }
        navigateview.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_UserProfile -> {
                    val intent3 = Intent(
                        this@MainActivity,
                        UserProfile::class.java
                    )
                    startActivity(intent3)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_AboutUs -> {
                    val intent1 = Intent(
                        this@MainActivity,
                        AboutUs::class.java
                    )
                    startActivity(intent1)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_ContactUs -> {
                    val intent10 = Intent(
                        this@MainActivity,
                        ContactUs::class.java
                    )
                    startActivity(intent10)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_Logout -> {
                    auth.signOut()
                    Toast.makeText(this@MainActivity, "Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        this@MainActivity,
                        Register::class.java
                    )
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })




        // Example of a call to a native method

    }

    fun btnObjectDetection_click(view: View) {
        var mMediaPlayer: MediaPlayer? = null
        mMediaPlayer = MediaPlayer.create(this, R.raw.objectdetection)
        mMediaPlayer!!.start()
        val intent = Intent(this, ObjectDetection::class.java)
        startActivity(intent)
    }





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }



}


