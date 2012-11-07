package com.googlecode.mgwt.linker.server.propertyprovider;

import javax.servlet.http.HttpServletRequest;

public class MobileUserAgentProvider extends PropertyProviderBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7478122098836802106L;

	@Override
	public String getPropertyName() {
		return "mobile.user.agent";
	}

	@Override
	public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {
		String ua = getUserAgent(req);

		if (ua.contains("android")) {
			return "mobilesafari";
		}
		if (ua.contains("iphone")) {
			return "mobilesafari";
		}
		if (ua.contains("ipad")) {
			return "mobilesafari";
		}
		if (ua.contains("blackberry")) {
			return "not_mobile";
		}
		return "not_mobile";
	}
}
