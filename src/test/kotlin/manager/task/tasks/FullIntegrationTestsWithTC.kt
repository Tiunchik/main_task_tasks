package manager.task.tasks

import manager.task.tasks.domains.Task
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
class FullIntegrationTestsWithTC {

    /**
     * Example of test container creation
     */
    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13.6-alpine"))
            .apply {
                withDatabaseName("tasks")
                withUsername("psql")
                withPassword("psql")
            }

        @JvmStatic
        @DynamicPropertySource
        fun propertiesConfig(registry: DynamicPropertyRegistry) =
            registry.apply {

                add("spring.liquibase.url", container::getJdbcUrl)
                add("spring.liquibase.user", container::getUsername)
                add("spring.liquibase.password", container::getPassword)

                add("spring.r2dbc.url") {
                    "r2dbc:postgresql://${container.host}:${container.firstMappedPort}/${container.databaseName}"
                }
                add("spring.r2dbc.username", container::getUsername)
                add("spring.r2dbc.password", container::getPassword)
            }
    }

    @Autowired
    private lateinit var template: TestRestTemplate
    private val baseUrl = "/api/v1/tasks"

    @Test
    fun fullTaskControllerTest() {
        //Post request
        val pRespBody = template.postForEntity(baseUrl, Task(), Task::class.java)
            .apply {
                Assertions.assertTrue(statusCode.is2xxSuccessful)
            }
            .body ?: throw Exception("Test answer on task creation is null")

        //Get one entity request
        val gRespBody = template.getForEntity("$baseUrl/${pRespBody.id}", Task::class.java)
            .apply {
                Assertions.assertTrue(body?.equals(pRespBody) ?: false)
            }
            .body ?: throw Exception("Test answer on get created task is null")

        //Get all entity request
        val getAllRespBody = template.getForEntity(baseUrl, List::class.java)
            .apply {
                Assertions.assertTrue(body?.size == 1)
            }
            .body ?: throw Exception("Test answer on get all created task is null")

        //Update entity request
        val uRespBody = template.postForEntity(baseUrl, Task(pRespBody.id, "new"), Task::class.java)
            .body ?: throw Exception("Test answer on update task is null")

        template.getForEntity("$baseUrl/${pRespBody.id}", Task::class.java)
            .apply {
                Assertions.assertTrue(body?.equals(uRespBody) ?: false)
            }

        //Delete entity request
        template.delete("$baseUrl/${pRespBody.id}")
        template.getForEntity(baseUrl, List::class.java)
            .apply {
                Assertions.assertTrue(body?.size == 0)
            }
    }
}
