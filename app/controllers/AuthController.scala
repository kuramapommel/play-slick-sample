package controllers

import javax.inject.Inject

import dao.UserDao
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

/**
	* Created by admin on 2017/02/19.
	*/
class AuthController @Inject()(userDao: UserDao) extends Controller{
	def auth(id: String) = Action.async{ implicit rs =>
		for(Some(user) <- userDao.findById(id)) yield Ok(views.html.user(user))
	}
}
