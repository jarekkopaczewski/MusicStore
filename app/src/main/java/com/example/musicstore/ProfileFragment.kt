package com.example.musicstore

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        val switch : Switch = view.findViewById(R.id.employeeLogin)
        val login : Button = view.findViewById(R.id.loginButton)
        val register : Button = view.findViewById(R.id.registerButton)
        val email : EditText = view.findViewById(R.id.editTextTextEmailAddress)
        val pass : EditText = view.findViewById(R.id.editTextTextPassword)
        email.text.clear()
        pass.text.clear()

        login.setOnClickListener {
            animateInOut(login)
            if(!switch.isChecked)
            {
                this.context?.let { it1 -> DataBaseSupport.checkLogin(it1, email.text.toString(), pass.text.toString()) }
                view.post(Runnable {
                    if(LoginInterface.getStatus())
                    {
                        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view!!.parent as ViewGroup).id, LogedInFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                })
            }
            else
            {
                this.context?.let { it1 -> DataBaseSupport.checkEmployeeLogin(it1, email.text.toString(), pass.text.toString()) }
                if(LoginInterface.getStatus())
                {
                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace((view!!.parent as ViewGroup).id, LogedInFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        register.setOnClickListener {
            animateInOut(register)
        }

        return view

    }

    private fun animateInOut(button: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        button.startAnimation(zoomIn)
        button.startAnimation(zoomOut)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}