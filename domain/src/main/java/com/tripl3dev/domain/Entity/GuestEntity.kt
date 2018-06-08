package com.tripl3dev.domain.Entity

import com.google.gson.annotations.SerializedName

data class GuestEntity(@SerializedName("success") val isSuccess: Boolean,
                       @SerializedName("guest_session_id") val guestSessionId: String,
                       @SerializedName("expires_at") val expiredAt: String) {
}

