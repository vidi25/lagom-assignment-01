package edu.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.entities.{User, UserData, UserInformation}
import edu.knoldus.service.{ExternalService, UserService}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class UserServiceImpl(externalService: ExternalService) extends UserService {

  val userList = new ListBuffer[User]

  override def createUser(): ServiceCall[User, Done] = ServiceCall {
    request =>
      val user = new User(request.id, request.name, request.salary)
      val userExists = userList.find(user => user.id == request.id)
      userExists match {
        case Some(user: User) => Future.successful(Done)
        case None =>
          userList += user
          Future.successful(Done)
      }
  }

  override def getUserDetails(id: Int): ServiceCall[NotUsed, String] = ServiceCall {
    _ =>
      val user = userList.find(user => user.id == id)
      user match {
        case Some(user: User) => Future.successful(s"User found is ${user.toString}")
        case None => Future.successful("User not found")
      }
  }

  override def updateUser(id: Int): ServiceCall[UserData, String] = ServiceCall {
    request =>
      val userExists = userList.find(user => user.id == id)
      userExists match {
        case Some(user: User) =>
          val updatedUser = user.copy(name = request.name, salary = request.salary)
          userList -= user
          userList += updatedUser
          Future.successful(s"Updated user list is ${userList.toString}")
        case None => Future.successful("User not found..!!")
      }
  }

  override def deleteUser(id: Int): ServiceCall[NotUsed, String] = ServiceCall {
    _ =>
      val user = userList.find(user => user.id == id)
      user match {
        case Some(user: User) =>
          userList -= user
          Future.successful(s"After deletion user list is ${userList.toString()}")
        case None =>
          Future.successful(s"User not found..!!!")
      }
  }

  override def testUser(): ServiceCall[NotUsed, UserInformation] = ServiceCall {
    _ =>
      val result = externalService.getUser().invoke()
      result.map(response => response)
  }
}
