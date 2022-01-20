package com.example.musicstore

enum class Type {
    M, S, K                           // 1
}
// magazyn, sklep, klient

class LoginInterface {
    companion object{
        private var  login: Boolean = false
        private var type = Type.K;

        fun getStatus(): Boolean = login
        fun setStatus(state:Boolean)
        {
            this.login = state
        }

        fun setType( type: Type)
        {
            this.type = type
        }
    }

}