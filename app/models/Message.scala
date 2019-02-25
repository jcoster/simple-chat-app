package models

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

case class Message(timestamp: DateTime, fromUserId: Int, toUserId: Int, content: String) {
  val timestampString: String = Message.dateTimeToString(timestamp)
}

object Message {
  val dateTimePattern = "yyyy-MM-dd HH:mm:ss"
  val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern(dateTimePattern)

  def dateTimeToString(dateTime: DateTime): String = dateTimeFormatter.print(dateTime)
  def dateTimeFromString(dateTimeStr: String): DateTime = dateTimeFormatter.parseDateTime(dateTimeStr)
}