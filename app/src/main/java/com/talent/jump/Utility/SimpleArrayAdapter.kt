package com.talent.jump.Utility

import android.content.Context
import android.widget.ArrayAdapter

class simpleArrayAdapter(context: Context?, resource: Int, objects: Array<String?>) :
    ArrayAdapter<Any?>(context!!, resource, objects!!) {
    //複寫這個方法，使返回的資料沒有最後一項
    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}