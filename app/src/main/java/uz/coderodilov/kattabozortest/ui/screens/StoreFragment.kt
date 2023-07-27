package uz.coderodilov.kattabozortest.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.coderodilov.kattabozortest.R
import uz.coderodilov.kattabozortest.databinding.DeviceDetailsBotttomSheetBinding
import uz.coderodilov.kattabozortest.databinding.FragmentStoreBinding
import uz.coderodilov.kattabozortest.models.Device
import uz.coderodilov.kattabozortest.ui.adapter.AttributesRvAdapter
import uz.coderodilov.kattabozortest.ui.adapter.DeviceRvAdapter
import uz.coderodilov.kattabozortest.utils.NetworkStatus

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var deviceAdapter: DeviceRvAdapter
    private lateinit var deviceList:List<Device>

    private var selectedDevicePosition = 0
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
                    deviceList = it.data ?: emptyList()

                    deviceAdapter = DeviceRvAdapter(deviceList)
                    binding.rvDevices.adapter = deviceAdapter

                    deviceAdapter.onItemClickListener{ position ->
                        selectedDevicePosition = position
                        showDeviceDetailsDialog()
                    }
                }

                NetworkStatus.ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    showNetworkStateDialog()
                }
            }
        }

        viewModel.networkState.observe(requireActivity()){ isConnected ->
            if (!isConnected) {
                showNetworkStateDialog()
            }
        }

    }

    private fun showDeviceDetailsDialog() {
        val deviceDetailsDialog = BottomSheetDialog(requireContext())
        val dialogBinding = DeviceDetailsBotttomSheetBinding
            .inflate(LayoutInflater.from(requireContext()))
        deviceDetailsDialog.setContentView(dialogBinding.root)

        val selDeviceInfo = deviceList[selectedDevicePosition]

        Glide.with(requireContext())
            .load(selDeviceInfo.image.url)
            .into(dialogBinding.imageDevice)

        dialogBinding.tvDeviceName.text = selDeviceInfo.name
        dialogBinding.tvDeviceBrand.text = selDeviceInfo.brand
        dialogBinding.tvShopName.text = selDeviceInfo.merchant

        val attributesAdapter = AttributesRvAdapter(selDeviceInfo.attributes)
        dialogBinding.rvAttributes.adapter = attributesAdapter

        deviceDetailsDialog.show()
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