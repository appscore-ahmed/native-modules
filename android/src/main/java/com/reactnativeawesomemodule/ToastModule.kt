package com.reactnativeawesomemodule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.widget.Toast
import com.facebook.react.bridge.*
import java.io.ByteArrayOutputStream


class ToastModule
(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  companion object {
    private const val DURATION_SHORT_KEY = "SHORT"
    private const val DURATION_LONG_KEY = "LONG"
  }

  override fun getName(): String {
    return "ToastExample"
  }

  override fun getConstants(): MutableMap<String, Any> {
    val constants: MutableMap<String, Any> = HashMap()
    constants[DURATION_SHORT_KEY] = Toast.LENGTH_SHORT
    constants[DURATION_LONG_KEY] = Toast.LENGTH_LONG
    return constants
  }

  @ReactMethod
  fun show(message: String, duration: Int) {
    Toast.makeText(reactApplicationContext, message, duration).show()
  }

//  @ReactMethod
//  fun methodWithCallback(message: String, callback: Callback) {
//    callback.invoke(message)
//  }

  @ReactMethod
  fun showLonger(message: String) {
    show(message, Toast.LENGTH_LONG)
  }

  @ReactMethod
  fun returnSomeString(promise: Promise) {
    promise.resolve("This is some string returning from native module")
  }

  @ReactMethod
  fun callbackMethod(a: Int, successCallback: Callback, errorCallback: Callback) {
    try {
      successCallback.invoke(a)
    } catch (e: Exception) {
      errorCallback.invoke(e)
    }
  }

  @ReactMethod
  fun callbackMethodWithPromise(a: Int, promise: Promise) {
    try {
      promise.resolve(a)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  private lateinit var mCameraPromise: Promise

  private val REQUEST_CODE = 1

  private val mActivityEventListener = object : BaseActivityEventListener() {

    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(activity, requestCode, resultCode, data)
      Log.e("ASD", "test")
      if (requestCode == REQUEST_CODE) {
        val image = data?.extras?.get("data") as Bitmap
        Log.e("ASD", "image $image")
        val imageUri = getImageUri(reactApplicationContext, image)
        val path = "file://" + getRealPathFromURI((imageUri))
        Log.e("ASD", "image path $path")
        mCameraPromise.also {
          if (image != null) it.resolve(path) else it.reject("error", "errrrrrr")
        }
      }
    }
  }

  fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
//    val OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true)
//    val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, OutImage, "Title", null)
//    return Uri.parse(path)
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
  }

  fun getRealPathFromURI(uri: Uri?): String? {
    var path = ""
    if (reactApplicationContext.contentResolver != null) {
      val cursor: Cursor = reactApplicationContext.contentResolver.query(uri, null, null, null, null)
      if (cursor != null) {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        path = cursor.getString(idx)
        cursor.close()
      }
    }
    return path
  }

  @ReactMethod
  fun callCamera(promise: Promise) {
    mCameraPromise = promise
    val intent = Intent("android.media.action.IMAGE_CAPTURE");
    reactApplicationContext.startActivityForResult(intent, REQUEST_CODE, null);
  }

  init {
    reactContext.addActivityEventListener(mActivityEventListener)
  }
}
