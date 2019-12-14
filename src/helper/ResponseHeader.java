package helper;

public enum ResponseHeader {
	NOT_FOUND(404, "Not Found"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	OK(200,"Ok");
	
	public int code;
	public String name;
	
	private ResponseHeader(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
}
