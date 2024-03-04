package com.chillarcards.britto.di.module

import com.chillarcards.britto.data.api.ApiHelper
import com.chillarcards.britto.data.api.ApiHelperImpl
import com.chillarcards.britto.data.repository.AuthRepository
import org.koin.dsl.module

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */

val repoModule = module {
    single {
        AuthRepository(get())
    }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}