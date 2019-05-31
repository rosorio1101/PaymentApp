package com.rosorio.paymentapp.flow.amount

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rosorio.paymentapp.R
import kotlinx.android.synthetic.main.fragment_select_amount_flow.*


class SelectAmountFlowFragment : Fragment(), SelectAmountFlowContract.View {

    private var onAmountListener: SelectAmountFlowContract.OnAmountListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_amount_flow, container, false)
    }

    override fun onResume() {
        super.onResume()
        configureTextWatcher()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SelectAmountFlowContract.OnAmountListener) {
            onAmountListener = context
        }
    }

    private fun configureTextWatcher() = amount.apply {
        addTextChangedListener(amountTextWatcher)
    }

    private val amountTextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val amount = if (s?.toString()?.isBlank()!!) 0 else s.toString().toInt()
            onAmountListener?.onAmountChange(amount)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectAmountFlowFragment()
    }
}
