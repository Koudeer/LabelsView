package com.koudeer.labelsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseIntArray
import android.util.SparseLongArray
import com.koudeer.labels.LabelsView

class MainActivity : AppCompatActivity() {

    private var labelsView: LabelsView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        labelsView = findViewById(R.id.mlabelsview)

        val mutableList = mutableListOf<String>()
        mutableList.add("Java")
        mutableList.add("Java1")
        mutableList.add("Java2")
        mutableList.add("Java3")
        mutableList.add("Java3")
        mutableList.add("Java3")
        mutableList.add("Java3")
        mutableList.add("Java3")
        mutableList.add("Java3")

        labelsView?.setLabels(mutableList)
    }
}