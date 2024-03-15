package com.chillarcards.britto.ui.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentJobDtlInnerBinding
import com.chillarcards.britto.databinding.FragmentPdfViewerBinding
import com.chillarcards.britto.ui.register.OTPFragmentArgs
import com.chillarcards.britto.utills.PrefManager

open class PdfViewerFragment : Fragment() {

    private var pdfUrl: String? = null
    lateinit var binding: FragmentPdfViewerBinding
    private lateinit var prefManager: PrefManager
    private val args: PdfViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf_viewer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        setToolbar()

        pdfUrl = args.id
        binding.pdfWebView.settings.javaScriptEnabled = true
//        binding.pdfWebView.loadUrl("$pdfUrl")
        binding.pdfWebView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdfUrl")
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.job)
    }


}
