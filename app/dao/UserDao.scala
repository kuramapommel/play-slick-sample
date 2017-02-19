package dao

import javax.inject.Inject

import models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
	* Created by admin on 2017/02/19.
	*/
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider){
	val dbConfig = dbConfigProvider.get[JdbcProfile]
	val db = dbConfig.db

	import dbConfig.driver.api._

	private val Users = User

	def findById(id: String): Future[Option[UserRow]] =
		db.run(_findById(id))

	private def _findById(id: String): DBIO[Option[UserRow]] =
		Users.filter(_.userId === id).result.headOption
}
