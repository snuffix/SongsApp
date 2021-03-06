package com.github.snuffix.songapp.remote.di

import com.github.snuffix.songapp.data.repository.SongsRemoteSource
import com.github.snuffix.songapp.remote.SongsRemoteSourceImpl
import com.github.snuffix.songapp.remote.mapper.RemoteSongsMapper
import com.github.snuffix.songapp.remote.service.ITunesSongServiceFactory
import org.koin.dsl.module

val remoteModule = module {
    single { RemoteSongsMapper() }
    single { ITunesSongServiceFactory.makeService(get(), get()) }
    factory<SongsRemoteSource> { SongsRemoteSourceImpl(get(), get()) }
}

