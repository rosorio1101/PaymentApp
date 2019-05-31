package com.rosorio.paymentapp.flow.issuer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.paymentapp.R
import kotlinx.android.synthetic.main.fragment_select_issuer_flow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope

class SelectIssuerFlowFragment : Fragment(),
    SelectIssuerFlowContract.View,
    SelectIssuerAdapter.OnIssuerListener {

    override lateinit var paymentMethod: PaymentMethod

    private var listener: SelectIssuerFlowContract.OnIssuerListener? = null

    private val presenter: SelectIssuerFlowContract.Presenter by currentScope.inject()
    private val adapter: SelectIssuerAdapter by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.view = this
        adapter.listener = this

    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            presenter.getAllIssuers(paymentMethod)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_issuer_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if(context is SelectIssuerFlowContract.OnIssuerListener) context else null
    }

    override fun onSelected(issuer: Issuer) {
        listener?.onIssuerSelected(issuer)
    }

    override fun showIssuers(issuers: List<Issuer>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.issuers = IssuerModel.from(issuers)
        }
    }

    private fun configureRecyclerView() = issuersRecyclerView.run {
        layoutManager = LinearLayoutManager(this@SelectIssuerFlowFragment.context, RecyclerView.VERTICAL, false)
        adapter = this@SelectIssuerFlowFragment.adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = SelectIssuerFlowFragment()
    }
}
