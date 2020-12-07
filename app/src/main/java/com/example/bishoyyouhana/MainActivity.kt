package com.example.bishoyyouhana

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun refresh(): Unit{
            val stringBuilder: StringBuilder = StringBuilder()
            //val name = filenameText.text.toString()
            //val file = File(filesDir,name.trim())
            val files  = fileList()
            for(i in files) {
                stringBuilder.append(i)
                stringBuilder.append("\n")
            }
            displayText.clearComposingText()
            displayText.setText(stringBuilder.toString()).toString()
        }
        refresh()
        buttonSave.setOnClickListener{
            val stringBuilder: StringBuilder = StringBuilder()
            val fileName = filenameText.text.toString()
            val data = dataText.text.toString()

            val fileOut: FileOutputStream
            val cal: Calendar = Calendar.getInstance()

            try {
                fileOut = openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOut.write(data.toByteArray())

                //fileOut.write("\n\n\n\n".toByteArray())
                stringBuilder.append("\r\n -- ")
                stringBuilder.append(cal.time)

                fileOut.write((stringBuilder.toString()).toByteArray())
            }
            catch(e:Exception){
                e.printStackTrace()
            }
            refresh()
        }

        displayButton.setOnClickListener{
            val name = filenameText.text.toString()

            if(name.toString() != null && name.trim() !=""){
                var fileIn : FileInputStream? = null
                try {
                    fileIn = openFileInput(name)

                    var inReader: InputStreamReader = InputStreamReader(fileIn)
                    val bufferedReader: BufferedReader = BufferedReader(inReader)

                    val stringBuilder: StringBuilder = StringBuilder()
                    var text: String? = null

                    while ({ text = bufferedReader.readLine(); text }() != null) {
                        stringBuilder.append(text)
                    }

                    dataText.setText(stringBuilder.toString()).toString()
                }catch(e:Exception){
                    //print smth
                    e.printStackTrace()
                }
            }
            else{ }

        }

        deleteButton.setOnClickListener {
            val name = filenameText.text.toString()
            val file = File(filesDir, name.trim())

            if (file.exists()) {
                file.delete()
            }
            refresh()
        }

        weatherButton.setOnClickListener{
            startActivity(Intent(this, Weather::class.java))
        }

    }
}