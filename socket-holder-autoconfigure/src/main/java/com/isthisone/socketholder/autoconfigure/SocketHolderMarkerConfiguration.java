package com.isthisone.socketholder.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SocketHolderMarkerConfiguration {

	@Bean
	public Marker socketHolderMarkerBean() {
		return new Marker();
	}

	static class Marker {

	}

}
