package com.app.reelshort.Utils

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object ShareUtils {
    suspend fun shareApp(
        context: Context,
        drawableResId: Int,
        title: String,
        description: String,
        appLink: String
    ) {
        withContext(Dispatchers.Default) {
            val imageUri = getUriForDrawable(context, drawableResId)

            if (imageUri == null) {
                Toast.makeText(context, "Failed to prepare image for sharing.", Toast.LENGTH_SHORT)
                    .show()
            }

            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareMessage = """
         $title
         
         $description
         $appLink
         """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, description)

            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)


            shareIntent.setType("image/png")
            val clipData = ClipData.newUri(
                context.contentResolver,
                "Image and Link",
                imageUri
            )

            shareIntent.clipData = clipData

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            withContext(Dispatchers.Main) {
                context.startActivity(Intent.createChooser(shareIntent, "Share App via"))
            }
        }
    }

    /**
     * Converts a drawable resource to a temporary file and returns its FileProvider URI.
     */
    private fun getUriForDrawable(context: Context, drawableResId: Int): Uri? {
        try {
            var bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)

            if (bitmap == null) {
                try {
                    val bitmapDrawable = context.resources.getDrawable(
                        drawableResId, context.theme
                    ) as BitmapDrawable
                    bitmap = bitmapDrawable.bitmap
                } catch (e: ClassCastException) {
                    return null
                }
            }


            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs() // Make sure the directory exists
            val file = File(cachePath, "shared_image.png")

            val outputStream = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        } catch (e: IOException) {
            return null
        }
    }
}