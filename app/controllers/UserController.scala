package controllers

import dao.UserDao
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(userDao: UserDao, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc)
{
  def userHome(id: Int) = Action.async {
    for {
      userOpt <- userDao.getUser(id)
      userContacts <- userDao.getUserContacts(id)
    } yield (userOpt, userContacts) match {
      case (Some(user), contacts) => Ok(views.html.user(user, contacts))
      case _ => BadRequest(s"No user found with id: $id")
    }
  }
}