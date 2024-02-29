package di

import org.koin.dsl.module
import plataform.DeviceInfo

val deviceInfoModule = module {
    single<Pair<Int, Int>> {
        DeviceInfo().getScreenResolution()
    }
}