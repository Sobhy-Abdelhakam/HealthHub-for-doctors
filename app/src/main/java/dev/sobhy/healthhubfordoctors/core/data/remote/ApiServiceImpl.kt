package dev.sobhy.healthhubfordoctors.core.data.remote

import dev.sobhy.healthhubfordoctors.authfeature.domain.model.DoctorRequest
import dev.sobhy.healthhubfordoctors.core.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ApiServiceImpl(
    private val httpClient: HttpClient,
) : ApiService {
    @OptIn(InternalAPI::class)
    override fun addDoctor(doctor: DoctorRequest): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            val result =
                httpClient.post("/doctors") {
                    contentType(ContentType.Application.Json)
                    body = doctor
                }
            emit(Resource.Success("success"))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}
