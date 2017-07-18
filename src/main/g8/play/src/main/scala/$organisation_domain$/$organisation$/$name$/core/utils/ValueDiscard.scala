package $organisation_domain$.$organisation$.$name$.core.utils

/**
  * Function that allows values to be discarded in a visible way.
  */
object ValueDiscard {

  /**
    * Function that allows values to be discarded in a visible way.
    *
    * @tparam T type of the value that will be computed (it won't be inferred,
    *   must be specified)
    * @return function accepting value expression that needs to be computed and
    *   whose value will be discarded
    */
  def apply[T]: (=> T) => Unit = { value =>
    val _ = value
  }
}
