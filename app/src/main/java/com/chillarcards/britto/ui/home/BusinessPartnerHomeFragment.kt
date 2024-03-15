package com.chillarcards.britto.ui.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHomeBinding
import com.chillarcards.britto.databinding.FragmentHomeBpBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.MainActivity
import com.chillarcards.britto.ui.adapter.DoctorAdapter
import com.chillarcards.britto.ui.adapter.HospitalAdapter
import com.chillarcards.britto.ui.adapter.OrderAdapter
import com.chillarcards.britto.ui.adapter.PharmacyAdapter
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager

open class BusinessPartnerHomeFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentHomeBpBinding
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                alertMsg(requireContext())
            }
        })
    }
    fun alertMsg(context: Context) {
        try {
            PrefManager(context)
            val builder = AlertDialog.Builder(context)
            // Create the AlertDialog

            //set title for alert dialog
            builder.setTitle(R.string.alert_heading)
            //set message for alert dialog
            builder.setMessage(R.string.pop_alert_message)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setCancelable(false)

            //performing positive action
            builder.setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                findNavController().popBackStack()
            }
            builder.setNegativeButton(context.getString(R.string.cancel)) { _, _ ->
//                alertDialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        } catch (e: Exception) {
            //e.printstackTrace()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_bp, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        if(prefManager.getPage() == "1"){
            binding.bpInnerPharm.visibility=View.VISIBLE
            binding.bpInnerDoc.visibility=View.GONE

        }else if(prefManager.getPage() == "2"){//DOCTOR
            binding.bpInnerPharm.visibility=View.GONE
            binding.bpInnerDoc.visibility=View.VISIBLE
            binding.searchEt.visibility=View.GONE
        }


        binding.menu.setOnClickListener{
            menuOptions(it)
        }
        val views = listOf(
            binding.idJob,
            binding.jobCard
        )

        views.forEach { view ->
            view.setOnClickListener {
                findNavController().navigate(
                    BusinessPartnerHomeFragmentDirections.actionBphomeFragmentToJobView(
                    )
                )
            }
        }
        val viewsProfile = listOf(
            binding.idProfile,
            binding.profileCard
        )

        viewsProfile.forEach { view ->
            view.setOnClickListener {
                findNavController().navigate(
                    BusinessPartnerHomeFragmentDirections.actionBphomeFragmentToBpProfileFragment(
                    )
                )
            }
        }


        binding.portalCard.setOnClickListener{
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionBphomeFragmentToAddJob(
                )
            )
        }
        binding.idStock.setOnClickListener{
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionBphomeFragmentToItemFragment(
                )
            )
        }

        binding.idOrder.setOnClickListener{
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionPartnerFragmentToOrderFragment(
                )
            )
        }


        binding.viewMore.setOnClickListener{
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionPartnerFragmentToOrderFragment(
                )
            )
        }

        binding.searchEt.setOnClickListener{
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionBphomeFragmentToSearchFragment(
                )
            )
        }

        val dummyItem = listOf(
            DummyImage(1,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(2,"https://assets.truemeds.in/Images/tr:orig-true/mobikwik-500cashback.jpg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(3,"https://assets.truemeds.in/Images/dwebbanner3.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
        )
        val dummyPhar = listOf(
            DummyMenu(1,"https://lh3.googleusercontent.com/p/AF1QipMRixWWkhlF-8Xgw5nE98k2h1Mds3jla2c87oWV=s1360-w1360-h1020","Mazoon Pharmacy","Oman"),
            DummyMenu(2,"https://www.kimshealth.om/media/filer_public/96/52/96527546-c594-4410-8edf-0719c28db223/1920x500_3.jpg","Mazoon Pharmacy","Oman"),
            DummyMenu(3,"https://www.searcharabia.com/uploads/39a9defee9.png","Mazoon Pharmacy","Oman"),
            DummyMenu(4,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Mazoon Pharmacy","Oman"),
            DummyMenu(5,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
            DummyMenu(6,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
          )


        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

        val orderPicAdapter = OrderAdapter(
            dummyPhar, context,activity,"home",this@BusinessPartnerHomeFragment)
        binding.topPicRv.adapter = orderPicAdapter

    }

    private fun menuOptions(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_top, popup.menu)
        popup.show()
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        val versionName = pInfo?.versionName //Version Name
        val ver = getString(R.string.ver) + Const.ver_title + versionName
        popup.menu.getItem(popup.menu.size() - 1).title = ver

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_logout -> showLogoutAlert()
            }
            true
        }

    }
    private fun showLogoutAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.logout)
        builder.setMessage(R.string.logout_alert_message)
        builder.setIcon(android.R.drawable.ic_lock_power_off)

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val prefManager = PrefManager(requireContext())
            prefManager.clearAll()
            val intent = Intent(requireContext(), MainActivity::class.java)
            ActivityCompat.finishAffinity(requireActivity())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_red
                )
            )
        }
        alertDialog.show()
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("PHAR")) {
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                BusinessPartnerHomeFragmentDirections.actionOrderFragmentToOrderviewFragment(pharmacyId,pharmacyName)
            )
        }

    }

}
