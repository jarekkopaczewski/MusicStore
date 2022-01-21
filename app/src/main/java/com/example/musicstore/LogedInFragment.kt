package com.example.musicstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogedInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogedInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_loged_in, container, false)
        val logout : Button = view.findViewById(R.id.logoutButton)
        val update : Button = view.findViewById(R.id.updateData)
        val town : EditText = view.findViewById(R.id.cityLabel)
        val street : EditText = view.findViewById(R.id.streetLabel)
        val number : EditText = view.findViewById(R.id.numberBuild)
        val code : EditText = view.findViewById(R.id.codeLabel)

        logout.setOnClickListener {
            Toast.makeText(context, "Log out", Toast.LENGTH_SHORT).show()
            LoginInterface.setStatus(false)
            LoginInterface.setType(Type.K)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        update.setOnClickListener {
            context?.let { it1 -> DataBaseSupport.updateData(it1, town.text.toString(),
                street.text.toString(), number.text.toString(), code.text.toString()) }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogedInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogedInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}