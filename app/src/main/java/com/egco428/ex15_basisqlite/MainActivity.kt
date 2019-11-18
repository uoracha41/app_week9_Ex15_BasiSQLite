package com.egco428.ex15_basisqlite

import android.app.ListActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : ListActivity() {

    private var dataSource:CommentDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataSource = CommentDataSource(this)
        dataSource!!.open()

        val values = dataSource!!.allComments //ดึงข้อมูลมาจากตาราง comment

        val adapter = ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values)
        setListAdapter(adapter)
    }

    fun onClick(view: View){
        val adapter = getListAdapter() as ArrayAdapter<Comment>
        var comment: Comment? = null
        when(view.getId()){
            R.id.add->{
                val title = titleText.text
                //val comments = arrayOf("Very Good", "Cool", "Not too bad", "nice")
                //val nextInt = Random.nextInt(3)
                comment = dataSource!!.createComment(title.toString())
                adapter.add(comment)
            }
            R.id.delete->if(getListAdapter().getCount()>0 &&
                getListAdapter().getCount()-1>=deleteText.text.toString().toInt()&&
                deleteText.text.toString().toInt()>=0){
                val position = deleteText.text.toString().toInt()
                comment = getListAdapter().getItem(position) as Comment
                dataSource!!.deleteComment(comment)
                adapter.remove(comment)
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}
