package com.tripl3dev.presentation.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.tripl3dev.presentation.R
import ru.alexbykov.nopaginate.callback.OnRepeatListener
import ru.alexbykov.nopaginate.item.ErrorItem

class CustomErrorPagination(private val errorListener: PaginateErrorListener) : ErrorItem {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.error_item_layout, parent, false)
        return CustomErrorViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, onRepeatListener: OnRepeatListener?) {
        val view = holder as CustomErrorViewHolder
        view.text.text = errorListener.errorTextToAppear()
        view.button.setOnClickListener {
            errorListener.onRetryClicked()
        }
    }

    interface PaginateErrorListener {
        fun onRetryClicked()
        fun errorTextToAppear(): String
    }

    class CustomErrorViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var button: Button = v.findViewById(R.id.errorButton)
        var text: TextView = v.findViewById(R.id.errorText)

    }
}
