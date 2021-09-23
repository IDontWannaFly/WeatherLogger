package com.mark.weatherlogger.ui.base

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import com.mark.weatherlogger.databinding.DialogLoaderBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    private val listeners = arrayListOf<OnDestroyListener>()

    private var loaderDialog: Dialog? = null

    fun showToast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, length).show()
    }

    fun showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, length).show()
    }

    override fun onDestroy() {
        listeners.forEach {
            it.onDestroy()
        }
        super.onDestroy()
    }

    fun toListenDestroy(listener: OnDestroyListener){
        listeners.add(listener)
    }

    open fun setLoading(value: Boolean){
        if(!value) {
            loaderDialog?.dismiss()
            return
        }

        if(loaderDialog?.isShowing == false) {
            loaderDialog?.show()
            return
        } else if(loaderDialog != null)
            return

        loaderDialog = Dialog(this)
        val dialogBinding = DialogLoaderBinding.inflate(layoutInflater)
        loaderDialog?.setContentView(dialogBinding.root)
        loaderDialog?.setCancelable(true)
        loaderDialog?.setOnCancelListener {
            onBackPressed()
        }
        loaderDialog?.setCanceledOnTouchOutside(false)
        loaderDialog?.show()
    }

    interface OnDestroyListener{
        fun onDestroy()
    }
}