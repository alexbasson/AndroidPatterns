package com.example.androidpatterns.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.androidpatterns.R
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageTextView = view.findViewById<TextView>(R.id.message)
        val fetchGatewayMessageButton = view.findViewById<Button>(R.id.fetch_gateway_message_button)
        val gatewayMessageTextView = view.findViewById<TextView>(R.id.gateway_message)
        val resetButton = view.findViewById<Button>(R.id.reset_button)

        mainViewModel.welcomeMessage.observe(viewLifecycleOwner, Observer { welcomeMessage ->
            messageTextView.text = welcomeMessage
        })

        mainViewModel.gatewayMessageLoadingState.observe(viewLifecycleOwner, Observer { loadingState ->
            fetchGatewayMessageButton.text = when (loadingState) {
                GatewayMessageLoadingState.UNLOADED -> context?.getText(R.string.fetch_gateway_message)
                GatewayMessageLoadingState.LOADING -> context?.getText(R.string.loading)
                GatewayMessageLoadingState.LOADED -> context?.getText(R.string.loaded)
            }
        })

        mainViewModel.gatewayMessage.observe(viewLifecycleOwner, Observer { gatewayMessage ->
            gatewayMessageTextView.text = gatewayMessage
        })

        fetchGatewayMessageButton.setOnClickListener {
            mainViewModel.fetchMessage()
        }

        resetButton.setOnClickListener {
            fetchGatewayMessageButton.text = context?.getText(R.string.fetch_gateway_message)
            gatewayMessageTextView.text = ""
        }
    }

}
