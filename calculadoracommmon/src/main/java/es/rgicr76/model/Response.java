package es.rgicr76.model;


public class Response {

	private String processId;

	public Response(String processId) {
		super();
		this.processId = processId;
	}

	public Response() {
		super();
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
