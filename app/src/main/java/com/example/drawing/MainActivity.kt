package com.example.drawing

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {


    private var drawingView: DrawingView?=null

    private var mImageButtonCurrentPaint: ImageButton? = null

    private var mBtnGallery: ImageButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView=findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_color_selector)


        mImageButtonCurrentPaint=linearLayoutPaintColors[0] as ImageButton

        mBtnGallery = findViewById(R.id.ib_gallery)
        mBtnGallery?.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivity(intent)

        }

        val ibBrush:ImageButton=findViewById(R.id.ib_brush)
        ibBrush.setOnClickListener{
            showBrushSizeChooserDialog()
        }



    }



    fun paintClicked(view: View){
        if(view !== mImageButtonCurrentPaint){
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_normal)
            )
            mImageButtonCurrentPaint=view as ImageButton
            val imageButton=view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)
        }

        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
        )
    }


    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size: ")
        val smallBtn: ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener{
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumButton=brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        mediumButton.setOnClickListener{
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        val largeButton=brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        largeButton.setOnClickListener{
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }


    override fun onBackPressed() {
        finish()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}