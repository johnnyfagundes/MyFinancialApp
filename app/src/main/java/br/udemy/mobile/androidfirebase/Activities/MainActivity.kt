package br.udemy.mobile.androidfirebase.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.udemy.mobile.androidfirebase.R
import kotlinx.android.synthetic.main.activity_onboarding.*

class MainActivity : AppCompatActivity() {

//    private val fragment1 = SliderFragment()
//    private val fragment2 = SliderFragment()
//    private val fragment3 = SliderFragment()
//    lateinit var adpter: myPagerAdapter
//    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        buttonNext.setOnClickListener {
            canvasHell.next()
        }

        buttonPrevious.setOnClickListener {
            canvasHell.previous()
        }


    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        btnEntra.setOnClickListener() {
//            val intentLogin = Intent(this, LoginActivity::class.java)
//            startActivity(intentLogin)
//            finish()
//        }
//
//        activity = this
//
//        fragment1.setTitle("Gerencie seu plano financeiro sem complicações")
//        fragment2.setTitle("Aprenda a poupar e realize grandes conquistas")
//        fragment3.setTitle("Mantenha relações positivas no mercado financeiro")
//
//        adpter = myPagerAdapter(supportFragmentManager)
//
//        adpter.list.add(fragment1)
//        adpter.list.add(fragment2)
//        adpter.list.add(fragment3)
//
//        view_pager.adapter = adpter
//
//        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//
//            override fun onPageScrollStateChanged(p0: Int) {
//
//            }
//
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//
//
//            }
//
//            override fun onPageSelected(p0: Int) {
//
//
//                when (view_pager.currentItem) {
//                    0 -> {
//                        indicador1.setTextColor(Color.WHITE)
//                        indicador2.setTextColor(Color.GRAY)
//                        indicador3.setTextColor(Color.GRAY)
//                        btnEntra.visibility = View.VISIBLE
//
//                    }
//                    1 -> {
//                        indicador1.setTextColor(Color.GRAY)
//                        indicador2.setTextColor(Color.WHITE)
//                        indicador3.setTextColor(Color.GRAY)
//                        btnEntra.visibility = View.VISIBLE
//
//                    }
//                    2 -> {
//                        indicador1.setTextColor(Color.GRAY)
//                        indicador2.setTextColor(Color.GRAY)
//                        indicador3.setTextColor(Color.WHITE)
//                        btnEntra.visibility = View.VISIBLE
//
//                    }
//                }
//            }
//
//        })
//
//    }

//    class myPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
//
//        val list: MutableList<Fragment> = ArrayList()
//
//        override fun getItem(p0: Int): Fragment {
//            return list[p0]
//        }
//
//        override fun getCount(): Int {
//            return list.size
//        }
//    }

}
