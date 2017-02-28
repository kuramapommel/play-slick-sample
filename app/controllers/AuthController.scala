package controllers

import javax.inject.Inject

import dao.UserDao
import form.AuthForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future


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
		form.bindFromRequest.fold(
			_ => Future.successful(Ok(views.html.login("バインドエラー"))),
			requestForm => {
				for(userRow <- userDao.findById(requestForm.userId)) yield
					userRow match {
						case Some(user) =>
							if(user.password == requestForm.password) {
								// TODO session関係の処理を入れたい。
								Ok(views.html.user(user))
							} else {
								Ok(views.html.login("パスワード不一致"))
							}
						case None => Ok(views.html.login("ユーザーが存在しない"))
					}
			}
		)
	}
}