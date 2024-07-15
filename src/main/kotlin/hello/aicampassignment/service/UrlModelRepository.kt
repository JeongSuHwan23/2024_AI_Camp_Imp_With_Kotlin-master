package hello.aicampassignment.service

import hello.aicampassignment.model.Url
import org.springframework.data.jpa.repository.JpaRepository

interface UrlModelRepository : JpaRepository<Url, Long> {
    fun findByEncodedUrl(findByEncodedUrl: String): Url?
}