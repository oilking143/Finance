package com.talent.jump.data

import androidx.lifecycle.ViewModel

class TestViewModel :ViewModel(){

    private var testData=""

     fun getString():String{
        return testData
    }

    fun setString(input:String){

        testData=input
    }

}