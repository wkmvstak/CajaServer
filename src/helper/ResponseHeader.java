package helper;

public enum ResponseHeader {
	NOT_FOUND(404, "Not Found"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");
	
	int code;
	String name;
	
	private ResponseHeader(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
}
