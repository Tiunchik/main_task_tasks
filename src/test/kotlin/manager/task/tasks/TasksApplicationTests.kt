package manager.task.tasks

import manager.task.tasks.domains.Task
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Testcontainers
class TasksApplicationTests {

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

    private val template = TestRestTemplate()

    @Test
    fun contextLoads() {
        Assertions.assertTrue(container.isRunning)
    }

    @Test
    fun sendPostRequest() {
        template
            .postForEntity("http://localhost:8080/api/v1/tasks", Task(), Task::class.java)
            .apply {
                assert(statusCode.is2xxSuccessful)
            }
    }

}
