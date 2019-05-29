package com.carlosgub.jsonexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.carlosgub.jsonexample.R
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {

    private lateinit var post: Post
    private val presenter = PostDetailPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        getExtras()
    }

    private fun getExtras(){
        val extras = intent.extras
        if(extras!=null){
            if(extras.getParcelable<Post>("POST")!=null){
                post = extras.getParcelable("POST") as Post
                cv.visibility= View.VISIBLE
                tvId.text = "${post.id}"
                tvBody.text = post.body
                tvTitle.text = post.title
                tvUserId.text = "${post.userId}"
            }else{
                presenter.retrievePost(extras.getInt("ID",-1).toString(),object:OperationCallback{
                    override fun hideLoading() {
                        cv.visibility= View.VISIBLE
                        pb.visibility=View.GONE
                    }

                    override fun showLoading() {
                        cv.visibility= View.GONE
                        pb.visibility=View.VISIBLE
                    }

                    override fun onError(obj: String?) {

                    }

                    override fun onSuccess(obj: Any?) {
                        post = obj as Post
                        tvId.text = "${post.id}"
                        tvBody.text = post.body
                        tvTitle.text = post.title
                        tvUserId.text = "${post.userId}"
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancel()
    }
}
