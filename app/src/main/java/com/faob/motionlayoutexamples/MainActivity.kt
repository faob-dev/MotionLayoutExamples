package com.faob.motionlayoutexamples

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val demosMap = mapOf(
            R.layout.card_detail to "Card Detail",
            R.layout.slide_menu_1 to "Slide Menu 1",
            R.layout.slide_menu_2 to "Slide Menu 2",
            R.layout.slide_menu_3 to "Slide Menu 3",
            R.layout.recycler_view_detail_1 to "Recycler View Detail 1"
        )

        val demoList = arrayListOf<DemoAdapter.Demo>()
        demosMap.entries.forEach {
            demoList.add(DemoAdapter.Demo(it.value, it.key))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DemoAdapter(demoList)
    }

    fun start(layoutFileId: Int) {
        var intent: Intent? = null;

        if (layoutFileId == R.layout.card_detail) {
            intent = Intent(this, CardDetailActivity::class.java).apply {
                putExtra("layout_file_id", layoutFileId)
            }
        } else if (layoutFileId == R.layout.slide_menu_1 ||
            layoutFileId == R.layout.slide_menu_2 ||
            layoutFileId == R.layout.slide_menu_3
        ) {
            intent = Intent(this, SlideMenuActivity::class.java).apply {
                putExtra("layout_file_id", layoutFileId)
            }
        } else if (layoutFileId == R.layout.recycler_view_detail_1) {
            intent = Intent(this, RecyclerViewDetailActivity::class.java).apply {
                putExtra("layout_file_id", layoutFileId)
            }
        }

        startActivity(intent)
    }
}

class DemoAdapter(private val demoList: ArrayList<Demo>) :
    RecyclerView.Adapter<DemoAdapter.ViewHolder>() {

    data class Demo(val item: String, val layoutId: Int)

    class ViewHolder(layout: ConstraintLayout) : RecyclerView.ViewHolder(layout) {
        val item = layout.findViewById<TextView>(R.id.item)
        var layoutId = 0

        init {
            layout.setOnClickListener {
                println("Demo ${item.text}")
                val context = it?.context as MainActivity
                context.start(layoutId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_main_row,
            parent,
            false
        ) as ConstraintLayout
        return ViewHolder(row);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = demoList[position].item
        holder.layoutId = demoList[position].layoutId
    }

    override fun getItemCount(): Int = demoList.size
}