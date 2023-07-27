package uz.coderodilov.kattabozortest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.coderodilov.kattabozortest.R
import uz.coderodilov.kattabozortest.databinding.DeviceAttributesItemBinding
import uz.coderodilov.kattabozortest.databinding.DeviceItemBinding
import uz.coderodilov.kattabozortest.models.Attribute
import uz.coderodilov.kattabozortest.models.Device

/* 
* Created by Coder Odilov on 26/07/2023
*/

class AttributesRvAdapter(private val deviceList:List<Attribute>): RecyclerView.Adapter<AttributesRvAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: DeviceAttributesItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(attribute: Attribute) {
            binding.tvName.text = attribute.name
            binding.tvValue.text = attribute.value
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DeviceAttributesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(deviceList[position])
    }

}