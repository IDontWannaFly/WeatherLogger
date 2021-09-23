package com.mark.weatherlogger.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mark.weatherlogger.Application
import com.mark.weatherlogger.view_models.BaseViewModel
import javax.inject.Inject

abstract class BaseMVVMActivity<VM: BaseViewModel, V: ViewDataBinding> : BaseActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    @Inject
    lateinit var application: Application

    lateinit var binding: V
    lateinit var viewModel: VM

    abstract val layoutId: Int
    abstract val viewModelClass: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initViewModel()
        assignVMToBinding()

        initOthers()
    }

    protected fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(viewModelClass)
        initViewModelVariables()
        if(viewModel.isLoading.hasObservers())
            return
        viewModel.isLoading.observe(this, {
            setLoading(it)
        })
        viewModel.errorEvent.observe(this) {
            processErrorEvent(it)
        }
    }

    protected fun processErrorEvent(errorEvent: BaseViewModel.ErrorEvent) {
        when (errorEvent) {
            is BaseViewModel.ErrorEvent.OnError -> onError(errorEvent)
            is BaseViewModel.ErrorEvent.OnServerError -> onServerError(errorEvent)
            is BaseViewModel.ErrorEvent.OnCrucialError -> onCrucialError(errorEvent)
        }
    }

    protected fun onError(errorEvent: BaseViewModel.ErrorEvent.OnError) {
        Toast.makeText(this, errorEvent.msg, Toast.LENGTH_LONG).show()
    }

    protected fun onServerError(errorEvent: BaseViewModel.ErrorEvent.OnServerError) {
        Toast.makeText(this, errorEvent.msg, Toast.LENGTH_LONG).show()
    }

    protected fun onCrucialError(errorEvent: BaseViewModel.ErrorEvent.OnCrucialError) {
        Toast.makeText(this, errorEvent.msg, Toast.LENGTH_LONG).show()
    }

    protected fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initDataBindingVariables()
    }

    abstract fun initDataBindingVariables()

    abstract fun initViewModelVariables()

    abstract fun assignVMToBinding()

    abstract fun initOthers()

}