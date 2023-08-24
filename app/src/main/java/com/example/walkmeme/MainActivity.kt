package com.example.walkmeme

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var memeUrl:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadResponse()
        nextBtn.setOnClickListener{
            loadResponse()
        }
        shareBtn.setOnClickListener {
            share()
        }
    }

        private fun loadResponse(){
            loadingBar.visibility = View.VISIBLE
//            val textView = findViewById<TextView>(R.id.text)
// ...

// Instantiate the RequestQueue.
//            val queue = Volley.newRequestQueue(this)
            val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                        { response ->
                    // Display the first 500 characters of the response string.
//                    textView.text = "Response is: ${response.substring(0, 500)}"
                            memeUrl = response.getString("url")
                            Glide.with(this).load(memeUrl).listener(object: RequestListener<Drawable>{
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    loadingBar.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    loadingBar.visibility = View.GONE
                                    return false
                                }
                            }).into(memeImg)
                },
                { Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show() })
//                Response.ErrorListener { textView.text = "That didn't work!" })

// Add the request to the RequestQueue.
//            queue.add(jsonObjectRequest)
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }

    private fun share(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "HA Ha HA! Check this out funny meme : $memeUrl")
        val chooser = Intent.createChooser(intent, "Choose a way to share")
        startActivity(chooser)
    }
}