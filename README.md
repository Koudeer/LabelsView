## LablesView ##

![](https://github.com/Koudeer/LabelsView/blob/master/img/1.png)

Useing

	private lateinit var labelsView: LabelsView<String>

	val mutableList = mutableListOf<LabelData<String>>()
    mutableList.add(LabelData("Java", "1"))
    mutableList.add(LabelData("Java", "2"))
    labelsView.setLabels(mutableList)

	labelsView.setLabelChangeListener(...)

Add the dependency:

    dependencies {
		implementation 'com.github.Koudeer:LabelsView:1.0.0'
	}

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
