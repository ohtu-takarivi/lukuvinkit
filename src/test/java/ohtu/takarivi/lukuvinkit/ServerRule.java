package ohtu.takarivi.lukuvinkit;

import java.util.Collections;

import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


class ServerRule extends ExternalResource{

    private final int port;
    ConfigurableApplicationContext app;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
    	SpringApplication sapp = new SpringApplication(Application.class);
        sapp.setDefaultProperties(Collections.singletonMap("server.port", Integer.toString(port)));
        this.app = sapp.run();
    }

    @Override
    protected void after() {
        app.close();
    }
}