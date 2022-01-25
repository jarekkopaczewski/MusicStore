package com.example.musicstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.musicstore.R
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.DataBaseSupport

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        val name: EditText = view.findViewById(R.id.nameLabelReg)
        val secName: EditText = view.findViewById(R.id.secondNameLabelReg)
        val email: EditText = view.findViewById(R.id.editTextTextPersonNameReg)
        val phone: EditText = view.findViewById(R.id.phoneLabelReg)
        val city: EditText = view.findViewById(R.id.cityLabelReg)
        val street: EditText = view.findViewById(R.id.streetLabelReg)
        val buildNumber: EditText = view.findViewById(R.id.numberBuildReg)
        val postCode: EditText = view.findViewById(R.id.codeLabelReg)
        val login: EditText = view.findViewById(R.id.loginRe)
        val pass: EditText = view.findViewById(R.id.passReg)
        val confirm: Button = view.findViewById(R.id.confirmReButton)

        confirm.setOnClickListener {
            AnimateView.animateInOut(confirm, context)
            if (name.text.isNotEmpty() && secName.text.isNotEmpty() && email.text.isNotEmpty() &&
                phone.text.isNotEmpty() && street.text.isNotEmpty() && buildNumber.text.isNotEmpty() && postCode.text.isNotEmpty()
            ) {

                context?.let { it1 ->
                    DataBaseSupport.addUser(
                        it1,
                        name.text.toString(),
                        secName.text.toString(),
                        email.text.toString(),
                        phone.text.toString(),
                        city.text.toString(),
                        street.text.toString(),
                        buildNumber.text.toString(),
                        postCode.text.toString(),
                        login.text.toString(),
                        pass.text.toString()
                    )
                }

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .remove(this)
                    .commit()
                requireActivity().supportFragmentManager.popBackStack()
            }
            else
            {
                Toast.makeText(context, "Fill the data!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}