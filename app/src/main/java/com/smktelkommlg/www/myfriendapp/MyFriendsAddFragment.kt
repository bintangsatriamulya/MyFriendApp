package com.smktelkommlg.www.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.smktelkommlg.www.myfriendapp.databinding.AddFriendsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment() {

    private lateinit var binding: AddFriendsBinding
    private lateinit var namaInput: String
    private lateinit var emailInput: String
    private lateinit var telpInput: String
    private lateinit var alamatInput: String
    private lateinit var genderInput: String
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsAddFragment {
            return MyFriendsAddFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        binding.btnSave.setOnClickListener {
            validasiInput()
        }
        setDataSpinnerGender()
    }

    private fun setDataSpinnerGender() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter
    }

    private fun validasiInput() {
        namaInput = binding.edtName.text.toString()
        emailInput = binding.edtEmail.text.toString()
        telpInput = binding.edtTelp.text.toString()
        alamatInput = binding.edtAddress.text.toString()
        genderInput = binding.spinnerGender.selectedItem.toString()

        when {
            namaInput.isEmpty() -> binding.edtName.error = "Nama tidak boleh kosong"
            genderInput == "Pilih Jenis kelamin" -> tampilToast("Jenis Kelamin harus dipilih")
            emailInput.isEmpty() -> binding.edtEmail.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> binding.edtTelp.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> binding.edtAddress.error = "Alamat tidak boleh kosong"
            else -> {
                val teman = MyFriend(null, namaInput, genderInput, emailInput, telpInput, alamatInput)
                tambahDataTeman(teman)
            }
        }
    }

    private fun tambahDataTeman(teman: MyFriend): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            myFriendDao?.tambahTeman(teman)
            requireActivity().runOnUiThread {
                (activity as MainActivity).tampilMyFriendsFragment()
            }
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}
