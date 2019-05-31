package com.rosorio.paymentapp.payment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PayerCost
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.extensions.hideKeyboard
import com.rosorio.paymentapp.flow.amount.SelectAmountFlowContract
import com.rosorio.paymentapp.flow.issuer.SelectIssuerFlowContract
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostFlowContract
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostFlowFragment
import com.rosorio.paymentapp.flow.paymentmethod.SelectPaymentMethodFlowContract
import kotlinx.android.synthetic.main.activity_payment_flow.*
import kotlinx.android.synthetic.main.payment_empty_view.*
import kotlinx.android.synthetic.main.payment_flow_container.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope

class PaymentFlowActivity : AppCompatActivity(),
    PaymentFlowContract.View,
    SelectAmountFlowContract.OnAmountListener,
    SelectPaymentMethodFlowContract.OnPaymentMethodListener,
    SelectIssuerFlowContract.OnIssuerListener,
    SelectPayerCostFlowContract.OnPayerCostListener {

    private val TAG = "PaymentFlowActivity"

    private val presenter: PaymentFlowContract.Presenter by currentScope.inject()

    private val paymentFlowAdapter: PaymentFlowAdapter by currentScope.inject()

    private val selectAmountFlowView: SelectAmountFlowContract.View by currentScope.inject()
    private val selectPaymentMethodFlowFlowView: SelectPaymentMethodFlowContract.View by currentScope.inject()
    private val selectIssuerFlowView: SelectIssuerFlowContract.View by currentScope.inject()
    private val selectPayerCostFlowView: SelectPayerCostFlowContract.View by currentScope.inject()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<android.view.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_flow)

        presenter.view = this

        configureViews()

    }

    private fun configureViews() {

        configureBottomSheetBehavior()

        configureCreatePaymentFlow()

        configureNextStep()

        configureRecyclerView()
    }


    private fun configureBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(paymentFlowLayout)
        bottomSheetBehavior.run {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun configureCreatePaymentFlow() = createPaymentFlow.run {
        viewTreeObserver.addOnGlobalLayoutListener {
            bottomSheetBehavior.peekHeight = createPaymentFlow.height
        }

        setOnClickListener {
            if (!presenter.startPaymentFlow()) {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> openPaymentFlowStepContainer()
                    BottomSheetBehavior.STATE_EXPANDED -> closePaymentFlowStepContainer()
                    else -> BottomSheetBehavior.STATE_HIDDEN
                }
            } else {
                getPaymentFlows()
                presenter.nextStep()
            }
        }
    }

    private fun configureNextStep() = nextStep.run {
        setOnClickListener {
            hideKeyboard()
            presenter.nextStep()
        }
    }


    private fun configureRecyclerView() = paymentFlowRecyclerView.run {
        layoutManager = LinearLayoutManager(this@PaymentFlowActivity, RecyclerView.VERTICAL, false)
        adapter = paymentFlowAdapter
    }

    override fun onStart() {
        super.onStart()

        getPaymentFlows()

    }

    private fun getPaymentFlows() {
        CoroutineScope(Dispatchers.IO).launch {
            presenter.getAllPaymentFlows()
        }
    }

    override fun showLoading() {
        Log.i(TAG, "show loading....")
    }

    override fun hideLoading() {
        Log.i(TAG, "hide loading....")
    }

    override fun showPaymentFlow(paymentFlows: List<PaymentFlowModel>) {

        CoroutineScope(Dispatchers.Main).launch {
            emptyViewContainer.visibility = View.GONE

            paymentFlowAdapter.paymentFlowModels = paymentFlows
        }

    }

    override fun showEmptyMessage() {
        CoroutineScope(Dispatchers.Main).launch {
            emptyViewContainer.visibility = View.VISIBLE
        }
    }

    override fun showSelectAmountFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            selectAmountFlowView as Fragment
        ).commit()
    }

    override fun showSelectPaymentMethodFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            selectPaymentMethodFlowFlowView as Fragment
        ).commit()
    }

    override fun showSelectIssuerFlowView(paymentMethod: PaymentMethod) {
        selectIssuerFlowView.paymentMethod = paymentMethod
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            selectIssuerFlowView as Fragment
        ).commit()
    }

    override fun showSelectInstallmentsFlowView(amount: Int, paymentMethod: PaymentMethod, issuer: Issuer) {
        selectPayerCostFlowView.apply { 
            this.amount = amount
            this.paymentMethod = paymentMethod
            this.issuer = issuer
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            selectPayerCostFlowView as Fragment
        ).commit()
    }

    override fun openPaymentFlowStepContainer() {
        createPaymentFlow.text = "Cerrar"
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun closePaymentFlowStepContainer() {
        createPaymentFlow.text = if (presenter.isNewFlow) "Crear Nuevo Pago" else "Continuar Pago"
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun enableNextStepButton() {
        CoroutineScope(Dispatchers.Main).launch {
            nextStep.apply {
                isEnabled = true
            }
        }
    }

    override fun disableNextStepButton() {
        CoroutineScope(Dispatchers.Main).launch {
            nextStep.apply {
                isEnabled = false
            }
        }
    }

    override fun onAmountChange(amount: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            presenter.updateAmount(amount)
        }
    }

    override fun onPaymentMethodSelected(paymentMethod: PaymentMethod) {
        CoroutineScope(Dispatchers.IO).launch {
            presenter.updatePaymentMethod(paymentMethod)
        }
    }

    override fun onIssuerSelected(issuer: Issuer) {
        CoroutineScope(Dispatchers.IO).launch {
            presenter.updateIssuer(issuer)
        }
    }

    override fun onPayerCostSelected(payerCost: PayerCost) {
        CoroutineScope(Dispatchers.IO).launch {
            presenter.updatePayerCost(payerCost)
        }
    }
}