package com.rosorio.paymentapp.flow.paymentmethod

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mercadopago.domain.entity.PaymentMethod

import com.rosorio.paymentapp.R
import kotlinx.android.synthetic.main.fragment_select_payment_method_flow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope

class SelectPaymentMethodFlowFragment : Fragment(),
    SelectPaymentMethodFlowContract.View,
SelectPaymentMethodAdapter.OnPaymentMethodListener{

    private var listener: SelectPaymentMethodFlowContract.OnPaymentMethodListener? = null

    private val presenter: SelectPaymentMethodFlowContract.Presenter by currentScope.inject()
    private val adapter: SelectPaymentMethodAdapter by currentScope.inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.view = this
        adapter.listener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_payment_method_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            presenter.getAllPaymentMethods()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if(context is SelectPaymentMethodFlowContract.OnPaymentMethodListener) context else null
    }

    override fun showPaymentMethods(paymentMethods: List<PaymentMethod>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.paymentMethods = PaymentMethodModel.from(paymentMethods)
        }
    }

    override fun onSelected(paymentMethod: PaymentMethod) {
        listener?.onPaymentMethodSelected(paymentMethod)
    }

    private fun configureRecyclerView() = paymentMethodRecyclerView.run {
        layoutManager = LinearLayoutManager(this@SelectPaymentMethodFlowFragment.context, RecyclerView.VERTICAL, false)
        adapter = this@SelectPaymentMethodFlowFragment.adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = SelectPaymentMethodFlowFragment()
    }
}
