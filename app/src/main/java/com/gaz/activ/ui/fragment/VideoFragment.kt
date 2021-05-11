package com.gaz.activ.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import com.gaz.activ.R
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.mvvm.model.VideoViewModel
import com.gaz.activ.ui.BaseFragment
import com.gaz.activ.ui.dialog.RegistrationDialog
import com.gaz.activ.utils.observerEvent
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseFragment<VideoViewModel>(R.layout.fragment_video) {

    override fun getModelClass() = VideoViewModel::class

    private val registrationObserver =
        observerEvent<User>(::onError) { it?.let(::onRegisterComplete) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareVideo()
        enterButton.setOnClickListener { onEnterClick() }
    }

    override fun initObservers() {
        super.initObservers()
        model.registrationLiveData.observe(this, registrationObserver)
    }

    override fun onError(throwable: Throwable?) {
        RegistrationDialog.enableRegistration(parentFragmentManager)
        super.onError(throwable)
    }

    private fun prepareVideo() {
        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        val mediaItem: MediaItem = MediaItem.fromUri("http://evolut.site/gaz/gazprom.mp4.m3u8")

        videoView.player = player
        videoView.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)

        player.setMediaItem(mediaItem)
        player.prepare()
    }

    private fun onEnterClick() {
        RegistrationDialog().apply {
                setTargetFragment(this@VideoFragment, 0)
        }.show(parentFragmentManager)
    }

    private fun onRegisterComplete(user: User) {
        RegistrationDialog.dismissAllowingStateLoss(parentFragmentManager)
        model.openFinalScreen(user)
    }

    companion object {

        fun newInstance() = VideoFragment()
    }
}