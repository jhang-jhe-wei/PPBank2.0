package com.homi.ppbank20

import User
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private var user:User =User()
private val TAG=M_accountFragment::class.java.simpleName
/**
 * A simple [Fragment] subclass.
 * Use the [M_accoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class M_accountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var user:User?=User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        user=arguments?.getSerializable("user")as? User
        Log.d(TAG,user.toString())
        Log.d("oncreateview",user.toString())
        return inflater.inflate(R.layout.fragment_m_accout, container, false)
    }

    override fun onStart() {
        super.onStart()
        user=arguments?.getSerializable("user")as? User
        Log.d(TAG,user.toString())
        Log.d("onstart",user.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user=arguments?.getSerializable("user")as? User
        Log.d(TAG,user.toString())
        Log.d("onviewcreated",user.toString())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment M_accoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            M_accountFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("user",user)
                }
            }
    }
}
