package dao

import com.google.inject.Inject
import models.Message
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.SQLiteProfile.api._
import slick.jdbc.{GetResult, SQLiteProfile}

import scala.concurrent.{ExecutionContext, Future}

class MessageDao @Inject()(
  val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[SQLiteProfile]
{
  implicit val getMessageResult: GetResult[Message] = GetResult { r =>
    Message(
      timestamp = Message.dateTimeFromString(r.nextString()),
      fromUserId = r.nextInt(),
      toUserId = r.nextInt(),
      content = r.nextString()
    )
  }

  def getAllMessagesBetweenUsers(user1Id: Int, user2Id: Int): Future[List[Message]] = {
    val query = sql"""
      SELECT * FROM message
      WHERE from_user = #$user1Id AND to_user = #$user2Id
        OR from_user = #$user2Id AND to_user = #$user1Id
      ORDER BY timestamp DESC"""
    db.run(query.as[Message]).map(_.toList)
  }

  def addMessage(message: Message): Future[Int] = {
    val statement =
      sqlu"""INSERT INTO message VALUES (
        '#${message.timestampString}',
        #${message.fromUserId},
        #${message.toUserId},
        '#${message.content}'
      )"""
    db.run(statement)
  }
}