package com.example.drawing

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.nvt.color.ColorPickerDialog

class MainActivity : AppCompatActivity() {


    private var drawingView: DrawingView?=null

    private var mImageButtonCurrentPaint: ImageButton? = null

    private var mBtnGallery: ImageButton? = null

    private var ibSelectedColor: ImageButton?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        drawingView=findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(10.toFloat())

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

        val ibColorPicker = findViewById<ImageButton>(R.id.ib_color_picker)
        ibColorPicker.setOnClickListener {
            onColorPicker()
        }

        val curColor=drawingView?.getColor()
        ibSelectedColor=findViewById(R.id.ib_selected_color)
        ibSelectedColor?.setBackgroundColor(curColor as Int)


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

    private fun onColorPicker(){
        val colorPicker = ColorPickerDialog(
            this,
            drawingView!!.getColor(), // color init
            true, // true is show alpha
            object : ColorPickerDialog.OnColorPickerListener {
                override fun onCancel(dialog: ColorPickerDialog?) {
                    // handle click button Cancel

                }

                override fun onOk(dialog: ColorPickerDialog?, colorPicker: Int) {
                    // handle click button OK
                    drawingView?.setColorWithColorPicker(colorPicker)
                    Toast.makeText(applicationContext,"$colorPicker",Toast.LENGTH_SHORT).show()
                    ibSelectedColor?.setBackgroundColor(colorPicker)
                }
            })
        colorPicker.show()
    }


    override fun onBackPressed() {
        finish()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}