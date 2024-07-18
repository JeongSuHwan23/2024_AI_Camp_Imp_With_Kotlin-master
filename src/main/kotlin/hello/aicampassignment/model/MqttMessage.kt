package hello.aicampassignment.model

import ch.qos.logback.core.net.server.Client
import com.fasterxml.jackson.databind.introspect.AnnotatedClass.Creators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "Mqtt_Message")
data class MqttMessage(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val di: Long = 0,

        @Column
        val clientID: String,

        @Column
        val message: String,

        @Column
        val createdAt: LocalDateTime = LocalDateTime.now()

)