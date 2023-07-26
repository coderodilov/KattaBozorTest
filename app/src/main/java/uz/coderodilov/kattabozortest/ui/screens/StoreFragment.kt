package uz.coderodilov.kattabozortest.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.coderodilov.kattabozortest.R
import uz.coderodilov.kattabozortest.databinding.FragmentStoreBinding
import uz.coderodilov.kattabozortest.ui.adapter.DeviceRvAdapter

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.fragment_store) {
    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeviceRvAdapter
    private val viewModel:StoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStoreBinding.bind(view)

        viewModel.devices.observe(requireActivity()){
            adapter = DeviceRvAdapter(it)
            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_SHORT).show()
            binding.rvDevices.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}