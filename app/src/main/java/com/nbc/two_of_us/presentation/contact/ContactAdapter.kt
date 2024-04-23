package com.nbc.two_of_us.presentation.contact

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.ItemListBinding
import com.nbc.two_of_us.databinding.ItemListGridBinding
import com.nbc.two_of_us.databinding.ItemListReverseBinding

class ContactAdapter(private var contacts: MutableList<ContactInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_BASE = 1
    private val ITEM_TYPE_REVERSE = 2
    private val ITEM_TYPE_GRID = 3
    lateinit var itemClick: ItemClick

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

            ITEM_TYPE_GRID -> {
                val binding =
                    ItemListGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeGridViewHolder(binding)
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
                    itemClick.onClick(contacts[position])
                }

            }

            ITEM_TYPE_REVERSE -> {
                val typeReverseHolder = holder as TypeReverseViewHolder
                typeReverseHolder.bind(currentItem)

                holder.itemView.setOnClickListener {
                    itemClick.onClick(contacts[position])
                }
            }

            ITEM_TYPE_GRID -> {
                val typeGridHolder = holder as TypeGridViewHolder
                typeGridHolder.bind(currentItem)

                holder.itemView.setOnClickListener {
                    itemClick.onClick(contacts[position])
                }
            }
        }
    }

    inner class TypeBaseViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                itemProfileCircleImageView.setImageURI(contactInfo.thumbnail)
                itemNameTextView.text = contactInfo.name

                if (contactInfo.like) {
                    binding.itemLikeImageView.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.itemLikeImageView.setImageResource(R.drawable.ic_favorite_border)
                }

                itemLikeImageView.setOnClickListener {
                    val updated = contactInfo.copy(like = !contactInfo.like)
                    val isUpdated = ContactManager.update(updated)

                    if (isUpdated) {
                        contacts[adapterPosition] = updated
                        notifyItemChanged(adapterPosition, updated)
                    } else {
                        Log.d("ContactAdapter", "좋아요 업데이트 실패")
                    }
                }
            }
        }
    }

    inner class TypeReverseViewHolder(private val binding: ItemListReverseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                itemProfileCircleImageViewReverse.setImageURI(contactInfo.thumbnail)
                itemNameTextViewReverse.text = contactInfo.name

                if (contactInfo.like) {
                    binding.itemLikeImageViewReverse.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.itemLikeImageViewReverse.setImageResource(R.drawable.ic_favorite_border)
                }

                itemLikeImageViewReverse.setOnClickListener {
                    val updated = contactInfo.copy(like = !contactInfo.like)
                    val isUpdated = ContactManager.update(updated)

                    if (isUpdated) {
                        contacts[adapterPosition] = updated
                        notifyItemChanged(adapterPosition, updated)
                    } else {
                        Log.d("ContactAdapter", "좋아요 업데이트 실패")
                    }
                }
            }
        }
    }

    inner class TypeGridViewHolder(private val binding: ItemListGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                itemProfileCircleImageViewGrid.setImageURI(contactInfo.thumbnail)
                itemNameTextViewGrid.text = contactInfo.name

                if (contactInfo.like) {
                    binding.itemLikeImageViewGrid.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.itemLikeImageViewGrid.setImageResource(R.drawable.ic_favorite_border)
                }

                itemLikeImageViewGrid.setOnClickListener {
                    val updated = contactInfo.copy(like = !contactInfo.like)
                    val isUpdated = ContactManager.update(updated)

                    if (isUpdated) {
                        contacts[adapterPosition] = updated
                        notifyItemChanged(adapterPosition, updated)
                    } else {
                        Log.d("ContactAdapter", "좋아요 업데이트 실패")
                    }
                }
            }
        }
    }
}