package diode

import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.CtorType.Props
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.vdom.VdomElement

package object react {
  type ReactConnectProxy[A] =
    Props[ModelProxy[A] => VdomElement, Scala.Unmounted[ModelProxy[A] => VdomElement, A, _]]
}
