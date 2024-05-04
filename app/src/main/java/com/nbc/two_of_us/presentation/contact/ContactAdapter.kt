package com.nbc.two_of_us.presentation.contact

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.ItemListBinding
import com.nbc.two_of_us.databinding.ItemListGridBinding
import com.nbc.two_of_us.databinding.ItemListReverseBinding
import com.nbc.two_of_us.util.DEFAULT_THUMBNAIL_URI

class ContactAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemClick: ItemClick
    private var contacts: MutableList<ContactInfo> = mutableListOf()

    fun add(contacts: List<ContactInfo>) {
        if (contacts.isEmpty()) {
            return
        }
        this.contacts.addAll(contacts)
        notifyItemInserted(contacts.size)
    }

    fun add(contactInfo: ContactInfo) {
        contacts.add(contactInfo)
        notifyItemInserted(contacts.lastIndex)
    }

    fun update(contactInfo: ContactInfo) {
        val index = this.contacts.indexOfFirst { it.rawContactId == contactInfo.rawContactId }
        if (index == -1) {
            return
        }
        contacts[index] = contactInfo
        notifyItemChanged(index)
    }

    fun remove(contactInfo: ContactInfo) {
        val index = this.contacts.indexOfFirst { it.rawContactId == contactInfo.rawContactId }
        if(index == -1) {
            return
        }
        contacts.removeAt(index)
        notifyItemRemoved(index)
    }

    interface ItemClick {
        fun onClick(contactInfo: ContactInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.BASE_LIST.viewType -> {
                val binding =
                    ItemListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeBaseViewHolder(binding)
            }

            ViewType.REVERSE_LIST.viewType -> {
                val binding =
                    ItemListReverseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeReverseViewHolder(binding)
            }

            ViewType.GRID_LIST.viewType -> {
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
        return if (currentLayoutType == LayoutType.LIST) {
            if (position % 2 == 0) {
                ViewType.BASE_LIST.viewType
            } else {
                ViewType.REVERSE_LIST.viewType
            }
        } else {
            ViewType.GRID_LIST.viewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = contacts[position]

        when (holder) {
            is TypeBaseViewHolder -> {
                holder.bind(currentItem)
            }

            is TypeReverseViewHolder -> {
                holder.bind(currentItem)

            }

            is TypeGridViewHolder -> {
                holder.bind(currentItem)
            }

            else -> throw IllegalArgumentException("Unsupported ViewHolder type")
        }

        holder.itemView.setOnClickListener {
            itemClick.onClick(contacts[position])
        }
    }

    inner class TypeBaseViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) {
            binding.apply {
                val uri = if (contactInfo.thumbnail == Uri.EMPTY) {
                    Uri.parse(DEFAULT_THUMBNAIL_URI)
                } else {
                    contactInfo.thumbnail
                }
                itemProfileCircleImageView.setImageURI(uri)
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
        fun bind(contactInfo: ContactInfo) = with(binding) {
            val uri = if (contactInfo.thumbnail == Uri.EMPTY) {
                Uri.parse(DEFAULT_THUMBNAIL_URI)
            } else {
                contactInfo.thumbnail
            }
            itemProfileCircleImageViewReverse.setImageURI(uri)
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

    inner class TypeGridViewHolder(private val binding: ItemListGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contactInfo: ContactInfo) = with(binding) {
            val uri = if (contactInfo.thumbnail == Uri.EMPTY) {
                Uri.parse(DEFAULT_THUMBNAIL_URI)
            } else {
                contactInfo.thumbnail
            }
            itemProfileCircleImageViewGrid.setImageURI(uri)
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