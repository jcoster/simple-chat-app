package controllers

import dao.{MessageDao, UserDao}
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(userDao: UserDao, messageDao: MessageDao, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc)
{
  def index() = Action.async {
    userDao.listUsers().map { users =>
      Ok(views.html.index(users))
    }
  }
}