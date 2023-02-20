package com.example.memeskotlin.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeskotlin.MemesAdapter
import com.example.memeskotlin.R
import com.example.memeskotlin.domain.models.Memes
import com.example.memeskotlin.presentation.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(){
    private val favMemesViewModel: FavoritesViewModel by viewModels()
    private lateinit var memesAdapter: MemesAdapter
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         rv = fav_data
        rv.layoutManager = LinearLayoutManager(activity)
         memesAdapter = MemesAdapter(mutableListOf())
        rv.adapter = memesAdapter
        swipeItem()
        lifecycleScope.launch{
            favMemesViewModel.memesLiveStateFlowDb.collect {
                memesAdapter.setData(it)
                 memesAdapter.notifyDataSetChanged()
                 print(it)


            }
        }
        favMemesViewModel.takeDataFromDBRepo()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val v = inflater.inflate(R.layout.fragment_favorite, container, false)
        memesAdapter = MemesAdapter(mutableListOf())
        memesAdapter.apiList.clear()

         return v
    }

fun swipeItem(){
    val mIth = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            0 or ItemTouchHelper.RIGHT,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                //val fromPos = viewHolder.adapterPosition
                //val toPos = target.adapterPosition
                // move item in `fromPos` to `toPos` in adapter.
                return true // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedMemePosition = viewHolder.adapterPosition
                //second step we enter in adapter and search position inside list to know what is the meme of this position now we have meme finally we will add it in db
                val memesPosition: Memes = memesAdapter.getmemesPosition(swipedMemePosition)

                memesAdapter.removeItem(swipedMemePosition)
                memesAdapter.notifyItemRemoved(swipedMemePosition)
                memesAdapter.notifyDataSetChanged()
                lifecycleScope.launch {
                    memesPosition.isFavorite = false
                    favMemesViewModel.deleteDataFromDb(memesPosition.name!!)
                }



            }
        })
    mIth.attachToRecyclerView(rv)
}


}