package com.brainlux.polyapp.ui.feedback

import com.brainlux.polyapp.api.FirebaseFeedbackApi
import com.brainlux.polyapp.base.BaseViewModel

class FeedbackViewModel : BaseViewModel() {

    fun sendData(email: String, message: String) = FirebaseFeedbackApi.sendFeedback(email, message)
}