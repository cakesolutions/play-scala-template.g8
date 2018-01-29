package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

/**
  * Performance test runner using Gatling
  */
@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
object Main {

  def main(args: Array[String]): Unit = {

    val gatlingProperties = new GatlingPropertiesBuilder
    gatlingProperties.simulationClass(
      classOf[$name;format="norm,Camel"$PerformanceTest].getCanonicalName
    )

    val runStatus: Int = Gatling.fromMap(gatlingProperties.build)

    sys.exit(runStatus)
  }
}
