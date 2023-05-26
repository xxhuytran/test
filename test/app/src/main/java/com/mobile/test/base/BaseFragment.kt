package com.mobile.test.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.test.utils.NavigationCommand

open class BaseFragment: Fragment() {
    private val baseActivity: BaseActivity
        get() = activity as? BaseActivity ?: throw IllegalStateException("BaseFragment's parent should be BaseActivity")
    var viewModel = BaseViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationCommands.observe(viewLifecycleOwner) { command ->
            try {
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.Back -> findNavController().popBackStack()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(
                        command.destinationId,
                        command.inclusive
                    )
                    is NavigationCommand.ToRoot -> findNavController().popBackStack(
                        findNavController().graph.startDestinationId,
                        command.inclusive
                    )
                    is NavigationCommand.StartActivity<*> -> {
                        val intent = Intent(context, command.activityName)
                        intent.putExtras(command.bundle ?: Bundle.EMPTY)
                        baseActivity.startActivity(intent)
                        if (command.isFinishedCurrentActivity) {
                            baseActivity.finish()
                        }
                    }
                    is NavigationCommand.ShowLoadingIndicator -> {
                        if (command.isShowing) {
                            showLoadingIndicator()
                        } else {
                            hideLoadingIndicator()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(this.javaClass.simpleName, e.toString())
            }
        }

        view.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun showLoadingIndicator() {
        baseActivity.showLoadingIndicator(true)
    }

    private fun hideLoadingIndicator() {
        baseActivity.showLoadingIndicator(false)
    }

    fun hideKeyboard() {
        baseActivity.hideKeyboard()
    }
}