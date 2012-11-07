package com.googlecode.mgwt.linker.client.cache.html5;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.mgwt.linker.client.cache.ApplicationCache;
import com.googlecode.mgwt.linker.client.cache.ApplicationCacheStatus;
import com.googlecode.mgwt.linker.client.cache.event.CachedEvent;
import com.googlecode.mgwt.linker.client.cache.event.CheckingEvent;
import com.googlecode.mgwt.linker.client.cache.event.CheckingEvent.Handler;
import com.googlecode.mgwt.linker.client.cache.event.DownloadingEvent;
import com.googlecode.mgwt.linker.client.cache.event.ErrorEvent;
import com.googlecode.mgwt.linker.client.cache.event.NoUpadateEvent;
import com.googlecode.mgwt.linker.client.cache.event.ObsoluteEvent;
import com.googlecode.mgwt.linker.client.cache.event.ProgressEvent;
import com.googlecode.mgwt.linker.client.cache.event.UpdateReadyEvent;

public class Html5ApplicationCache implements ApplicationCache {

	protected static final ApplicationCacheStatus[] STATUS_MAPPING = new ApplicationCacheStatus[] { ApplicationCacheStatus.UNCACHED, ApplicationCacheStatus.IDLE, ApplicationCacheStatus.CHECKING,
			ApplicationCacheStatus.DOWNLOADING, ApplicationCacheStatus.UPDATEREADY, ApplicationCacheStatus.OBSOLTE };

	public static Html5ApplicationCache createIfSupported() {
		if (!isSupported()) {
			return null;
		}
		return new Html5ApplicationCache();
	}

	protected static native boolean isSupported()/*-{
		return typeof ($wnd.applicationCache) == "object";
	}-*/;

	protected EventBus eventBus = new SimpleEventBus();

	protected Html5ApplicationCache() {
		initialize();
	}

	@Override
	public ApplicationCacheStatus getStatus() {
		int status0 = getStatus0();
		return STATUS_MAPPING[status0];
	}

	@Override
	public HandlerRegistration addCheckingHandler(Handler handler) {
		return eventBus.addHandler(CheckingEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addCachedHandler(com.googlecode.mgwt.linker.client.cache.event.CachedEvent.Handler handler) {
		return eventBus.addHandler(CachedEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addDownloadingHandler(com.googlecode.mgwt.linker.client.cache.event.DownloadingEvent.Handler handler) {
		return eventBus.addHandler(DownloadingEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addErrorHandler(com.googlecode.mgwt.linker.client.cache.event.ErrorEvent.Handler handler) {
		return eventBus.addHandler(ErrorEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addNoUpdateHandler(com.googlecode.mgwt.linker.client.cache.event.NoUpadateEvent.Handler handler) {
		return eventBus.addHandler(NoUpadateEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addObsoluteHandler(com.googlecode.mgwt.linker.client.cache.event.ObsoluteEvent.Handler handler) {
		return eventBus.addHandler(ObsoluteEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addProgressHandler(com.googlecode.mgwt.linker.client.cache.event.ProgressEvent.Handler handler) {
		return eventBus.addHandler(ProgressEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addUpdateReadyHandler(com.googlecode.mgwt.linker.client.cache.event.UpdateReadyEvent.Handler handler) {
		return eventBus.addHandler(UpdateReadyEvent.getType(), handler);
	}

	protected native int getStatus0()/*-{
		return $wnd.applicationCache.status;
	}-*/;

	protected void onChecking() {
		eventBus.fireEventFromSource(new CheckingEvent(), this);
	}

	protected void onError() {
		eventBus.fireEventFromSource(new ErrorEvent(), this);
	}

	protected void onNoUpdate() {
		eventBus.fireEventFromSource(new NoUpadateEvent(), this);
	}

	protected void onDownloading() {
		eventBus.fireEventFromSource(new DownloadingEvent(), this);
	}

	protected void onProgress() {
		eventBus.fireEventFromSource(new ProgressEvent(), this);
	}

	protected void onUpdateReady() {
		eventBus.fireEventFromSource(new UpdateReadyEvent(), this);
	}

	protected void onCached() {
		eventBus.fireEventFromSource(new CachedEvent(), this);
	}

	protected void onObsolete() {
		eventBus.fireEventFromSource(new ObsoluteEvent(), this);
	}

	protected native void initialize() /*-{
		var that = this;

		$wnd.applicationCache
				.addEventListener(
						"checking",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onChecking()();
						}));

		$wnd.applicationCache
				.addEventListener(
						"onerror",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onError()();

						}));

		$wnd.applicationCache
				.addEventListener(
						"onnoupdate",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onNoUpdate()();

						}));

		$wnd.applicationCache
				.addEventListener(
						"ondownloading",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onDownloading()();
						}));

		$wnd.applicationCache
				.addEventListener(
						"onprogress",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onProgress()();
						}));

		$wnd.applicationCache
				.addEventListener(
						"onupdateready",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onUpdateReady()();
						}));

		$wnd.applicationCache
				.addEventListener(
						"oncached",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onCached()();
						}));

		$wnd.applicationCache
				.addEventListener(
						"onobsolete",
						$entry(function() {
							that.@com.googlecode.mgwt.linker.client.cache.html5.Html5ApplicationCache::onObsolete()();
						}));

	}-*/;

	@Override
	public native void swapCache() /*-{
		$wnd.applicationCache.swapCache();

	}-*/;

}
