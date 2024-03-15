package android.com.example.gmaildemo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SaveMatchesAdapter(val matches:List<Venues>): RecyclerView.Adapter<SaveMatchesAdapter.SaveMatchesViewHolder>() {

    interface OnStarClickListener {
        fun onStarClick(position: Int)
    }

    private var starClickListener: OnStarClickListener? = null

    fun saveOnStarClickListener(listener: OnStarClickListener) {
        starClickListener = listener
    }
    inner class SaveMatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val starButton : ImageView = itemView.findViewById(R.id.starButton)
        val name :TextView = itemView.findViewById(R.id.name)
        init{

            starButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    starClickListener?.onStarClick(position)
                    notifyDataSetChanged()
                }
            }
        }
        fun bind(match: Venues) {
            name.text = match.name
            Log.d("saveCheckValue",match.verified.toString())
            if(match.verified){
                starButton.setImageResource(R.drawable.verified_star)

            }
            else{
                starButton.setImageResource(R.drawable.unverified_star)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveMatchesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_matches_recycler_view,parent,false)
        return SaveMatchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaveMatchesViewHolder, position: Int) {
        val match = matches[position]
        holder.bind(match)
        holder.starButton.setOnClickListener {
            Log.d("OnclickedCheck","passed")
            starClickListener?.onStarClick(position)
        }
    }


    override fun getItemCount(): Int {
        return matches.size
    }

}