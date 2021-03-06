package com.github.snuffix.songapp.data

import com.github.snuffix.songapp.data.mapper.SongsEntityMapper
import com.github.snuffix.songapp.data.repository.NoConnectivityException
import com.github.snuffix.songapp.data.repository.RemoteException
import com.github.snuffix.songapp.data.repository.SongsLocalSource
import com.github.snuffix.songapp.data.repository.SongsRemoteSource
import com.github.snuffix.songapp.domain.model.Result
import com.github.snuffix.songapp.domain.model.Song
import com.github.snuffix.songapp.domain.repository.SongsRepository
import kotlinx.coroutines.CancellationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val QUERY_LIMIT = 50

class SongsRepositoryImpl constructor(
    private val mapper: SongsEntityMapper,
    private val remoteSource: SongsRemoteSource,
    private val localSource: SongsLocalSource
) : SongsRepository {

    override suspend fun getRemoteSongs(query: String, offset: Int): Result<List<Song>> {
        val result = getResult { remoteSource.getSongs(query = query, offset = offset, limit = QUERY_LIMIT) }

        return result.whenOkReturn { songs ->
            songs.map { mapper.mapFromEntity(it, isFromRemote = true) }
        }
    }

    override suspend fun getLocalSongs(query: String, offset: Int): Result<List<Song>> {
        val result = getResult { localSource.getSongs(query = query, offset = offset, limit = QUERY_LIMIT) }
        Result

        return result.whenOkReturn { songs ->
            songs.map { mapper.mapFromEntity(it, isFromRemote = false) }
        }
    }
}

suspend fun <T : Any> getResult(sourceCall: suspend () -> T): Result<T> {
    return try {
        Result.Ok(sourceCall())
    } catch (exception: CancellationException) {
        Result.CancelledError()
    } catch (exception: NoConnectivityException) {
        Result.NetworkError(exception = exception)
    } catch (exception: UnknownHostException) {
        Result.NetworkError(exception = exception)
    } catch (exception: ConnectException) {
        Result.NetworkError(exception = exception)
    } catch (exception: SocketTimeoutException) {
        Result.NetworkError(exception = exception)
    } catch (exception: RemoteException) {
        Result.ApiError(exception.code)
    } catch (exception: Exception) {
        Result.Error(exception = exception)
    }
}
