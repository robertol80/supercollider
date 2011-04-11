// idea by adc, mods by /f0, jrh, mc, ...

ProxyMixerOld {
		// proxymixer need not be built into a supplied window, 
		// so just call the parent window.
	var <window, <zone, <proxyspace, <>nProxies;
	var <activeNodes;
	var <title;
	
	var <editZoneBtn, <pxMons, <editBtnsAr, <editBtnsKr, <buttonLinesKr;
	var <compArZone, <compKrZone;
	
	var origBounds, fullBounds, editZoneOpen=false, <selectMethod=\existingProxies;
	
	w { 
		warn("" ++ this.class ++ ":w is deprecated. use .window instead.") 
		^window 
	}
	
	*new { arg proxyspace, nProxies = 16, title, where;
		^super.new.init(proxyspace, nProxies, 
			title ?? { format("proxyspace: %", proxyspace.tryPerform(\name) ? "") }, where
		);
	}
	
	init { arg space, nPxs = 16, argTitle, where; 
		
		this.makeWindow;
				
		zone = CompositeView(window, fullBounds.moveTo(0,0));
		zone.decorator =  FlowLayout(zone.bounds); //.gap_(6@6);
		
		compArZone = CompositeView.new(zone, Rect(0, 0, 400, height))
			.background_(skin.foreground);
		compKrZone = CompositeView.new(zone, Rect(0, 0, 200, height))
			.background_(skin.foreground);		
					if (mod.notNil and: { mod.isAlt }) { 
						NodeProxyEditor(pxmon.proxy);					} { 
						editor.proxy_(pxmon.proxy);
						this.openEditZone(1);
						editBtnsAr.do { |b| b.value_(0) };
						editBtnsKr.do { |b| b.value_(0) };
						btn.value_(1);
					};
				});
			arLayout.nextLine;
			[pxmon, edbut]
		}).flop;

		scrollyAr = EZScroller.new(window,
			Rect(compArZone.bounds.right - 16, 30, 12, nProxies * skin.buttonHeight),
			nProxies, nProxies,

	highlightSlots { |parOffset, num| 
		var onCol = Color(1, 0.5, 0.5);
		var offCol = Color.clear;
		{ pxMons.do { |moni, i| 
			var col = if (i >= parOffset and: (i < (parOffset + num).max(0)), onCol, offCol); 
			moni.nameView.background_(col.green_([0.5, 0.7].wrapAt(i - parOffset div: 2)));
		} }.defer;
	}
	
	updateKrSlots { 
		var krProxyNames;
		krProxyNames = proxyspace.krProxyNames;

		if (krProxyNames.size > nProxies) {

			scrollyKr.visible_(true)
				.numItems_(krProxyNames.size)