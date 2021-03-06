package com.dinosys.sportbook.application

import android.app.Application
import com.dinosys.sportbook.di.authentication.AuthenticationComponent
import com.dinosys.sportbook.di.authentication.DaggerAuthenticationComponent
import com.dinosys.sportbook.di.tournament.DaggerTournamentComponent
import com.dinosys.sportbook.di.tournament.TournamentComponent

class SportbookApp: Application() {

    companion object {
        lateinit var authComponent: AuthenticationComponent
        lateinit var tournamentComponent: TournamentComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponents()
    }

    fun initComponents() {
        authComponent = DaggerAuthenticationComponent
                    .builder().build()
        tournamentComponent = DaggerTournamentComponent
                .builder().build()
    }
}