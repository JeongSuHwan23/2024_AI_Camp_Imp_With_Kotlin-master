package hello.aicampassignment.service

import hello.aicampassignment.model.Url
import org.aspectj.lang.annotation.After
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface UrlModelRepository : JpaRepository<Url, Long> {
  fun findByEncodedUrl(findByEncodedUrl: String): Url?
  fun findByOriginalUrl(originalUrl: String): Url?

  @Query("SELECT u FROM Url u WHERE u.createdAt > :createdAfter")
  fun findAllCreatedAfter(@Param("createdAfter") createdAfter: LocalDateTime): List<Url>
}