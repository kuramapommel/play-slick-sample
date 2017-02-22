package form

/**
	* Created by admin on 2017/02/21.
	*/
case class AuthForm(
                   userId: String,
                   password: String
                   );

object AuthForm{
	val userId = "userId"
	val password = "password"
}

case class SignUpForm(
                     userId: String,
                     password: String,
                     passwordConfirm: String,
                     userName: String
                     );
object SignUpForm{
	val userId = "userId"
	val password = "password"
	val passwordConfirm = "passwordConfirm"
	val userName = "userName"
}