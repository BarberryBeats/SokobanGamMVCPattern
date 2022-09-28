package com.example.sokobangamemvcpattern

import android.graphics.Bitmap

class Canvas(view: Bitmap) {



    fun loadBoy(){
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
         val canvas = Canvas(bitmap)
    }


}