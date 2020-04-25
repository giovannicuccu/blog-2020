package it.giovannicuccu.blog.testcontainers.model;

import java.time.LocalDateTime;

public class DiagnosticEvent {
	

	private Long id;
	private String type;
	private String name;
	private String result;
	private LocalDateTime start;
	private int elapsedMillis;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public int getElapsedMillis() {
		return elapsedMillis;
	}
	public void setElapsedMillis(int elapsedMillis) {
		this.elapsedMillis = elapsedMillis;
	}
	
	@Override
	public String toString() {
		return "DiagnosticEvent [id=" + id + ", type=" + type + ", name=" + name + ", result=" + result + ", start="
				+ start + ", elapsedMillis=" + elapsedMillis + "]";
	}
	
}
