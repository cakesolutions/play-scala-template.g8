package $organisation_domain$.$organisation$.$name$.core

import javax.inject.Singleton

import com.google.inject.{AbstractModule, Provides}
import org.slf4j.LoggerFactory

object CoreModule {
  private val logger =
    LoggerFactory.getLogger(classOf[CoreModule])
}

class CoreModule()
  extends AbstractModule {
  import CoreModule._

    override def configure(): Unit = {}
}
