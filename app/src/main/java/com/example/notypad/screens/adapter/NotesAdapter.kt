package com.example.notypad.screens.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notypad.R
import com.example.notypad.screens.models.Note
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note_layout = itemView.findViewById<CardView>(R.id.myCard)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val noteTv = itemView.findViewById<TextView>(R.id.tvNote)
        val date = itemView.findViewById<TextView>(R.id.tvDate)
    }

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.title.isSelected = true
        holder.title.text = currentNote.title
        holder.noteTv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true
        holder.note_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )
        holder.note_layout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }
        holder.note_layout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.note_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()

    }

    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()

    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.noteColor1)
        list.add(R.color.noteColor2)
        list.add(R.color.noteColor3)
        list.add(R.color.noteColor4)
        list.add(R.color.noteColor5)
        list.add(R.color.noteColor6)
        list.add(R.color.noteColor7)
        list.add(R.color.noteColor8)
        list.add(R.color.noteColor9)
        list.add(R.color.noteColor10)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}