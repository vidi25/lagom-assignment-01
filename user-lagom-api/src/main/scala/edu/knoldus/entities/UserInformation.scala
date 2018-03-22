package edu.knoldus.entities

import play.api.libs.json.{Format, Json}

case class UserInformation(userId: Int,id: Int,title: String,body: String)

object UserInformation {

  implicit val format: Format[UserData] = Json.format
}