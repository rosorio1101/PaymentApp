package com.rosorio.paymentapp.payment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.flow.SelectAmountFlowFragment
import com.rosorio.paymentapp.flow.SelectInstallmentsFlowFragment
import com.rosorio.paymentapp.flow.SelectIssuerFlowFragment
import com.rosorio.paymentapp.flow.SelectPaymentMethodFlowFragment
import kotlinx.android.synthetic.main.activity_payment_flow.*
import kotlinx.android.synthetic.main.payment_empty_view.*
import kotlinx.android.synthetic.main.payment_flow_container.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope

class PaymentFlowActivity : AppCompatActivity(), PaymentFlowContract.View {

    private val TAG = "PaymentFlowActivity"

    private val presenter: PaymentFlowContract.Presenter<PaymentFlowActivity> by currentScope.inject()

    private val paymentFlowAdapter: PaymentFlowAdapter by currentScope.inject()

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
        CoroutineScope(Dispatchers.Main).launch {
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
        emptyViewContainer.visibility = View.GONE

        paymentFlowAdapter.paymentFlowModels = paymentFlows
    }

    override fun showEmptyMessage() {
        emptyViewContainer.visibility = View.VISIBLE
    }

    override fun showSelectAmountFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            SelectAmountFlowFragment.newInstance("","")
        ).commit()
    }

    override fun showSelectPaymentMethodFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            SelectPaymentMethodFlowFragment.newInstance("","")
        ).commit()
    }

    override fun showSelectIssuerFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            SelectIssuerFlowFragment.newInstance("","")
        ).commit()
    }

    override fun showSelectInstallmentsFlowView() {
        supportFragmentManager.beginTransaction().replace(
            R.id.flowContainer,
            SelectInstallmentsFlowFragment.newInstance("","")
        ).commit()
    }

    override fun openPaymentFlowStepContainer() {
        createPaymentFlow.text = "Cerrar"
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun closePaymentFlowStepContainer() {
        createPaymentFlow.text = if (presenter.isNewFlow)  "Crear Nuevo Pago" else "Continuar Pago"
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}