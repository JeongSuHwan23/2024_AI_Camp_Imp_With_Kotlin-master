package hello.aicampassignment.service

import hello.aicampassignment.model.Url
import hello.aicampassignment.service.UrlModelRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UrlModelService(val urlModelRepository: UrlModelRepository) {
  fun save(url: Url): Url = urlModelRepository.save(url)

  fun findAll(): List<Url> = urlModelRepository.findAll()

  @Transactional
  fun findByEncodedUrl(encodedUrl: String): Url? {
    val result: Url? = urlModelRepository.findByEncodedUrl(ORIGIN_URL + encodedUrl)
    result?.let {
      it.clickCount = it.clickCount + 1
      urlModelRepository.save(it)
    }
    return result
  }

  fun createShortUrl(originalUrl: String): Url {
    val existingUrl = urlModelRepository.findByOriginalUrl(originalUrl)
    if (existingUrl != null) {
      return existingUrl
    }

    val encodedUrl = ORIGIN_URL + originalUrl.hashCode().toString(36)

    val url = Url(
            originalUrl = originalUrl,
            encodedUrl = encodedUrl,
            createdAt  = LocalDateTime.now()
    )

    return urlModelRepository.save(url)
  }
  companion object {
    val ORIGIN_URL: String = "http://localhost:8090/short-url/"
  }
  fun findUrlsCreatedAfter(createdAfter: LocalDateTime): List<Url> {
    return urlModelRepository.findAllCreatedAfter(createdAfter)
  }
}
