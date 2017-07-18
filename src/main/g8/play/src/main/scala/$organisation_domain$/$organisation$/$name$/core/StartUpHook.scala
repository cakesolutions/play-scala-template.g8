package $organisation_domain$.$organisation$.$name$.core

import javax.inject.Inject

trait StartUpHookBase {

  def run(): Unit
}

class StartUpHook @Inject()(startUpLogging: StartUpLogging)
    extends StartUpHookBase {

  run()

  def run(): Unit = {
    startUpLogging.log()
  }
}
