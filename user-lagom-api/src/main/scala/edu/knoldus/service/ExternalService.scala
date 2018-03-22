package edu.knoldus.service

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import edu.knoldus.entities.UserInformation

trait ExternalService extends Service {

  def getUser(): ServiceCall[NotUsed, UserInformation]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("external-service")
      .withCalls(
        pathCall("/posts/1", getUser _)
      ).withAutoAcl(true)
    // @formatter:on

  }
}
