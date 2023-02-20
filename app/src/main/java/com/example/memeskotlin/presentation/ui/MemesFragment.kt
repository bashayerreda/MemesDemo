package com.example.memeskotlin.presentation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.memeskotlin.MemesAdapter
import com.example.memeskotlin.R
import com.example.memeskotlin.util.Resource
import com.example.memeskotlin.domain.networkconnectivity.ConnectionLiveData
import com.example.memeskotlin.domain.models.Memes
import com.example.memeskotlin.presentation.viewmodels.MemesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_memes.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MemesFragment : Fragment() {
    private val memesViewModel: MemesViewModel by viewModels()
    private lateinit var memesAdapter: MemesAdapter
    private lateinit var rv: RecyclerView
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progressBar = progress_bar
        val textViewError = text_view_error
        rv = rec_data
        rv.layoutManager = LinearLayoutManager(activity)
        memesAdapter = MemesAdapter(mutableListOf())
        rv.adapter = memesAdapter
        swipeItem()
        memesViewModel.memesData.observe(activity!!, Observer {
            memesAdapter.setData(it.data as MutableList<Memes>)
            progressBar.isVisible = it is Resource.Loading && it.data.isNullOrEmpty()
            textViewError.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
            textViewError.text = it.error?.localizedMessage

        })



        var fav_btn = go_to_fav_btn
        fav_btn.setOnClickListener {
            val favFragment = FavoriteFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.main_layout, favFragment)
                .addToBackStack(null)
            transaction.commit()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val v = inflater.inflate(R.layout.fragment_memes, container, false)
        callNetworkConnection()
        return v
    }

    private fun callNetworkConnection() {
        connectionLiveData = ConnectionLiveData(activity!!.applicationContext)
        connectionLiveData.observe(viewLifecycleOwner, { isConnected ->
            if (isConnected) {
            } else {
                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setMessage("there is no connection please open wifi or mobile data and try again later")
                    .setCancelable(false)
                    .setPositiveButton("End", DialogInterface.OnClickListener { dialog, id ->
                        activity!!.finish()
                    })
                    .setNegativeButton("cached data", DialogInterface.OnClickListener { dialog, id ->

                        dialog.cancel()
                    })

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Welcome to our app")
                // show alert dialog
                alert.show()
            }

        })

    }

    fun swipeItem() {
        memesAdapter.removeList()
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0 or ItemTouchHelper.RIGHT,
                ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = target.adapterPosition
                    // move item in `fromPos` to `toPos` in adapter.
                    return true // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    var swipedMemePosition: Int = viewHolder.adapterPosition
                    //second step we enter in adapter and search position inside list to know what is the meme of this position now we have meme finally we will add it in db
                    val memesPosition: Memes = memesAdapter.getmemesPosition(swipedMemePosition)
                    val memes = Memes()
                    lifecycleScope.launch {
                        memesPosition.isFavorite = true
                        memesViewModel.insertDataFromDb(memesPosition)
                        memesAdapter.notifyItemChanged(swipedMemePosition)
                    }
                }
            })

        mIth.attachToRecyclerView(rv)
    }
}