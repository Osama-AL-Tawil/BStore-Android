package com.os_tec.store.Classes

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewDecoration(
    private val right: Int,
    private val left: Int,
    private val top: Int,
    private val bottom: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = top
        outRect.bottom = bottom
        outRect.left = left
        outRect.right = right

    }


//                with(outRect) {
//            if (orientation == GridLayoutManager.VERTICAL) {
//                if (parent.getChildAdapterPosition(view) < spanCount) {
//                    top = spaceSize
//                }
//                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
//                    left = spaceSize
//                }
//            } else {
//                if (parent.getChildAdapterPosition(view) < spanCount) {
//                    left = spaceSize
//                }
//                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
//                    top = spaceSize
//                }
//            }
//
//            right = spaceSize
//            bottom = spaceSize
//        }
}