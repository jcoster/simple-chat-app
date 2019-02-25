package models

import org.joda.time.DateTime
import org.scalatestplus.play.PlaySpec

class MessageSpec extends PlaySpec {

  "dateTimeToString" should {
    "convert a DateTime object to a string with the expected pattern" in {
      val dateTime = new DateTime()
        .withYear(2019).withMonthOfYear(2).withDayOfMonth(25)
        .withHourOfDay(11).withMinuteOfHour(12).withSecondOfMinute(13)
      val dtString = Message.dateTimeToString(dateTime)

      dtString mustEqual "2019-02-25 11:12:13"
    }
  }

  "dateTimeFromString" should {
    "convert a string in the expected pattern to a DateTime object" in {
      val dateTime = Message.dateTimeFromString("2019-02-25 11:12:13")
      val expectedDateTime = new DateTime()
        .withYear(2019).withMonthOfYear(2).withDayOfMonth(25)
        .withHourOfDay(11).withMinuteOfHour(12).withSecondOfMinute(13).withMillisOfSecond(0)

      dateTime mustEqual expectedDateTime
    }
  }
}