package com.example.musicstore.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.musicstore.service.LoginInterface
import com.example.musicstore.R
import com.example.musicstore.data.Type
import com.example.musicstore.data.Address
import com.example.musicstore.service.DataBaseSupport

class LoggedInFragment : Fragment() {
    private lateinit var currentAddress: Address

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_loged_in, container, false)
        val logout: Button = view.findViewById(R.id.logoutButton)
        val update: Button = view.findViewById(R.id.updateData)
        val refresh: Button = view.findViewById(R.id.refreshData)
        val town: EditText = view.findViewById(R.id.cityLabel)
        val street: EditText = view.findViewById(R.id.streetLabel)
        val number: EditText = view.findViewById(R.id.numberBuild)
        val code: EditText = view.findViewById(R.id.codeLabel)

        if (LoginInterface.getType() != Type.K) {
            refresh.isEnabled = false
            update.isEnabled = false
        }

        view.post{
            context?.let { it1 -> DataBaseSupport.getAddressBase(it1) }
        }

        logout.setOnClickListener {
            Toast.makeText(context, "Log out", Toast.LENGTH_SHORT).show()
            LoginInterface.setStatus(false)
            LoginInterface.setType(Type.K)
            LoginInterface.setAddressState(false)
            LoginInterface.setClientID(0)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        update.setOnClickListener {
            context?.let { it1 ->
                DataBaseSupport.updateData(
                    it1, town.text.toString(),
                    street.text.toString(), number.text.toString(), code.text.toString()
                )
            }
        }

        refresh.setOnClickListener {
            context?.let { it1 -> DataBaseSupport.getAddressBase(it1) }
            currentAddress = DataBaseSupport.getAddress()
            town.text = Editable.Factory.getInstance().newEditable(currentAddress.miasto)
            street.text = Editable.Factory.getInstance().newEditable(currentAddress.ulica)
            number.text = Editable.Factory.getInstance().newEditable(currentAddress.numer_domu.toString())
            code.text = Editable.Factory.getInstance().newEditable(currentAddress.kod_pocztowy)

            if (!town.text.equals("") && !street.text.equals("") && !number.text.equals("") && !code.text.equals(""))
                LoginInterface.setAddressState(true)
        }
        return view
    }
}