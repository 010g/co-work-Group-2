package app.appworks.school.stylish.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.databinding.ViewholderAnswerBinding
import app.appworks.school.stylish.databinding.ViewholderHelloBinding
import app.appworks.school.stylish.databinding.ViewholderQuestionBinding

private const val ITEM_VIEW_TYPE_HELLO = 0x00
private const val ITEM_VIEW_TYPE_QUESTION = 0x01
private const val ITEM_VIEW_TYPE_ANSWER = 0x02

class ChatBotListAdapter(
    private val getDeliveryInfo: () -> Unit,
    private val getProcessInfo: () -> Unit,
    private val getExchangeInfo: () -> Unit,
    private val getOrderInfo: () -> Unit,
    private val getFindAnotherCustomerService: () -> Unit
) :
    ListAdapter<ChatBotDataItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatBotDataItem.ForHelloItem -> ITEM_VIEW_TYPE_HELLO
            is ChatBotDataItem.ForQuestionItem -> ITEM_VIEW_TYPE_QUESTION
            is ChatBotDataItem.ForAnswerItem -> ITEM_VIEW_TYPE_ANSWER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HELLO -> HelloViewHolder.from(parent)

            ITEM_VIEW_TYPE_QUESTION -> QuestionViewHolder.from(parent)

            ITEM_VIEW_TYPE_ANSWER -> AnswerViewHolder.from(parent)

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HelloViewHolder -> {
                val item = getItem(position) as ChatBotDataItem.ForHelloItem
                holder.bind(item.helloItem)
            }

            is QuestionViewHolder -> {
                val item = getItem(position) as ChatBotDataItem.ForQuestionItem
                holder.bind(
                    item.questionItem,
                    getDeliveryInfo,
                    getProcessInfo,
                    getExchangeInfo,
                    getOrderInfo,
                    getFindAnotherCustomerService
                )
            }

            is AnswerViewHolder -> {
                val item = getItem(position) as ChatBotDataItem.ForAnswerItem
                holder.bind(item.answerItem)
            }
        }
    }

    // Todo not yet doing the viewholder
    class HelloViewHolder(private var binding: ViewholderHelloBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(helloItem: HelloItem) {

            binding.helloItem = helloItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HelloViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderHelloBinding.inflate(layoutInflater, parent, false)
                return HelloViewHolder(binding)
            }
        }
    }

    class QuestionViewHolder(private var binding: ViewholderQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            questionItem: QuestionItem,
            getDeliveryInfo: () -> Unit,
            getProcessInfo: () -> Unit,
            getExchangeInfo: () -> Unit,
            getOrderInfo: () -> Unit,
            getFindAnotherCustomerService: () -> Unit
        ) {

            binding.questionItem = questionItem
            binding.title1Button1.setOnClickListener {
                getDeliveryInfo()
            }
            binding.title1Button2.setOnClickListener {
                getProcessInfo()
            }
            binding.title1Button3.setOnClickListener {
                getExchangeInfo()
            }
            binding.title2Button1.setOnClickListener {
                getOrderInfo()
            }
            binding.title2Button2.setOnClickListener {
                getFindAnotherCustomerService()
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): QuestionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderQuestionBinding.inflate(layoutInflater, parent, false)
                return QuestionViewHolder(binding)
            }
        }
    }

    class AnswerViewHolder(private var binding: ViewholderAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            answerItem: AnswerItem
        ) {

            binding.answerItem = answerItem
            binding.root.setOnClickListener {
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AnswerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewholderAnswerBinding.inflate(layoutInflater, parent, false)
                return AnswerViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatBotDataItem>() {
        override fun areItemsTheSame(oldItem: ChatBotDataItem, newItem: ChatBotDataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ChatBotDataItem,
            newItem: ChatBotDataItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ChatBotDataItem {

    abstract val id: Long

    data class ForHelloItem(val helloItem: HelloItem) :
        ChatBotDataItem() {
        override val id: Long
            get() = helloItem.id
    }

    data class ForQuestionItem(val questionItem: QuestionItem) :
        ChatBotDataItem() {
        override val id: Long
            get() = questionItem.id
    }

    data class ForAnswerItem(val answerItem: AnswerItem) :
        ChatBotDataItem() {
        override val id: Long
            get() = answerItem.id
    }
}