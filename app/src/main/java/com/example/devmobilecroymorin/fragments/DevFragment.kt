package com.example.devmobilecroymorin.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devmobilecroymorin.R
import kotlinx.android.synthetic.main.dev_fragment.*
import kotlinx.android.synthetic.main.dev_fragment.view.*
import kotlinx.android.synthetic.main.popup.view.*

class DevFragment : Fragment() {

    val KEY_POSITION : String = "position"
    var jsonFile : String = ""
    var mContext : Context? = null

    fun newInstance(position : Int): DevFragment{

        var sf = DevFragment()

        val args : Bundle = Bundle()
        args.putInt(KEY_POSITION, position)
        sf.arguments = args

        return sf
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_show_popup.setOnClickListener {

            val mDialogView = LayoutInflater.from(mContext).inflate(R.layout.popup, null)
            val mBuilder = AlertDialog.Builder(mContext)
                .setView(mDialogView)
                .setTitle("OLIVE")

            val mAlertDialog = mBuilder.show()

            mDialogView.btn_olive.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dev_fragment, null)
            }
}