package com.koudeer.labelsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.koudeer.labels.LabelData
import com.koudeer.labels.LabelsView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var labelsView: LabelsView<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        labelsView = findViewById(R.id.mlabelsview)

        val mutableList = mutableListOf<LabelData<String>>()
        mutableList.add(LabelData("Java", "1"))
        mutableList.add(LabelData("Java", "2"))
        mutableList.add(LabelData("Java", "3"))
        mutableList.add(LabelData("Java", "4"))
        mutableList.add(LabelData("Java", "5"))
        mutableList.add(LabelData("Java", "6"))
        mutableList.add(LabelData("Java", "7"))
        labelsView.setLabels(mutableList)

        labelsView.setLabelChangeListener(object : LabelsView.OnLabelChangeListener<String> {
            override fun onChange(list: List<String>) {
                Log.d(TAG, "onChange: $list")
            }
        })
    }
}