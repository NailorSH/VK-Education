package com.nailorsh.rectlistviews

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var adapter: RectListAdapter
    private var num = 1

    companion object {
        private const val NUM_KEY = "num_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rectListRecyclerView)
        addButton = findViewById(R.id.addButton)

        if (savedInstanceState != null) {
            num = savedInstanceState.getInt(NUM_KEY, 1)
        }



        adapter = RectListAdapter()

        val orientation = resources.configuration.orientation
        recyclerView.layoutManager = GridLayoutManager(
            this,
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
        )

        recyclerView.addItemDecoration(EqualSpacingItemDecoration(2))

        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            num++
            adapter.notifyItemInserted(num)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NUM_KEY, num)
    }

    private inner class RectListAdapter : RecyclerView.Adapter<RectListAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_rectangle, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, index: Int) {
            holder.textView.text = (index + 1).toString()
            if ((index + 1) % 2 == 0) {
                holder.itemView.setBackgroundColor(Color.BLUE)
            } else {
                holder.itemView.setBackgroundColor(Color.RED)
            }
        }

        override fun getItemCount(): Int {
            return num
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.rectangleTextView)
        }
    }
}

class EqualSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
}

