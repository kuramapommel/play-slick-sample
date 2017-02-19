package controllers

import javax.inject.Inject

import dao.UserDao
import models.Tables.UserRow
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

/**
	* Created by admin on 2017/02/19.
	*/
class AuthController @Inject()(userDao: UserDao) extends Controller{

	def auth(id: String) = Action.async{ implicit request =>
		for(Some(user) <- userDao.findById(id)) yield
			user match {
				case _: UserRow => Ok(views.html.user(user))
				case _ => Ok(views.html.user(new UserRow("", "", None, None)))
		}
	}

}
