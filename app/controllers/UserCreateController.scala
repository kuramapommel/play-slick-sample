package controllers

import javax.inject.Inject

import dao.UserDao
import form.SignUpForm
import models.Tables.UserRow
import play.api.data.Form
import play.api.data.Forms.{mapping, text, nonEmptyText}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
/**
	* Created by admin on 2017/02/22.
	*/
class UserCreateController @Inject()(userDao: UserDao)  extends Controller{

	val form = Form(
		mapping(
			SignUpForm.userId -> text(minLength = 8),
			SignUpForm.password -> nonEmptyText,
			SignUpForm.passwordConfirm -> nonEmptyText,
			SignUpForm.userName -> text(minLength = 1, maxLength = 20)
		)(SignUpForm.apply)(SignUpForm.unapply)
	)

	def transitionSignUp = Action{
		Ok(views.html.userCreate(""))
	}

	def registUser = Action.async{ implicit request =>
		form.bindFromRequest.fold(
			_ => Future.successful(Ok(views.html.login("バインドエラー"))),
			requestForm => {
				if(requestForm.password == requestForm.passwordConfirm)
					userDao.insert(requestForm).map(_ => Ok(views.html.userCreate("登録完了"))) else
					Future.successful(Ok(views.html.userCreate("確認用パスワードと一致しません")))
			}
		)
	}

	import java.sql.Timestamp
	implicit def formToUser(form: SignUpForm): UserRow =
		new UserRow(form.userId, form.password, Some(form.userName), Some(new Timestamp(System.currentTimeMillis())))

}
