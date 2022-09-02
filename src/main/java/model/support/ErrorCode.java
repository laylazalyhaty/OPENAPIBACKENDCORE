package model.support;

public enum ErrorCode {
	// Not Found

	ERROR_NOT_FOUND_MOVIE("E010"),

	; // So that we can add new enums with comma

	public final String code;

	ErrorCode(String code) {
		this.code = code;
	}
}
