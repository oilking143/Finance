package com.talent.jump.Fragment

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import com.talent.jump.model.ApiClient
import com.talent.jump.model.ApiTokenClient
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random


abstract class BaseFragment: Fragment()  {

  var apiloginclient=ApiClient()
  var apiclient=ApiTokenClient()

    fun md5(input: String): String {

        val md5String=input+""
        Log.d("md5String", md5String)
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(md5String.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getRandom(min: Int, max: Int): Float {
        require(min < max) { "Invalid range [$min, $max]" }
        return (min + Random.nextFloat() * (max - min))
    }

    open fun getPath(context:Context,uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor =
            uri?.let { context.contentResolver.query(it, projection, null, null, null) } ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }

    fun bitmapToFile(bitmap: Bitmap, path: String): File{
        var file = File(path)
        var out: OutputStream? = null
        try{
            file.createNewFile()
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }finally{
            out?.close()
        }
        return file
    }

}