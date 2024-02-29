package germano.guilherme.automorama2

import android.app.Application
import di.initKoin
import germano.guilherme.automorama2.di.deviceInfoModule
import germano.guilherme.automorama2.di.driverFactoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication: Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        initKoin(listOf(driverFactoryModule, deviceInfoModule)) {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }
    
}