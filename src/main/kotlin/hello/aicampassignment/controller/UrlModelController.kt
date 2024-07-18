package hello.aicampassignment.controller

import hello.aicampassignment.dto.UrlModelRequest
import hello.aicampassignment.model.Url
import hello.aicampassignment.service.UrlModelService
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.annotation.After
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class UrlModelController(
        private val urlModelService: UrlModelService,
) {
  private val logger = LoggerFactory.getLogger(UrlModelController::class.java)

  @PostMapping("/api/short-urls")
  fun createShortUrl(
          @RequestBody request: UrlModelRequest,
  ): Url = urlModelService.createShortUrl(request.original_url)

//  @GetMapping("/api/short-urls")
//  fun listMyUrls(): List<Url> = urlModelService.findAll()

  @GetMapping("/api/short-urls")
  fun listMyUrls(
          @RequestParam(required = false) createdAfter: LocalDateTime?
  ): List<Url> {
    // createdAfter 파라미터가 없을 경우 기본값으로 최근 10분 이내의 데이터만 반환
    val defaultTime = LocalDateTime.now().minusMinutes(10)
    val filterTime = createdAfter ?: defaultTime
    return urlModelService.findUrlsCreatedAfter(filterTime)
  }

  @GetMapping("/api/short-url/{encodedUrl}")
  fun redirect(
          @PathVariable encodedUrl: String,
          response: HttpServletResponse
  ) {
    logger.info("Request : $encodedUrl")
    val url = urlModelService.findByEncodedUrl(encodedUrl)
            ?: throw IllegalStateException("Invalid URL")

    response.sendRedirect(url.originalUrl)
  }
}
