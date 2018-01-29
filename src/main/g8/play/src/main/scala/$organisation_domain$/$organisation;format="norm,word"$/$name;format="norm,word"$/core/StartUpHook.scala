package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core

import javax.inject.Inject

trait StartUpHookBase {

  def run(): Unit
}

class StartUpHook @Inject()(startUpLogging: StartUpLogging) extends StartUpHookBase {

  run()

  def run(): Unit = {
    startUpLogging.logAllTheThings()
  }
}
