package controllers

import akka.stream.Materializer
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub}
import dao.{MessageDao, UserDao}
import javax.inject._
import models.Message
import org.joda.time.DateTime
import play.api.libs.json.{JodaReads, Json, Reads}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChatController @Inject()(userDao: UserDao, messageDao: MessageDao, cc: ControllerComponents)
  (implicit ec: ExecutionContext, mat: Materializer) extends AbstractController(cc)
{
  implicit lazy val dateTimeReads: Reads[DateTime] = JodaReads.jodaDateReads(Message.dateTimePattern)
  implicit lazy val messageReads: Reads[Message] = Json.reads[Message]

  def chatPage(fromUserId: Int, toUserId: Int) = Action.async {
    for {
      fromUserOpt <- userDao.getUser(fromUserId)
      toUserOpt <- userDao.getUser(toUserId)
      allMessages <- messageDao.getAllMessagesBetweenUsers(fromUserId, toUserId)
    } yield (fromUserOpt, toUserOpt, allMessages) match {
      case (Some(fromUser), Some(toUser), messages) =>
        Ok(views.html.chat(fromUser, toUser, messages))
      case _ =>
        BadRequest(s"One or more invalid user ids: [$fromUserId, $toUserId]")
    }
  }

  private val userFlow: Flow[String, String, _] = {
    val (chatSink, chatSource) = {
      val source = MergeHub.source[String]
      val sink = BroadcastHub.sink[String]
      source.toMat(sink)(Keep.both).run()
    }

    Flow[String].map { element =>
      // FIXME find less-hacky way to persist incoming websocket messages
      val message = Json.parse(element).as[Message]
      messageDao.addMessage(message)
      element
    }.via(Flow.fromSinkAndSource(chatSink, chatSource))
  }

  def webSocket: WebSocket = WebSocket.acceptOrResult[String, String] { requestHeader =>
    Future.successful(userFlow).map(Right.apply)
  }
}