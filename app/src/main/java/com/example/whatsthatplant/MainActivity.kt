package com.example.whatsthatplant

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.whatsthatplant.formatters.formatProb
import com.example.whatsthatplant.formatters.formatString
import com.example.whatsthatplant.formatters.formatTwoItems
import com.example.whatsthatplant.imageSelection.TakePhoto
import com.example.whatsthatplant.network.PlantData
import com.example.whatsthatplant.network.PlantIdApi
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var plantDataDisplay: androidx.appcompat.widget.LinearLayoutCompat
    private lateinit var notaPlantDisplay: androidx.appcompat.widget.LinearLayoutCompat
    private lateinit var plantDataErrorDisplay: androidx.appcompat.widget.LinearLayoutCompat
    private lateinit var loadingDisplay: ConstraintLayout
    private lateinit var welcomePageDisplay: ConstraintLayout

    private lateinit var probabilityText: TextView
    private lateinit var commonNameText: TextView
    private lateinit var scientificNameText: TextView
    private lateinit var ediblePartsText: TextView
    private lateinit var propagationMethodsText: TextView
    private lateinit var linkText: TextView
    private lateinit var similarImageView: ImageView

    private lateinit var isPlantProbabilityText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val livePlantButton: Button = findViewById(R.id.livePlantButton)
        val savedPlantButton: Button = findViewById(R.id.savedPlantButton)

        plantDataDisplay = findViewById(R.id.plant_data_layout)
        notaPlantDisplay = findViewById(R.id.not_a_plant_layout)
        plantDataErrorDisplay = findViewById(R.id.plant_data_error_layout)
        loadingDisplay = findViewById(R.id.loading_layout)
        welcomePageDisplay = findViewById(R.id.welcome_page_layout)

        probabilityText = findViewById(R.id.probability_value)
        commonNameText = findViewById(R.id.common_name_value)
        scientificNameText = findViewById(R.id.scientific_name_value)
        ediblePartsText = findViewById(R.id.edible_parts_value)
        propagationMethodsText = findViewById(R.id.propagation_methods_value)
        linkText = findViewById(R.id.wiki_url_value)
        similarImageView = findViewById(R.id.selected_image)

        isPlantProbabilityText = findViewById(R.id.is_a_plant_probability_value)

        livePlantButton.setOnClickListener {
            // launch take photo activity
            val intent = Intent(this, TakePhoto::class.java)
            getPhotoResultLauncher.launch(intent)
        }

        savedPlantButton.setOnClickListener {
            // select photo from device
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getPhotoResultLauncher.launch(intent)
        }

    }

    // Retrieve image in onActivityResult for both captured and selected image
    private val getPhotoResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            var imageUri: Uri? = null

            val isTakePhoto = result.data?.getBooleanExtra("take_photo", false)

            if (isTakePhoto == true) {
                val imageUriStr = result.data?.getStringExtra("image_uri")
                imageUri = Uri.parse(imageUriStr)
            }
            if (isTakePhoto == false) {
                imageUri = result.data?.data // Get the captured image Uri from the intent
            }


            welcomePageDisplay.visibility = View.GONE
            plantDataDisplay.visibility = View.GONE
            notaPlantDisplay.visibility = View.GONE
            plantDataErrorDisplay.visibility = View.GONE
            // loading screen
            loadingDisplay.visibility = View.VISIBLE

            // Call the PlantIdApi to identify the plant
            CoroutineScope(Dispatchers.IO).launch {
                val plantData: PlantData? = PlantIdApi.identifyPlant(this@MainActivity, imageUri)

                // switch to main thread to update UI
                withContext(Dispatchers.Main) {
                    updateUI(plantData)
                }
            }

        }
    }

    private fun updateUI(plantData: PlantData?) {
        loadingDisplay.visibility = View.GONE


        if (plantData != null) {
            val isPlant = plantData.isPlant ?: false
            if (isPlant) {
                val probability = plantData.suggestions?.get(0)?.probability
                val commonName = plantData.suggestions?.get(0)?.plantDetails?.commonNames?.get(0)
                val scientificName = plantData.suggestions?.get(0)?.plantDetails?.scientificName
                val edibleParts = plantData.suggestions?.get(0)?.plantDetails?.edibleParts
                val propagationMethods =
                    plantData.suggestions?.get(0)?.plantDetails?.propagationMethods
                val link = plantData.suggestions?.get(0)?.plantDetails?.url

                val imageUrl = plantData.suggestions?.get(0)?.similarImages?.get(0)?.url
                val placeholderImg = R.drawable.loading_animation
                val errorImg = R.drawable.img_error

                probabilityText.text =
                    resources.getString(R.string.one_string, formatProb(probability))
                commonNameText.text =
                    resources.getString(R.string.one_string, formatString(commonName))
                scientificNameText.text =
                    resources.getString(R.string.one_string, formatString(scientificName))
                ediblePartsText.text =
                    resources.getString(R.string.one_string, formatTwoItems(edibleParts))
                propagationMethodsText.text =
                    resources.getString(R.string.one_string, formatTwoItems(propagationMethods))

                // Truncate and link text
                val maxChars = 35
                val formattedLink = resources.getString(R.string.one_string, formatString(link))
                val truncatedLink = formattedLink.substring(0, maxChars) + "..."
                linkText.text = truncatedLink

                linkText.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedLink))
                    startActivity(intent)
                }


                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(placeholderImg)
                    .error(errorImg)
                    .centerCrop()
                    .into(similarImageView)

                plantDataDisplay.visibility = View.VISIBLE
            } else {
                val isPlantProbability = plantData.isPlantProbability
                isPlantProbabilityText.text = resources.getString(R.string.one_string, formatProb(isPlantProbability))

                notaPlantDisplay.visibility = View.VISIBLE
            }
        }
        else {
            plantDataErrorDisplay.visibility = View.VISIBLE
            Log.e("MainActivity", "Error identifying plant")
        }
    }






}

