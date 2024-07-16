
package hello.aicampassignment.config

import hello.aicampassignment.dto.MqttRequest
import hello.aicampassignment.service.MqttListener
import org.apache.logging.log4j.message.Message
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.hibernate.pretty.MessageHelper
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Configuration
class MqttConfig {
  private val brokerUrl = "tcp://localhost:1883"
  //private val brokerUrl = "tcp://10.150.150.90:1883"
  private val clientId = "userskin1"
  private val topic = "bssm"

  @Bean
  fun mqttPahoClientFactory() = DefaultMqttPahoClientFactory().apply {
    connectionOptions = MqttConnectOptions().apply {
      serverURIs = arrayOf(brokerUrl)
    }
  }

  @Bean
  fun mqttOutboundChannel(): MessageChannel = DirectChannel()

  @Bean
  @ServiceActivator(inputChannel = "mqttOutboundChannel")
  fun mqttOutbound(): MessageHandler {
    val handler = MqttPahoMessageHandler(clientId, mqttPahoClientFactory()).apply {
      setAsync(true)
      setDefaultTopic(topic)
    }
    return handler
  }

  @Bean
  fun mqttInboundChannel(): MessageChannel {
    return DirectChannel()
  }

  @Bean
  fun inbound(): MqttPahoMessageDrivenChannelAdapter {
    val adapter = MqttPahoMessageDrivenChannelAdapter(
            brokerUrl,
            PUB_CLIENT_ID,
            mqttPahoClientFactory(),
            topic,
    )

    adapter.setCompletionTimeout(5000)
    adapter.setConverter(DefaultPahoMessageConverter())
    adapter.setQos(1)
    adapter.outputChannel = mqttInboundChannel()
    return adapter
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttInboundChannel")
  suspend fun mqttInbound(): MessageHandler {
    return MqttListener()
  }

  companion object {
    const val SUB_CLIENT_ID = "userskin1"
    const val PUB_CLIENT_ID = "userskin2"
  }
}
