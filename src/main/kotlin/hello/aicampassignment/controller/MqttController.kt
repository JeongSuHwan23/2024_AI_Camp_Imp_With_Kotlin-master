package hello.aicampassignment.controller

import hello.aicampassignment.config.MqttConfig.Companion.SUB_CLIENT_ID
import hello.aicampassignment.dto.MqttRequest
import hello.aicampassignment.dto.UrlModelRequest
import hello.aicampassignment.model.Url
import hello.aicampassignment.service.MqttService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MqttController(
        private val mqttGateway: MqttService.MqttGateway
) {
  @GetMapping("/mqtt/ping")
  fun ping() {
    mqttGateway.sendToMqtt("Hello World!")
  }

  @PostMapping("/mqtt/publish")
  fun publishToMqtt(
          @RequestBody payload: MqttRequest
  ) {
    val message = "[${SUB_CLIENT_ID}] : ${payload.publishMessage}"
    mqttGateway.sendToMqtt(message)
  }


}