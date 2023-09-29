package com.mutondo.weather4sureapp.data

data class Resource<out T>(
    val status: ResourceStatus,
    val data: T?,
    val errorType: ResourceErrorType) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, ResourceErrorType.NONE)
        }

        fun <T> error(errorType: ResourceErrorType, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, errorType)
        }
    }
}

enum class ResourceStatus {
    SUCCESS,
    ERROR
}