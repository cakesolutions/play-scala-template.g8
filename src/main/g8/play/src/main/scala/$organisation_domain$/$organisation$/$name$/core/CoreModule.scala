package $organisation_domain$.$organisation$.$name$.core

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ LoggerLike, Logger }

class CoreModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[LoggerLike])
      .annotatedWith(Names.named("startUpLogging"))
      .toInstance(Logger)

    bind(classOf[StartUpHookBase])
      .to(classOf[StartUpHook])
      .asEagerSingleton
  }

}
