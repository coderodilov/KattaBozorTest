package uz.coderodilov.kattabozortest.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.coderodilov.kattabozortest.R
import uz.coderodilov.kattabozortest.databinding.FragmentStoreBinding
import uz.coderodilov.kattabozortest.ui.adapter.DeviceRvAdapter
import uz.coderodilov.kattabozortest.utils.NetworkStatus

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeviceRvAdapter
    private val viewModel: StoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStoreBinding.bind(view)

        viewModel.devices.observe(requireActivity()) {
            when (it.status) {
                NetworkStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                NetworkStatus.SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter = DeviceRvAdapter(it.data ?: emptyList())
                    binding.rvDevices.adapter = adapter
                    adapter.onItemClickListener{ position ->
                        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                NetworkStatus.ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    showNetworkStateDialog()
                }
            }
        }

        viewModel.networkState.observe(requireActivity()){ isConnected ->
            if (!isConnected) showNetworkStateDialog()
        }

    }

    private fun showNetworkStateDialog() {
        val noNetworkDialog = AlertDialog.Builder(requireContext())
        noNetworkDialog.setTitle("Connection Warning")
            .setMessage("No internet connection")
            .setCancelable(false)
            .setPositiveButton("Reload") { dialog, _ ->
                dialog.dismiss()
                viewModel.getDevices()
            }
            .setNegativeButton("Exit") { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()
            }
        noNetworkDialog.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}