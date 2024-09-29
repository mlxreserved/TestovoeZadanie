package com.example.search.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class RecomRecyclerDecoration: RecyclerView.ItemDecoration() {
   override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = 40
        } else if(parent.getChildAdapterPosition(view) == 0){
            outRect.left = 20
        }
    }
}