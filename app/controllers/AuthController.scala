package controllers

import javax.inject.Inject

import dao.UserDao
import form.AuthForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}


/**
	* Created by admin on 2017/02/19.
	*/
class AuthController @Inject()(userDao: UserDao) extends Controller{

	val form = Form(
		mapping(
			AuthForm.userId -> text(minLength = 8),
			AuthForm.password -> text(minLength = 8)
		)(AuthForm.apply)(AuthForm.unapply)
	)

	def index = Action{
		Ok(views.html.login(""))
	}

	def auth = Action.async{ implicit request =>
		val requestForm = form.bindFromRequest
		if(requestForm.hasErrors) {
			 Ok(views.html.login("ログイン認証エラー")) // TODO 何故かここでかえらない
		}

		val userForm = requestForm.get

		for(userRow <- userDao.findById(userForm.userId)) yield
		userRow match {
			case Some(user) =>
				if(user.password == userForm.password) {
					Ok(views.html.user(user))
				} else {
					Ok(views.html.login("ログイン認証エラー"))
				}
			case None => Ok(views.html.login("ログイン認証エラー"))
		}
	}
}
