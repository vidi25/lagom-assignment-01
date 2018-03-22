package edu.knoldus.entities

import play.api.libs.json.{Format, Json}

case class UserData(name: String,salary: Int)

object UserData {

  implicit val format: Format[UserData] = Json.format
}
