package nl.cargonaut.util.redelivery.route;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.wildfly.extension.camel.CamelAware;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@CamelAware
@ApplicationScoped
public class RedeliveryRouteBuilder extends RouteBuilder {

    private static final String ACTIVEMQ = "activemq:";

    @Inject
    @ConfigurationValue("activemq.connection.url")
    private String activeMqConnectionUrl;

    @Inject
    @ConfigurationValue("activemq.channel.inputChannel")
    private String inputChannel;

    @Inject
    @ConfigurationValue("activemq.channel.outputChannel")
    private String outputChannel;

    @Override
    public void configure() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL(activeMqConnectionUrl);
        getContext().addComponent("activemq", activeMQComponent);

        from(ACTIVEMQ + inputChannel + "?username=publisher&password=Qns4hdM3GcvK4ccc9M4jhU")
                .log(LoggingLevel.DEBUG, "Resending: ${body}")
                .to(ACTIVEMQ + outputChannel + "?username=publisher&password=Qns4hdM3GcvK4ccc9M4jhU");
    }
}
