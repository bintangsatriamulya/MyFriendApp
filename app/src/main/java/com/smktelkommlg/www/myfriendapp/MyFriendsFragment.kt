package com.smktelkommlg.www.myfriendapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.smktelkommlg.www.myfriendapp.databinding.FriendsFragmentBinding

class MyFriendsFragment : Fragment() {

    private lateinit var myFriendList : MutableList<MyFriend>
    private lateinit var binding: FriendsFragmentBinding
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FriendsFragmentBinding.inflate(inflater, container, false)
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
        binding.fabAddFriend.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
        ambilDataTeman()
    }
    private fun ambilDataTeman() {
        myFriendList = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(viewLifecycleOwner, Observer { r ->
            myFriendList = r as MutableList<MyFriend>
            when {
                myFriendList.isEmpty() -> tampilToast("Belum ada data teman")
                else -> showMyFriends()
            }
        })
    }
    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showMyFriends(){
        binding.listMyFriends.layoutManager = LinearLayoutManager(activity)
        binding.listMyFriends.adapter = MyFriendAdapter(requireActivity(),
            myFriendList as ArrayList<MyFriend>
        )
    }
}