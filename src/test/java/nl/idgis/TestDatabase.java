package nl.idgis;

import org.springframework.beans.factory.DisposableBean;

import com.spotify.docker.client.DockerClient;

public class TestDatabase implements DisposableBean {

	private final DockerClient dockerClient;
	
	private final String containerId;
	
	private final String host;
	
	private final String port;
	
	public TestDatabase(DockerClient dockerClient, String containerId, String host, String port) {
		this.dockerClient = dockerClient;
		this.containerId = containerId;
		this.host = host;
		this.port = port;
	}

	public DockerClient getDockerClient() {
		return dockerClient;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	@Override
	public void destroy() throws Exception {
		dockerClient.stopContainer(containerId, 5);
		dockerClient.removeContainer(containerId);
	}
}
