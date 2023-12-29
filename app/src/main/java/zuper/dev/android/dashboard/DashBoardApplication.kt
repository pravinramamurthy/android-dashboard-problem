package zuper.dev.android.dashboard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DashBoardApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}