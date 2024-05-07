package com.application.knc.AppInterface

interface AdapterViewClickListener
{
    fun onClick(data:Any, selectedPosition:Int, clickLayoutCode:Int=0, callBack:AdapterViewClickListener?=null)
}