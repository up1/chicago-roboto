package com.gdgchicagowest.windycitydevcon.features.sessions

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import com.gdgchicagowest.windycitydevcon.data.FirebaseSessionProvider
import com.gdgchicagowest.windycitydevcon.data.FirebaseSpeakerProvider
import com.gdgchicagowest.windycitydevcon.model.Session
import com.gdgchicagowest.windycitydevcon.model.Speaker

class SessionListView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        RecyclerView(context, attrs, defStyle), SessionListMvp.View {

    private val adapter: SessionAdapter
    private val presenter: SessionListMvp.Presenter

    init {
        presenter = SessionListPresenter(FirebaseSessionProvider(), FirebaseSpeakerProvider())

        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        addItemDecoration(SessionItemDecoration(context))
        adapter = SessionAdapter()
        super.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttach(this)
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

    fun setDate(date: String) {
        presenter.setDate(date)
    }

    override fun showNoSessions() {
        // todo
    }

    override fun showSessions(sessions: List<Session>) {
        adapter.sessions.clear()
        adapter.sessions.addAll(sessions)
        adapter.notifyDataSetChanged()
    }

    override fun showSpeakers(speakers: Map<String, Speaker>) {
        adapter.speakers.clear()
        adapter.speakers.putAll(speakers)
        adapter.notifyDataSetChanged()
    }
}

