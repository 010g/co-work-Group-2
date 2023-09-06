package app.appworks.school.stylish.chatbot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.appworks.school.stylish.databinding.FragmentChatBotBinding
import app.appworks.school.stylish.ext.getVmFactory

class ChatBotFragment : Fragment() {

    private val viewModel by viewModels<ChatBotViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBotBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.recyclerChatBot.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ChatBotListAdapter(
            viewModel.getDeliveryInfo,
            viewModel.getProcessInfo,
            viewModel.getExchangeInfo,
            viewModel.getOrderInfo,
            viewModel.getFindAnotherCustomerService
        )
        binding.recyclerChatBot.adapter = adapter

        viewModel.chatBotContentListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.recyclerChatBot.scrollToPosition(adapter.itemCount - 1)
        })

        return binding.root
    }

}