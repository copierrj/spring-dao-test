package nl.idgis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

@Configuration
public class TestDatabaseConfig {
		
	@Bean
	DockerClient dockerClient() throws Exception {
		return DefaultDockerClient.fromEnv().build();
	}
	
	@Bean
	TestDatabase testDatabase(DockerClient dockerClient) throws Exception {
		dockerClient.pull("postgres:9.6");
		
		Map<String, List<PortBinding>> portBindings = new HashMap<>();
		portBindings.put("5432", Arrays.asList(PortBinding.randomPort("0.0.0.0")));
		HostConfig hostConfig = HostConfig.builder()
				.portBindings(portBindings)
				.build();
		
		ContainerConfig dbContainerConfig = ContainerConfig.builder()
				.hostConfig(hostConfig)
				.image("postgres:9.6")
				.exposedPorts("5432")
				.build();
		
		String containerId = dockerClient.createContainer(dbContainerConfig).id();
		dockerClient.startContainer(containerId);
		
		ContainerInfo dbContainerInfo = dockerClient.inspectContainer(containerId);
		String port = dbContainerInfo.networkSettings().ports().get("5432/tcp").get(0).hostPort();
		
		String host = dockerClient.getHost();
		
		for(int i = 5; i >= 0; i--) {
			Thread.sleep(1000);
			
			try(Connection c = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/postgres", "postgres", "postgres");
				Statement stmt = c.createStatement()) {
				stmt.execute("create database \"spring-demo\"");
				break;
			} catch(Exception e) { 
				if(i == 0) {
					throw new IllegalStateException("Failed to create test database", e);
				}
			}
		}
		
		return new TestDatabase(dockerClient, containerId, host, port);
	}
	
	@Bean
	DataSource dataSource(TestDatabase testDatabase) throws Exception {
		return new DriverManagerDataSource("jdbc:postgresql://" + testDatabase.getHost() + ":" + testDatabase.getPort() + "/spring-demo", "postgres", "postgres");
	}
}
