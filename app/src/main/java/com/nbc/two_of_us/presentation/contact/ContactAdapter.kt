package com.nbc.two_of_us.presentation.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.databinding.ItemListBinding
import com.nbc.two_of_us.databinding.ItemListReverseBinding

class ContactAdapter(private val contacts: List<ContactInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_BASE = 1
    private val ITEM_TYPE_REVERSE = 2
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onClick(contactInfo: ContactInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BASE -> {
                val binding =
                    ItemListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeBaseViewHolder(binding)
            }

            ITEM_TYPE_REVERSE -> {
                val binding =
                    ItemListReverseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeReverseViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount(): Int = contacts.size

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            ITEM_TYPE_BASE
        } else {
            ITEM_TYPE_REVERSE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = contacts[position]

        when (holder.itemViewType) {
            ITEM_TYPE_BASE -> {
                val typeBaseHolder = holder as TypeBaseViewHolder
                typeBaseHolder.bind(currentItem)

                holder.itemView.setOnClickListener {
                    itemClick?.onClick(contacts[position])
                }
            }

            ITEM_TYPE_REVERSE -> {
                val typeReverseHolder = holder as TypeReverseViewHolder
                typeReverseHolder.bind(currentItem)

                holder.itemView.setOnClickListener {
                    itemClick?.onClick(contacts[position])
                }
            }
        }
    }

    class TypeBaseViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                itemProfileCircleImageView.setImageURI(contactInfo.thumbnail)
                itemNameTextView.text = contactInfo.name
                //itemLikeImageView 처리
            }
        }
    }

    class TypeReverseViewHolder(private val binding: ItemListReverseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                itemProfileCircleImageViewReverse.setImageURI(contactInfo.thumbnail)
                itemNameTextViewReverse.text = contactInfo.name
                //itemLikeImageViewReverse 처리
            }
        }
    }
}