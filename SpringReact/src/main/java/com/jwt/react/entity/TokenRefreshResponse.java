package com.jwt.react.entity;

public class TokenRefreshResponse {

	private String refreshToken;

	public TokenRefreshResponse(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
