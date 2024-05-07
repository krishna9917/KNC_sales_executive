package com.application.knc.AppInterface

interface DialogClickListener
{
    fun onClick(clickCode: Int,data:Any?=null,callBack: DialogClickListener?=null)
}