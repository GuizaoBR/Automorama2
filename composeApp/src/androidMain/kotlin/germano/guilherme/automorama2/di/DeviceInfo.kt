package germano.guilherme.automorama2.di

import org.koin.dsl.module
import plataform.DeviceInfo

val deviceInfoModule = module {
    single<Pair<Int, Int>> { DeviceInfo(get()).getScreenResolution() }
}