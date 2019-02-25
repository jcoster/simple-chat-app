package dao

import com.google.inject.Inject
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.SQLiteProfile.api._
import slick.jdbc.{GetResult, SQLActionBuilder, SQLiteProfile}

import scala.concurrent.{ExecutionContext, Future}

class UserDao @Inject()(
  val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[SQLiteProfile]
{
  implicit val getUserResult: GetResult[User] = GetResult { r =>
    User(id = r.nextInt(), username = r.nextString())
  }

  implicit class QueryOps(q: SQLActionBuilder) {
    def listUsers: Future[List[User]] = db.run(q.as[User]).map(_.toList)
  }

  def getUser(userId: Int): Future[Option[User]] = {
    val query = sql"""
      SELECT * from user
      WHERE id = #$userId"""
    query.listUsers.map(_.headOption)
  }

  def listUsers(): Future[List[User]] = {
    val query = sql"""
      SELECT * FROM user
      ORDER BY username"""
    query.listUsers
  }

  // TODO add contacts feature; returning all other users for now
  def getUserContacts(userId: Int): Future[List[User]] = {
    val query = sql"""
      SELECT * FROM user
      WHERE id <> #$userId
      ORDER BY username"""
    query.listUsers
  }
}
