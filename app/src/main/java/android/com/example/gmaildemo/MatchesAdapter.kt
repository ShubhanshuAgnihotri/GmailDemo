package android.com.example.gmaildemo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchesAdapter(val matches: List<Venues>) :
    RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {


    private var starClickListener: OnStarClickListener? = null

    fun setOnStarClickListener(listener: OnStarClickListener) {
        starClickListener = listener
    }

    interface OnStarClickListener {
        fun onStarClick(position: Int)
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val starButton: ImageView = itemView.findViewById(R.id.starButton)
        val name: TextView = itemView.findViewById(R.id.name)
        fun bind(match: Venues) {
            name.text = match.name
            if(match.verified){
                starButton.setImageResource(R.drawable.verified_star)

            }
            else{
                starButton.setImageResource(R.drawable.unverified_star)
            }


            starButton.setOnClickListener {



            }
        }
        init {
            starButton.setOnClickListener{
                val position = adapterPosition
                if(position!=RecyclerView.NO_POSITION)
                {
                    starClickListener?.onStarClick(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_matches_recycler_view, parent, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]

        holder.bind(match)
        holder.starButton.setOnClickListener {
            starClickListener?.onStarClick(position)
        }

    }


}