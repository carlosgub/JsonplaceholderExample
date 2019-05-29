package com.carlosgub.jsonexample.view.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carlosgub.jsonexample.R
import com.carlosgub.jsonexample.data.OperationCallback
import com.carlosgub.jsonexample.model.Post
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {

    private val presenter = NewPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        btSalir.setOnClickListener {
            onBackPressed()
        }

        btCrear.setOnClickListener {
            if(validar()){
                presenter.createPost(etUserId.text.toString(),etTitle.text.toString(),etBody.text.toString(),object:OperationCallback{
                    override fun showLoading() {
                        cl.visibility= View.VISIBLE
                        cvPb.visibility = View.VISIBLE
                    }

                    override fun hideLoading() {
                        cvPb.visibility=View.GONE
                        cvResult.visibility=View.VISIBLE
                    }

                    override fun onError(obj: String?) {
                        Toast.makeText(applicationContext,obj,Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess(obj: Any?) {
                        val post = obj as Post
                        tvId.text = "${post.id}"
                    }
                })
            }
        }

    }

    private fun validar():Boolean{
        return if(etUserId.text.isNullOrEmpty()){
            Toast.makeText(applicationContext,"Ingrese el UserID",Toast.LENGTH_SHORT).show()
            false
        }else if(etTitle.text.isNullOrEmpty()){
            Toast.makeText(applicationContext,"Ingrese el titulo",Toast.LENGTH_SHORT).show()
            false
        }else if(etBody.text.isNullOrEmpty()){
            Toast.makeText(applicationContext,"Ingrese el body",Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }
}
