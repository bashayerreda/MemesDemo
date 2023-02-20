package com.example.memeskotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memeskotlin.domain.models.Memes
import kotlinx.android.synthetic.main.row_items.view.*
import java.lang.Boolean.FALSE


class MemesAdapter(var apiList: MutableList<Memes>) : RecyclerView.Adapter<MemesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemesViewHolder {
        return MemesViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
        holder.onBind(apiList[position])
    }

    override fun getItemCount(): Int {
        return apiList.size
    }

    fun setData(apilist: MutableList<Memes>) {
        this.apiList = apilist
      notifyDataSetChanged()
    }

    fun getmemesPosition(position: Int): Memes {
        return apiList.get(position)

    }

    fun removeItem(position: Int) {
        if (apiList.size > 0) {
            apiList.removeAt(position);
            notifyItemRemoved(position);

            if (position != 0) {
                notifyItemChanged(position - 1, FALSE!!)
            }
        }
        this.notifyDataSetChanged()

    }
    fun removeList(){
        val size: Int = apiList.size
        apiList.clear()
        notifyItemRangeRemoved(0, size)
    }


}

class MemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val memes_img = itemView.memes_img
    val memes_name = itemView.memes_name
    fun onBind(memes: Memes) {
        Glide
            .with(itemView)
            .load(memes.url)
            .centerCrop()
            .into(memes_img)
        memes_name.text = memes.name
    }
}


