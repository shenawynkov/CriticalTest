package ae.digitalwise.jadeer.networking.models.safeCall

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.errorhandler.Errors

fun <T>  handleRegisterError(
    error: ResultWrapper<T>,
    message: MutableLiveData<String>,
    status: MutableLiveData<String>? = null,
    pariority: ErrorsPariority = ErrorsPariority.MEDIUM
) {
    when (error) {
        is ResultWrapper.NetworkError -> {
            if (!pariority.equals(ErrorsPariority.LOW))
                status?.value = "networkError"


        }
        is ResultWrapper.GenericError -> {
           // Log.d("ApiResponse", error.error?.message + error.error?.status)
            error.error?.message?.let { message.value = it }
            status?.value = error.error?.status



        }
    }

}


