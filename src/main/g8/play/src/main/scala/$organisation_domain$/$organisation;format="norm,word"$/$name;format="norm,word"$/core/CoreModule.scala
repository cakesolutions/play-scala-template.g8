package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{Logger, LoggerLike}

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
