package pl.inpost.recruitmenttask.data.network

import org.junit.Test
import org.junit.Assert.*
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

class ApiTypeAdapterTest {

    private val adapter = ApiTypeAdapter()

    @Test
    fun `test toZonedDateTime with valid date`() {
        val input = "2020-04-01T12:00:00+02:00"
        val expected = ZonedDateTime.parse(input)
        val result = adapter.toZonedDateTime(input)
        assertEquals(expected, result)
    }

    @Test(expected = DateTimeParseException::class)
    fun `test toZonedDateTime with invalid date`() {
        val input = "invalid-date-time"
        adapter.toZonedDateTime(input) // Expected to throw DateTimeParseException
    }

    @Test
    fun `test fromZonedDateTime with non-null date`() {
        val date = ZonedDateTime.parse("2020-04-01T12:00:00+02:00")
        val expected = "2020-04-01T12:00:00+02:00"
        val result = adapter.fromZonedDateTime(date)
        assertEquals(expected, result)
    }

    @Test
    fun `test fromZonedDateTime with null date`() {
        val result = adapter.fromZonedDateTime(null)
        assertNull(result)
    }
}
