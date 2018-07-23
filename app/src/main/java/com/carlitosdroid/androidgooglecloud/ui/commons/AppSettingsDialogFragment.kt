package com.carlitosdroid.androidgooglecloud.ui.commons

import android.content.Context
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import com.carlitosdroid.androidgooglecloud.R
import kotlinx.android.synthetic.main.dialog_fragment_app_settings.*

/**
 * @author carlosleonardocamilovargashuaman on 7/23/18.
 */
class AppSettingsDialogFragment : DialogFragment() {

    private var onStorageRtionaleListener: OnCameraRationaleListener? = null

    interface OnCameraRationaleListener {
        fun onAccept()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCameraRationaleListener) {
            onStorageRtionaleListener = activity as? OnCameraRationaleListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        onStorageRtionaleListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_fragment_app_settings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvTitle.text = getString(R.string.open_settings_tap_permissions, arguments!!.getString("title"))

        btnAccept.setOnClickListener { _ ->
            dialog.dismiss()
            onStorageRtionaleListener?.onAccept()
        }
    }

    companion object {
        fun newInstance(title: String): AppSettingsDialogFragment {
            val cameraDialogFragment = AppSettingsDialogFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            cameraDialogFragment.arguments = bundle
            return cameraDialogFragment
        }
    }

}