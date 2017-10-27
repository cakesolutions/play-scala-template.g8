package $organisation_domain$.$organisation$.$name;format="norm,word"$.core

import java.lang.management.ManagementFactory
import javax.inject._

import play.api.{Configuration, LoggerLike}

/**
  * Defines standard logging of configuration, environment, properties and
  * system information.
  * @param config Typesafe configuration object
  * @param logger the logging adapter to which the logs will be sent
  */
class StartUpLogging @Inject()(
  config: Configuration,
  @Named("startUpLogging") logger: LoggerLike
) {

  /**
    * Logs environment, JVM, properties and configuration data.
    */
  def logAllTheThings(): Unit = {
    logEnvironmentData()
    logJVMData()
    logPropertyData()
    logConfigurationData(config)
  }

  private def logEnvironmentData(): Unit = {
    sys.env.toList.sortBy(_._1).foreach {
      case (key, value) =>
        logger.info(s"environment: \$key=\$value")
    }
  }

  private def logJVMData(): Unit = {
    val heap = ManagementFactory.getMemoryMXBean.getHeapMemoryUsage
    val nonHeap = ManagementFactory.getMemoryMXBean.getNonHeapMemoryUsage

    logger.info(
      "java.lang.memory.heap: committed=" +
        prettyPrintBytes(heap.getCommitted)
    )
    logger.info(
      s"java.lang.memory.heap: initial=\${prettyPrintBytes(heap.getInit)}"
    )
    logger.info(
      s"java.lang.memory.heap: maximum=\${prettyPrintBytes(heap.getMax)}"
    )
    logger.info(
      s"java.lang.memory.heap: used=\${prettyPrintBytes(heap.getUsed)}"
    )
    logger.info(
      "java.lang.memory.non-heap: committed=" +
        prettyPrintBytes(nonHeap.getCommitted)
    )
    logger.info(
      "java.lang.memory.non-heap: initial=" +
        prettyPrintBytes(nonHeap.getInit)
    )
    logger.info(
      s"java.lang.memory.non-heap: maximum=\${prettyPrintBytes(nonHeap.getMax)}"
    )
    logger.info(
      s"java.lang.memory.non-heap: used=\${prettyPrintBytes(nonHeap.getUsed)}"
    )
    logger.info(
      "runtime: available-processors=" +
        Runtime.getRuntime.availableProcessors().toString
    )
  }

  private def logPropertyData(): Unit = {
    sys.props.toList.sortBy(_._1).foreach {
      case (key, value) =>
        logger.info(s"property: \$key=\$value")
    }
  }

  private def logConfigurationData(config: Configuration): Unit = {
    val configData = config.entrySet.toList.sortBy(_._1)
    for ((key, value) <- configData) {
      logger.info(s"configuration: \$key=\${value.unwrapped}")
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  private def prettyPrintBytes(byteValue: Long): String = {
    val unit = 1000
    if (byteValue < 0) {
      "undefined"
    } else if (byteValue < unit) {
      s"\${byteValue}B"
    } else {
      val exp = (Math.log(byteValue.toDouble) / Math.log(unit.toDouble)).toInt
      val pre = "kMGTPE".charAt(exp - 1)
      f"\${byteValue / Math.pow(unit.toDouble, exp.toDouble)}%.1f\${pre}B"
    }
  }
}
