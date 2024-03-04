package com.chillarcards.britto.di.module

import com.chillarcards.britto.viewmodel.RegisterViewModel
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

}