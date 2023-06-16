package com.alvintio.calcuwaste.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.utils.Utils.uriToFile
import com.alvintio.calcuwaste.databinding.ActivityCalculateBinding
import com.alvintio.calcuwaste.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding
    private var getFile: File?= null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCameraX.setOnClickListener { startCameraX() }

        binding.btnGallery.setOnClickListener { startGallery() }

        binding.btnCalculate.setOnClickListener {
            startCalculate()
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            binding.tvCarbonEmissions.text = result
            binding.tvEstimatedProfit.text = result
            binding.tvCategory.text = result
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

//            myFile?.let { file ->
//                rotateFile(file, isBackCamera)
//                getFile = file
//                binding.imageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
            myFile?.let { file ->
                binding.imageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                getFile = uriToFile(uri, this@CalculateActivity)
                binding.imageView.setImageURI(uri)
            }
        }
    }

    private fun startCalculate() {
        if (getFile != null){
            val model = Model.newInstance(this)
            val labelsModel = getLabelsModel()
            val labelsPrice = getLabelsPrice()
            val emissionFactor = getEmissionFactor()

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val bitmap = BitmapFactory.decodeFile(getFile?.path)
            val resize = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(resize)

            inputFeature0.loadBuffer(tensorImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var maxIndex = 0
            var maxValue = outputFeature0.getFloatValue(0)
            for (i in 0 until 5){
                val value = outputFeature0.getFloatValue(i)
                if (value > maxValue){
                    maxValue = value
                    maxIndex = i
                }
            }
            val labelModel = labelsModel[maxIndex]
            val labelPriceVal = labelsPrice[maxIndex]
            val emissionFactorVal = emissionFactor[maxIndex]

            binding.tvCategory.text = labelModel

            // CALCULATE
            val inputWeight = binding.edtWeight.text.toString().trim()
            var isEmptyFields = false

            if (inputWeight.isEmpty()) {
                isEmptyFields = true
                binding.edtWeight.error = resources.getString(R.string.required_field)
            }

            // CARBON EMISSION
            if (!isEmptyFields) {
                val carbonEmission = (inputWeight.toDouble() * emissionFactorVal.toDouble())
                binding.tvCarbonEmissions.text = carbonEmission.toString() + resources.getString(R.string.weight)
            }

            // ESTIMATED PROFIT
            if (!isEmptyFields) {
                val estimatedProfit = (inputWeight.toDouble() * labelPriceVal.toDouble())
                binding.tvEstimatedProfit.text = resources.getString(R.string.money) + estimatedProfit.toString()
            }
        } else {
            Toast.makeText(this, resources.getString(R.string.input_image), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLabelsModel(): List<String> {
        val inputString = this.assets.open("labels_model.txt").bufferedReader().use {
            it.readText()
        }
        return inputString.split("\n")
    }
    private fun getLabelsPrice(): List<String> {
        val inputString = this.assets.open("labels_price.txt").bufferedReader().use {
            it.readText()
        }
        return inputString.split("\n")
    }
    private fun getEmissionFactor(): List<String> {
        val inputString = this.assets.open("emission_factor.txt").bufferedReader().use {
            it.readText()
        }
        return inputString.split("\n")
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val STATE_RESULT = "state_result"
    }
}