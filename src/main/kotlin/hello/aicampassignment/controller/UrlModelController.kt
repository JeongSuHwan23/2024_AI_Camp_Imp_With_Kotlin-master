package hello.aicampassignment.controller

import hello.aicampassignment.dto.UrlModelRequest
import hello.aicampassignment.model.Url
import hello.aicampassignment.service.UrlModelService
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class UrlModelController(
    val urlModelService: UrlModelService,
    private val UrlModelService: UrlModelService,
) {
    private val logger = LoggerFactory.getLogger(UrlModelController::class.java)
    @PostMapping("/api/short-urls")
    fun createShortUrl(
        @RequestBody
        request: UrlModelRequest,
    ) = urlModelService.createShortUrl(request.original_url)

    @GetMapping("/api/short-urls")
    fun listMyUrls(): List<Url>
            = UrlModelService.findAll();

    @GetMapping("/api/short-urls/{encodedUrl}")
    fun redirect(
        @PathVariable encodedUrl: String,
        response: HttpServletResponse,
    ) {
        logger.error("Request : $encodedUrl")
        val url = UrlModelService.findByEncodedUrl("http://localhost:8080/short-url/" + encodedUrl)
            ?: throw IllegalStateException("Invelid URL")

        response.sendRedirect(url.originalUrl)
    }
}