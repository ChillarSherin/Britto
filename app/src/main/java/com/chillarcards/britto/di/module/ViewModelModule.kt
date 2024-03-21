package com.chillarcards.britto.di.module

import com.chillarcards.britto.viewmodel.*
import org.koin.dsl.module


/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
val viewModelModule = module {
    single {
        RegisterViewModel(get(), get())
    }
    single {
        OTPViewModel(get(), get())
    }
    single {
        BusinessViewModel(get(), get())
    }
    single {
        ProvinceViewModel(get(), get())
    }
    single {
        BusinessRegisterViewModel(get(), get())
    }
    single {
        ItemViewModel(get(), get())
    }

}