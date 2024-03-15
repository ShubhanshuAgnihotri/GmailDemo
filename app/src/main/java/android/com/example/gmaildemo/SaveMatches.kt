package android.com.example.gmaildemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class SaveMatches : Fragment() ,SaveMatchesAdapter.OnStarClickListener{


    private lateinit var savedMatchesAdapter: SaveMatchesAdapter
    private lateinit var databaseHelper: MatchDatabaseHelper
    private lateinit var saveMatchRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databaseHelper = MatchDatabaseHelper(requireContext())

        val view = inflater.inflate(R.layout.fragment_save_matches, container, false)
        saveMatchRecyclerView = view.findViewById(R.id.saveMatchRecyclerView)

        savedMatchesAdapter = SaveMatchesAdapter(fetchSavedMatchesFromDatabase())
        savedMatchesAdapter.saveOnStarClickListener(this)

        saveMatchRecyclerView.adapter = savedMatchesAdapter



        return view
    }

    private fun removeMatchFromDatabase(id: String) {
        return databaseHelper.deleteMatch(id)

    }

    private fun fetchMatchToDatabase():List<Venues> {
        TODO("Not yet implemented")
    }

    private fun fetchSavedMatchesFromDatabase(): List<Venues> {
        val fetchMatches = databaseHelper.getAllMatches()
        return fetchMatches
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database connection when the fragment is destroyed
        databaseHelper.close()
    }

    override fun onStarClick(position: Int) {
        val match = savedMatchesAdapter.matches[position]
        match.verified = !match.verified
        removeMatchFromDatabase(match.id)

        // Notify the adapter that the data has changed
        savedMatchesAdapter.notifyItemRemoved(position)
        savedMatchesAdapter.notifyDataSetChanged()
    }


}