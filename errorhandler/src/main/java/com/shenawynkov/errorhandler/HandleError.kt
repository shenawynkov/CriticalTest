package ae.digitalwise.jadeer.networking.models.safeCall

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.errorhandler.Errors

fun <T> handleApiError(
    error: ResultWrapper<T>,
    status: MutableLiveData<Int>,
    message: MutableLiveData<String>,
    pariority: ErrorsPariority = ErrorsPariority.MEDIUM
) {
    when (error) {
        is ResultWrapper.NetworkError -> {
            if (!pariority.equals(ErrorsPariority.LOW)) {
                status.value = Errors.NETWORKERROR.code
                Log.d("ApiResponse", Errors.NETWORKERROR.toString())


            }

        }
        is ResultWrapper.GenericError -> {
            Log.d("ApiResponse", error.error?.message + error.error?.status)
            message.value = error.error?.message
            status.value = error.code
        }
    }

}

enum class ErrorsPariority {

    HIGH, MEDIUM, LOW
}
