package com.example.musicstore

enum class Type {
    M, S, K
}

class LoginInterface {
    companion object{
        private var  login: Boolean = false
        private var type = Type.K;
        private var correctAddress : Boolean = false

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
    }
}