package uz.coderodilov.kattabozortest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.coderodilov.kattabozortest.R
import uz.coderodilov.kattabozortest.databinding.DeviceItemBinding
import uz.coderodilov.kattabozortest.models.Device

/* 
* Created by Coder Odilov on 26/07/2023
*/

class DeviceRvAdapter(private val deviceList:List<Device>): RecyclerView.Adapter<DeviceRvAdapter.ViewHolder>(){
    private lateinit var onItemClicked: OnItemClicked

    inner class ViewHolder(private val binding: DeviceItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(device: Device) {
            binding.tvDeviceName.text = device.name
            binding.tvDeviceBrand.text = device.brand
            Glide.with(binding.root).load(device.image.url).placeholder(R.drawable.placeholder).into(binding.imageDevice)

            binding.root.setOnClickListener {
                onItemClicked.onItemClickListener(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DeviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(deviceList[position])
    }

    fun interface OnItemClicked{
        fun onItemClickListener(position: Int)
    }

    fun onItemClickListener(listener:OnItemClicked){
        this.onItemClicked = listener
    }
}