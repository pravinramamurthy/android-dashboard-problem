package zuper.dev.android.dashboard.ui.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.ChartData
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {


    private val _jobsStateFlow: MutableStateFlow<List<ChartData>> = MutableStateFlow(emptyList())
    val jobsStateFlow: StateFlow<List<ChartData>> = _jobsStateFlow.asStateFlow()

    //Get Jobs from repo
    fun observeJobs() {
        viewModelScope.launch {
            val response = dataRepository.observeJobs()
            response.collect {
                _jobsStateFlow.emit(calculateChartData(it))
            }
        }
    }

    //Get Invoices from repo
    private val _invoiceStateFlow: MutableStateFlow<List<ChartData>> =
        MutableStateFlow(emptyList())
    val invoiceStateFlow: StateFlow<List<ChartData>> = _invoiceStateFlow.asStateFlow()
    fun observeInvoice() {
        viewModelScope.launch {
            val response = dataRepository.observeInvoices()
            response.collect {
                _invoiceStateFlow.emit(calculateChartData(it))
            }
        }
    }

    // This function is to calculate all chart related data
    private inline fun <reified T> calculateChartData(data: List<T>): List<ChartData> {
        val chartResponseList: MutableList<ChartData> = mutableListOf()
        Log.d("viewMode", "calculateChartData: $data ")
        val statusCountMap = when (T::class) {
            JobApiModel::class -> {
                (data as? List<JobApiModel>)?.groupingBy { it.status }
                    ?.eachCount()

            }

            InvoiceApiModel::class -> {
                (data as? List<InvoiceApiModel>)?.groupingBy { it.status }
                    ?.eachCount()
            }

            else -> null
        }
        val totalSum = when (T::class) {
            JobApiModel::class -> {
                val jobList = data as? List<JobApiModel>
                "${jobList?.size ?: 0} Jobs"
            }

            InvoiceApiModel::class -> {
                val invoiceList = data as? List<InvoiceApiModel>
                "Total Value ($${invoiceList?.sumOf { it.total } ?: 0})"
            }

            else -> "0"
        }
        val paidAmount = when (T::class) {
            JobApiModel::class -> {
                val jobList = data as? List<JobApiModel>
                "${
                    (jobList?.count { it.status == JobStatus.Completed })
                } out of ${(jobList?.size)}"
            }

            InvoiceApiModel::class -> {
                val invoiceList = data as? List<InvoiceApiModel>
                "$${
                    (invoiceList
                        ?.filter { it.status.equals(InvoiceStatus.Paid) }
                        ?.sumOf { it.total }) ?: 0
                } Collected"
            }

            else -> "0"
        }

        Log.d("viewMode", "calculateChartData: $data ")
        statusCountMap?.let { map ->
            val totalCount = map.values.sum()

            map.forEach { (status, count) ->
                val percentage = (count.toDouble() / totalCount) * 100
                val chartData = ChartData(
                    value = percentage.toFloat(),
                    status = status.name,
                    count = count.toString(),
                    total = totalSum,
                    completion = paidAmount
                )
                chartResponseList += chartData
            }
        }

        return chartResponseList
    }


}