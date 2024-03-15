package android.com.example.gmaildemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllMatches : Fragment(), MatchesAdapter.OnStarClickListener {

    private lateinit var matchReyclerView: RecyclerView
    lateinit var matchesAdapter: MatchesAdapter
    lateinit var apiServices: ApiService
    lateinit var venueList: MutableList<Venues>
    private lateinit var databaseHelper: MatchDatabaseHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_matches, container, false)

        matchReyclerView = view.findViewById(R.id.allMatchRecyclerView)
        venueList = mutableListOf()
        matchesAdapter = MatchesAdapter(venueList)
        matchesAdapter.setOnStarClickListener(this)
        matchReyclerView.adapter = matchesAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiServices = retrofit.create(ApiService::class.java)

        fetchMatchesData()
        // Initialize the database helper
        databaseHelper = MatchDatabaseHelper(requireContext())
        return view
    }

    private fun removeMatchFromDatabase(matchId: String) {
        databaseHelper.deleteMatch(matchId)
    }

    private fun saveMatchToDatabase(match: Venues) {
        databaseHelper.insertMatch(match)

    }
    private fun fetchMatchesData() {

        val latLng = "40.7484,-73.9857"
        val oauthToken = "NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ"
        val version = "20180616"
        val call = apiServices.getMatches(latLng, oauthToken, version)


        call.enqueue(object : Callback<MatchesResponse> {
            override fun onResponse(
                call: Call<MatchesResponse>,
                response: retrofit2.Response<MatchesResponse>
            ) {
                if (response.isSuccessful) {
                    val matchesResponse = response.body()
                    if (matchesResponse != null) {
                        val matches = matchesResponse.response.venues
                        venueList.addAll(matches)
                        matchesAdapter.notifyDataSetChanged()
                    }
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.message()
                    Log.e("API Error", "Code: $errorCode, Message: $errorMessage")

                }
            }
            override fun onFailure(call: Call<MatchesResponse>, t: Throwable) {
                Log.d("case 4", "passed")

                TODO("Not yet implemented")
            }
        })
    }

    override fun onStarClick(position: Int) {
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
        val match = matchesAdapter.matches[position]
        match.verified = !match.verified

        matchesAdapter.notifyDataSetChanged()

        if (match.verified) {
            saveMatchToDatabase(match)

        } else {
            removeMatchFromDatabase(match.id)
        }

    }
}