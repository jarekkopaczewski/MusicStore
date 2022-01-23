package com.example.musicstore

enum class Type {
    M, S, K
}

class LoginInterface {
    companion object{
        private var  login: Boolean = false
        private var type = Type.K;
        private var correctAddress : Boolean = false
        private var clientId : Int = 0

        fun getStatus(): Boolean = login
        fun setStatus(state:Boolean)
        {
            this.login = state
        }

        fun getType(): Type = type
        fun setType( type: Type)
        {
            this.type = type
        }

        fun getAddressState() = correctAddress
        fun setAddressState( state: Boolean)
        {
            this.correctAddress = state
        }

        fun getClientID() = clientId
        fun setClientID( clientId: Int)
        {
            this.clientId = clientId
        }
    }
}