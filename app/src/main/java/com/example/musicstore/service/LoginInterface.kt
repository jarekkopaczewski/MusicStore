package com.example.musicstore.service

import com.example.musicstore.data.Type

class LoginInterface {
    companion object {
        private var login: Boolean = false
        private var type = Type.K
        private var correctAddress: Boolean = false
        private var clientId: Int = 0

        fun getStatus(): Boolean = login
        fun setStatus(state: Boolean) {
            login = state
        }

        fun getType(): Type = type
        fun setType(type: Type) {
            Companion.type = type
        }

        fun getAddressState() = correctAddress
        fun setAddressState(state: Boolean) {
            correctAddress = state
        }

        fun getClientID() = clientId
        fun setClientID(clientId: Int) {
            Companion.clientId = clientId
        }
    }
}