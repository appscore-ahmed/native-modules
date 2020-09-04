package com.reactnativeawesomemodule
import android.widget.Toast
import com.facebook.react.bridge.*
class ToastModule(reactContext: ReactApplicationContext) :
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
}