package com.spbstu.application.ui.feedback

import com.spbstu.application.api.FirebaseFeedbackApi
import com.spbstu.application.base.BaseViewModel

class FeedbackViewModel : BaseViewModel() {

    fun sendData(email: String, message: String) = FirebaseFeedbackApi.sendFeedback(email, message)
}