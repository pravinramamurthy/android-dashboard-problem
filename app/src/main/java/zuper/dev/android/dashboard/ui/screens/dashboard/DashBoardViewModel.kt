package zuper.dev.android.dashboard.ui.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.JobApiModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _jobsStateFlow: MutableStateFlow<List<JobApiModel>> = MutableStateFlow(emptyList())
    val jobsStateFlow: StateFlow<List<JobApiModel>> = _jobsStateFlow.asStateFlow()

    init {
        observeJobs()
    }

    private fun observeJobs() {
        viewModelScope.launch {
            dataRepository.observeJobs()
            dataRepository.observeJobs()
                .onEach { jobs ->
                    _jobsStateFlow.value = jobs
                }

        }

        Log.d("DashBoardViewModel","data ${_jobsStateFlow} ")


    }

}