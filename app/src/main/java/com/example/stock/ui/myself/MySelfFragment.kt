package com.example.stock.ui.myself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stock.R
import com.example.stock.ui.MainActivity
import com.example.stock.ui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_myself.view.*

class MySelfFragment : Fragment() {

    private lateinit var mySelfFragment: MySelfViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mySelfFragment =
                ViewModelProvider(this, ViewModelFactory.creatFactory(activity as MainActivity))
                    .get(MySelfViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myself, container, false)
        val _adapter=MyselfAdapter()
        root.recycle_myself.run {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@MySelfFragment.context)
            adapter=_adapter
        }
        //val textView: TextView = root.findViewById(R.id.text_home)
        mySelfFragment.getMyself().observe(viewLifecycleOwner, Observer {
            _adapter.submitList(it)
        })
        return root
    }
}