package at.htl.webscrapperandroid.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import at.htl.webscrapperandroid.R
import at.htl.webscrapperandroid.web.VideoScrapper
import kotlinx.android.synthetic.main.main_fragment.*
import kotlin.concurrent.thread

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        thread {
            while (true) {
                val link = VideoScrapper.scrapWeb()
                activity?.runOnUiThread {
                    videoPlayer.setVideoPath(link)
                    val mc = MediaController(context)
                    mc.setAnchorView(videoPlayer)
                    videoPlayer.setMediaController(mc)
                    videoPlayer.start()
                }

                Thread.sleep(60000)
            }
        }


    }

}
