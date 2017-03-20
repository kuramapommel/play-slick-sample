package tools.slick.generator

import slick.codegen.SourceCodeGenerator
import com.typesafe.config._
import common.ApplicationConstants._

/**
	* Created by admin on 2017/02/17.
	*/
object SlickModelGenerator {

		def main(args: Array[String]): Unit = {
				val config = ConfigFactory.load()
				// SourceCodeGeneratorに渡す値を用意
				val slickDriver = "slick.driver.MySQLDriver"
				val jdbcDriver = "com.mysql.jdbc.Driver"
				val url = "jdbc:mysql://localhost:3306/play_slick"
				// 接続先MySQLのURI
				val user = config.getString(DB_USER_KEY)
				// 接続先MySQLの権限ユーザーID
				val password = config.getString(DB_PASSWORD_KEY) // 接続先MySQLの権限ユーザーpassword

				val outputFolder = "app"
				val pkg = "models" // 作成したmodelを配置するpackage

				SourceCodeGenerator.main(
						Array(
								slickDriver,
								jdbcDriver,
								url,
								outputFolder,
								pkg,
								user,
								password
						)
				)
		}

}
