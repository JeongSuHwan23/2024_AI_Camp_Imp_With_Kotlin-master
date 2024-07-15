package hello.aicampassignment.service

import hello.aicampassignment.model.Url
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class UrlServiceTest(
    @Autowired private val urlModelRepository: UrlModelRepository,
    @Autowired private val sut: UrlModelService

) {
    @BeforeTest
    fun setUp() {
        urlModelRepository.deleteAll()
    }

    @Test
    fun testSave() {
        val url = Url(
            originalUrl = "https://www.google.com",
            encodedUrl = "localhost:8080/short-url/123"
        )

        val result = sut.save(url)

        assertEquals(url.originalUrl, result.originalUrl)
        assertEquals(url.encodedUrl, result.encodedUrl)
        assertNotNull(result.id)
    }

    @Test
    fun testFindAll() {
        val saveUrls = urlModelRepository.saveAll(
            (1..10).map {
                Url(
                    originalUrl = "https://www.google.com/$it",
                    encodedUrl = "localhost:8080/short-url/$it"
                )
            }
        )

        val result = sut.findAll()

        assertEquals(result.size, 10)
        assertEquals(result[0].originalUrl, saveUrls[0].originalUrl)
        assertEquals(result[0].encodedUrl, saveUrls[0].encodedUrl)
        assertNotNull(result[0].id)
    }

    @Test
    fun findByEncodedUrlTest() {

        val encodedUrl = "localhost:8080/short-url/123"

        val saved = urlModelRepository.save(
            Url(
                originalUrl = "https://www.google.com",
                encodedUrl = encodedUrl
            )
        )

        val result = requireNotNull(sut.findByEncodedUrl(encodedUrl))

        assertEquals(result.originalUrl, saved.originalUrl)
        assertEquals(result.encodedUrl, saved.encodedUrl)
        assertNotNull(result.id)
    }

    @Test
    fun createShortUrlTest() {
        val originalUrl = "https://www.google.com"
        val result = sut.createShortUrl(originalUrl)

        assertEquals(originalUrl, result.originalUrl)
        assertEquals(result.encodedUrl, "http://localhost:8080/short-url/" + originalUrl.hashCode().toString(36))
        assertNotNull(result.id)
    }

    @Test
    fun checkClickCount() {
        val encodedUrl = "localhost:8080/short-url/123"

        val saved = urlModelRepository.save(
            Url(
                originalUrl = "https://www.google.com",
                encodedUrl = encodedUrl
            )
        )
    }
}