package edu.knoldus.service

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import edu.knoldus.entities.{User, UserData, UserInformation}

trait UserService extends Service {

  def createUser(): ServiceCall[User, Done]

  def getUserDetails(id: Int): ServiceCall[NotUsed, String]

  def updateUser(id: Int): ServiceCall[UserData, String]

  def deleteUser(id: Int): ServiceCall[NotUsed, String]

  def testUser(): ServiceCall[NotUsed, UserInformation]

  override final def descriptor: Descriptor = {
    import Service._
    named("user-service")
      .withCalls(
        restCall(Method.POST, "/api/createUser", createUser _),
        restCall(Method.GET, "/api/getUser/:id", getUserDetails _),
        restCall(Method.PUT, "/api/updateUser/:id", updateUser _),
        restCall(Method.DELETE,"/api/deleteUser/:id", deleteUser _),
        pathCall("/api/user",testUser _)
      )
      .withAutoAcl(true)
  }
}
