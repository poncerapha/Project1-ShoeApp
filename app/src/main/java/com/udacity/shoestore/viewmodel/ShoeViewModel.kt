package com.udacity.shoestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel : ViewModel() {

    private lateinit var shoeList: MutableList<Shoe>
    var modifyShoeEnabled : Boolean = false

    private val _shoes = MutableLiveData<List<Shoe>>()
    val shoesLiveData: LiveData<List<Shoe>>
        get() = _shoes

    init {
        shoeList()
    }

    fun getShoeList(){
        _shoes.postValue(shoeList)
    }

    fun saveShoe(item: Shoe){
        if (modifyShoeEnabled) {
            var findItem = -1
            shoeList.forEachIndexed { index, value ->
                if (item.name == value.name) {
                    findItem = index
                }
            }
            shoeList.removeAt(findItem)
        }
        shoeList.add(item)
        _shoes.postValue(shoeList)
    }

    private fun shoeList() {
        shoeList = arrayListOf<Shoe> (
            Shoe(
                name = "Nike Air",
                size = 42.0,
                company = "Nike",
                description = "test",
                images = arrayListOf("", "")
            ),
            Shoe(
                name = "Nike Jordan",
                size = 42.0,
                company = "AIA",
                description = "very summer shoe",
                images = arrayListOf("", "")
            )
        )
    }
}