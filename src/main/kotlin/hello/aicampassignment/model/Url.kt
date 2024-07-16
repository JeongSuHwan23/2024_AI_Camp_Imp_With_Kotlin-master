package hello.aicampassignment.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "urls")
data class Url(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false, unique = true)
        val originalUrl: String,

        @Column(nullable = false, unique = true)
        var encodedUrl: String,
        @Column
        var clickCount: Int = 0,

        @Column(nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now()
)