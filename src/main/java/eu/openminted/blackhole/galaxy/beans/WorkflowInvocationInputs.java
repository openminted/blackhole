package eu.openminted.blackhole.galaxy.beans;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class WorkflowInvocationInputs extends WorkflowInputs {

	@JsonProperty("inputs")
	public Map<String, WorkflowInput> getInputs() {
		return super.getInputs();
	}
}
