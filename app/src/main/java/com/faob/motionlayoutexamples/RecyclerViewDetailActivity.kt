package com.faob.motionlayoutexamples

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDetailActivity : AppCompatActivity() {
    val colorList = arrayListOf("#5CA0F2", "#8FD16F", "#FF404A")
    val cardsList = arrayListOf<RVCardAdapter.Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = intent.getIntExtra("layout_file_id", R.layout.recycler_view_detail_1)
        setContentView(layout)

        for (i in 0 until 500) {
            cardsList.add(
                RVCardAdapter.Card(
                    "CARD ${i + 1}",
                    "CARD INFO ${i + 1}",
                    colorList[i % 3],
                    false
                )
            )
        }

        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = RVCardAdapter(cardsList, ::handleItemClick)

    }

    fun handleItemClick(index: Int) {
        println("item clicked $index")
    }
}

class RVCardAdapter(private val cardList: ArrayList<Card>, val itemHandler: (index: Int) -> Unit) :
    RecyclerView.Adapter<RVCardAdapter.ViewHolder>() {

    data class Card(val title: String, val info: String, val bg: String, var isExpanded: Boolean)

    class ViewHolder(
        val layout: MotionLayout,
        val cardList: ArrayList<Card>,
        val itemHandler: (index: Int) -> Unit
    ) : RecyclerView.ViewHolder(layout) {
        val bg: ConstraintLayout = layout.findViewById(R.id.rv_bg)
        val title: TextView = layout.findViewById(R.id.rv_title)
        val info: TextView = layout.findViewById(R.id.rv_info)

        init {
            layout.setOnClickListener {
                if (cardList[adapterPosition].isExpanded) {
                    layout.transitionToStart()
                } else {
                    layout.transitionToEnd()
                }
                cardList[adapterPosition].isExpanded = !cardList[adapterPosition].isExpanded
                itemHandler(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val motionLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_detail_row,
            parent,
            false
        ) as MotionLayout
        return ViewHolder(motionLayout, cardList, itemHandler)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = cardList[position].title
        holder.info.text = cardList[position].info
        holder.bg.setBackgroundColor(Color.parseColor(cardList[position].bg))

        if (cardList[position].isExpanded) {
            holder.layout.progress = 1.0f
        } else {
            holder.layout.progress = 0.0f
        }
    }

    override fun getItemCount(): Int = cardList.size
}