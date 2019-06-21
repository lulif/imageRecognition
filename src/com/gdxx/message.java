package com.gdxx;

public class message {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public message(String name, String id) {
		super();
		this.id = id;
		this.name = name;
	}

}
