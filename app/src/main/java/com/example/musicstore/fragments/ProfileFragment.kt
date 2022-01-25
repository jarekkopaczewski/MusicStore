package com.example.musicstore.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.annotation.RequiresApi
import com.example.musicstore.service.LoginInterface
import com.example.musicstore.R
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.DataBaseSupport

class ProfileFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val switch: Switch = view.findViewById(R.id.employeeLogin)
        val login: Button = view.findViewById(R.id.loginButton)
        val register: Button = view.findViewById(R.id.registerButton)
        val email: EditText = view.findViewById(R.id.editTextTextEmailAddress)
        val pass: EditText = view.findViewById(R.id.editTextTextPassword)
        email.text.clear()
        pass.text.clear()

        login.setOnClickListener {
            AnimateView.animateInOut(login, context)
            if (!switch.isChecked) {
                this.context?.let { it1 ->
                    DataBaseSupport.checkLogin(
                        it1,
                        email.text.toString(),
                        pass.text.toString()
                    )
                }
                view.post {
                    if (LoginInterface.getStatus()) {
                        activity!!.supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view.parent as ViewGroup).id, LoggedInFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            } else {
                this.context?.let { it1 ->
                    DataBaseSupport.checkEmployeeLogin(
                        it1,
                        email.text.toString(),
                        pass.text.toString()
                    )
                }
                if (LoginInterface.getStatus()) {
                    activity!!.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace((view.parent as ViewGroup).id, LoggedInFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        register.setOnClickListener {
            AnimateView.animateInOut(register, context)
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace((view.parent as ViewGroup).id, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}