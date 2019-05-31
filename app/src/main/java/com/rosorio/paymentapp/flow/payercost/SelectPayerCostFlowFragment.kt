package com.rosorio.paymentapp.flow.payercost

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PayerCost
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.flow.FlowAdapter
import kotlinx.android.synthetic.main.fragment_select_payer_cost_flow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope

class SelectPayerCostFlowFragment : Fragment(),
    SelectPayerCostFlowContract.View,
    FlowAdapter.OnDataListener<PayerCostModel> {

    override lateinit var paymentMethod: PaymentMethod
    override lateinit var issuer: Issuer
    override var amount: Int = 0


    private var listener: SelectPayerCostFlowContract.OnPayerCostListener? = null

    private val presenter: SelectPayerCostFlowContract.Presenter by currentScope.inject()
    private val adapter: SelectPayerCostAdapter by currentScope.inject()

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
        return inflater.inflate(R.layout.fragment_select_payer_cost_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            presenter.getPayerCosts(amount, paymentMethod, issuer)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if(context is SelectPayerCostFlowContract.OnPayerCostListener) context else null
    }

    override fun showPayerCosts(payerCosts: List<PayerCost>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.listData = PayerCostModel.from(payerCosts)
        }
    }

    override fun onSelected(data: PayerCostModel) {
        listener?.onPayerCostSelected(data.related)
    }

    private fun configureRecyclerView() = payerCostsRecyclerView.run {
        layoutManager = LinearLayoutManager(this@SelectPayerCostFlowFragment.context, RecyclerView.VERTICAL, false)
        adapter = this@SelectPayerCostFlowFragment.adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = SelectPayerCostFlowFragment()
    }
}
