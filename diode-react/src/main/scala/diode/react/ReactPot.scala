package diode.react

import diode.data.{PendingBase, Pot}
import diode.util._
import japgolly.scalajs.react.vdom.VdomElement

object ReactPot {

  import scala.language.implicitConversions

  implicit class potWithReact[A](val pot: Pot[A]) extends AnyVal {

    /**
      * Render non-empty (ready or stale) content
      *
      * @param f Transforms Pot value into a ReactNode
      * @return
      */
    def render(f: A => VdomElement): VdomElement =
      if (pot.nonEmpty) f(pot.get) else null

    /**
      * Render content in Ready state, not including stale states
      *
      * @param f Transforms Pot value into a VdomElement
      * @return
      */
    def renderReady(f: A => VdomElement): VdomElement =
      if (pot.isReady) f(pot.get) else null

    /**
      * Render when Pot is pending
      *
      * @param f Transforms duration time into a VdomElement
      * @return
      */
    def renderPending(f: Int => VdomElement): VdomElement =
      if (pot.isPending) f(pot.asInstanceOf[PendingBase].duration()) else null

    /**
      * Render when Pot is pending with a filter on duration
      *
      * @param b Filter based on duration value
      * @param f Transforms duration time into a VdomElement
      * @return
      */
    def renderPending(b: Int => Boolean, f: Int => VdomElement): VdomElement = {
      if (pot.isPending) {
        val duration = pot.asInstanceOf[PendingBase].duration()
        if (b(duration)) f(duration) else null
      } else null
    }

    /**
      * Render when Pot has failed
      *
      * @param f Transforms an exception into a VdomElement
      * @return
      */
    def renderFailed(f: Throwable => VdomElement): VdomElement =
      pot.exceptionOption.map(f).orNull

    /**
      * Render stale content (`PendingStale` or `FailedStale`)
      *
      * @param f Transforms Pot value into a VdomElement
      * @return
      */
    def renderStale(f: A => VdomElement): VdomElement =
      if (pot.isStale) f(pot.get) else null

    /**
      * Render when Pot is empty
      *
      * @param f Returns a VdomElement
      * @return
      */
    def renderEmpty(f: => VdomElement): VdomElement =
      if (pot.isEmpty) f else null
  }

}
