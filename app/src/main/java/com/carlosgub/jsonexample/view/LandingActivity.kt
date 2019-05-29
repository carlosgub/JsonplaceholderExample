package com.carlosgub.jsonexample.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosgub.jsonexample.R
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import com.carlosgub.jsonexample.adapter.PostAdapter
import com.carlosgub.jsonexample.view.create.NewActivity
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity() {

    private val presenter = LandingPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.retrievePosts(object:OperationCallback{

            override fun onError(obj: String?) {
                Toast.makeText(applicationContext,obj,Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(obj: Any?) {
                rv.layoutManager = LinearLayoutManager(applicationContext)
                rv.adapter = PostAdapter(obj as MutableList<Post>,object:PostAdapter.onClick{
                    override fun click(id: Int) {
                        val bundle = Bundle()
                        bundle.putInt("ID",id)
                        val intent = Intent(this@LandingActivity,PostDetailActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onLongClick(post: Post) {
                        val bundle = Bundle()
                        bundle.putParcelable("POST",post)
                        val intent = Intent(this@LandingActivity,PostDetailActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                })
            }

            override fun showLoading() {
                pb.visibility = View.VISIBLE
                rv.visibility = View.GONE
            }

            override fun hideLoading() {
                pb.visibility = View.GONE
                rv.visibility = View.VISIBLE
            }
        })

        fa.setOnClickListener {
            startActivity(Intent(applicationContext,NewActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }
}
