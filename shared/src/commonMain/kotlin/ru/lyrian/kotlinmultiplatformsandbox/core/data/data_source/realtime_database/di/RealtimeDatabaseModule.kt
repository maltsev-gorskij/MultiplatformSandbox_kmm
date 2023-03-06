package ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.realtime_database.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.core.common.constants.FirebaseConstants

internal val realtimeDatabaseModule = module {
    factory<FirebaseDatabase> { Firebase.database(FirebaseConstants.REALTIME_DATABASE_URL) }
}