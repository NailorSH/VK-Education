package com.nailorsh.rectlistviews

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val PORTRAIT_COLUMNS_NUMBER = 3
const val LANDSCAPE_COLUMNS_NUMBER = 4

class MainActivity : AppCompatActivity() {
    var num = 0

    companion object {
        private const val NUM_KEY = "num_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rectList)
        val addButton: Button = findViewById(R.id.addButton)

        val adapter = RectListAdapter(onItemClicked = {
            val data = Bundle()
            data.putInt(SecondActivity.SECOND_KEY, it)
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtras(data)
            this.startActivity(intent)
        })
        recyclerView.adapter = adapter

        if (savedInstanceState != null) {
            num = savedInstanceState.getInt(NUM_KEY, 0)
        }

        val orientation = resources.configuration.orientation
        recyclerView.layoutManager = GridLayoutManager(
            this,
            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                LANDSCAPE_COLUMNS_NUMBER else PORTRAIT_COLUMNS_NUMBER
        )

        addButton.setOnClickListener {
            num++
            adapter.notifyItemInserted(num)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NUM_KEY, num)
    }

    private inner class RectListAdapter(
        val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.Adapter<RectListAdapter.RectListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RectListViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_square, parent, false)

            return RectListViewHolder(view)
        }

        override fun onBindViewHolder(holder: RectListViewHolder, position: Int) {
            holder.textView.text = (position + 1).toString()
            if ((position + 1) % 2 == 0) {
                holder.itemView.setBackgroundColor(Color.RED)
            } else {
                holder.itemView.setBackgroundColor(Color.BLUE)
            }

            holder.itemView.setOnClickListener { onItemClicked(position+1) }
        }

        override fun getItemCount(): Int {
            return num
        }

        inner class RectListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.squareTextView)
        }
    }
}