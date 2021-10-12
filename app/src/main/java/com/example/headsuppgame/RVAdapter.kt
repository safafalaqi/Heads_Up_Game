package com.example.headsuppgame
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.headsuppgame.databinding.ItemRowBinding

class RVAdapter(private val celebrities: Celebrities ,val context:Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>(){
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    var filterdCelebrities:Celebrities
    init {
        filterdCelebrities = celebrities as Celebrities
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val name=celebrities.get(position).name
        val taboo1=celebrities.get(position).taboo1
        val taboo2=celebrities.get(position).taboo2
        val taboo3=celebrities.get(position).taboo3
        val id=celebrities.get(position).pk

        holder.binding.apply {
            tvName.text = name
            tvTaboo1.text = taboo1
            tvTaboo2.text = taboo2
            tvTaboo3.text = taboo3

        }
        holder.binding.btimgdel.setOnClickListener{
            val intent = Intent(context, UpdateDeleteActivity::class.java)
                intent.putExtra("celebrity",celebrities[position])
                context.startActivity(intent)
        }
        holder.binding.btimgupdate.setOnClickListener{
            val intent = Intent(context, UpdateDeleteActivity::class.java)
            intent.putExtra("celebrity",celebrities[position])
            context.startActivity(intent)
        }



     }

    override fun getItemCount()= celebrities.size

    //implementing DiffUtil in RecyclerView
    fun updateList(newCelebrities: Celebrities){
        val diffCallback = CelebritiesDiffCallback(celebrities, newCelebrities)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        celebrities.clear()
        celebrities.addAll(newCelebrities)
        //Log.d("checkdiff","here in updatelist")
        diffResult.dispatchUpdatesTo(this)
    }



}