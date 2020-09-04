package com.reactnativeawesomemodule

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class AwesomeModuleModule(reactContext: ReactApplicationContext) 
: ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "AwesomeModule"
  }

  // Example method
  // See https://facebook.github.io/react-native/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Int, b: Int, promise: Promise) {
    promise.resolve(a * b)
  }

  @ReactMethod
  fun add(a: Int, b: Int, promise: Promise) {
    promise.resolve(a + b)
  }


}
