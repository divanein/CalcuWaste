package com.alvintio.calcuwaste.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(

    @field:SerializedName("result")
	val result: Result,

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class Result(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
) : Parcelable
