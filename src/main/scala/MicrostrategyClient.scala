/**
  * Created by onkar on 6/26/17.
  */

import akka.actor._
import akka.event.LoggingReceive
import com.diwo.common.http.error.UnconfiguredTable
import com.diwo.kmm.common.koq.util._
import com.diwo.kmm.common.koq.{Filter, _}

class MicrostrategyClient extends AbstractLoggingActor {
  //val remote = context.actorSelection("akka.tcp://KMM@172.25.30.33:2551/user/KPRouterActor")
  val remote = context.actorSelection("akka.tcp://KMM@127.0.0.1:2551/user/KPRouter")
  //val remote = context.actorSelection("akka.tcp://KMM@172.25.30.43:2553/user/MicrostrategyKOActor")
  //val remote = context.actorSelection("akka.tcp://kmm.diwo-kmm:2552/user/KPRouter")


  override def receive: Receive = LoggingReceive {
    case query: KmmQuery => {
      remote ! query
    }

    case query:KOQuery =>{
      remote ! query
    }

    case refreshCache:RefreshKMMCache=>
      remote ! refreshCache


    case (topic: String, query: KmmQuery) => {
      remote ! (topic, query)
    }

    case response: String =>
      log.info("Got Response : ")
      log.info(response)

    case UnconfiguredTable =>
    case x: Any =>
      log.info(s"Received $x from $sender")
  }
}

object Main extends App {

  val system = ActorSystem("AkkaLocal")

  private val actor = system.actorOf(Props(classOf[MicrostrategyClient]), s"MicrostrategyClient")

  //implicit val timeout = Timeout(120 seconds)
  //private val result = actor ? "init"
  //Await.result(result, 120 seconds)
  val metric1 = MetricFilter("Cost", Some(Operator.GEQ), Some(50000), Order.DESC)
  val metric2 = MetricFilter("Revenue", Some(Operator.LEQ), Some(250000), Order.DESC)

  val metric3 = MetricFilter("Cost", Order.DESC)
  val metric4 = MetricFilter("Revenue", Order.DESC)

  val vf1 = Filter("Distribution Center", Operator.IN, List("Seattle", "San Diego"))
  val vf2 = Filter("Supplier", Operator.IN, List("McGraw Hill"))
  val time1 = YearMonth(2016, 7)
  val query: KmmQuery = KmmQuery(List("Year", "Month", "Call Center", "Category"), Metric(List(metric1, metric2)), ViewFilter(List(vf1)), List(time1), Rank.TOP, 5)
  //private val future = actor ? query

  /*val f = future.onComplete {
    case Success(value) => println("Got the response : " + value)
    case Failure(e) =>

      e.printStackTrace()
  }*/

  // actor ! RefreshMetadata("5731DB3F4496967971E88AB7602D63A6")

  //Thread.sleep(20000)
  //println("Woke up !!!")

  val time2 = YearMonth(2015, 6)
  val query2: KmmQuery = KmmQuery(List("Distribution Center", "Supplier", "Year", "Month", "Category", "Region"), Metric(List(metric3, metric4)),
    ViewFilter(List(vf1, vf2)), List(time1, time2), Rank.ALL)

  //actor ! ("MICROSTRATEGY", query2)

  //system.scheduler.schedule(5.seconds, 15.seconds, actor, ("MICROSTRATEGY", query2))


  // revenue of red for week 38
  // KMM Query (Outdated)
/*  val attributes = List("colorfamily", "year","week")
  val metric = Metric(List(MetricFilter("net_sales")))
  val viewFilter = ViewFilter(List(
    Filter("colorfamily", Operator.EQ, "RED")))
  val rank = Rank.ALL
  val time = List(YearWeek(2016, 34),YearWeek(2016, 36),YearWeek(2016, 37),YearWeek(2016, 38))*/
  // KMM Query (Outdated)

  // KMM Query New  max_week_rev_cfmly
  /*val attributes = List("color family")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("colorfamily", Operator.EQ, "RED")))
  val rank = Rank.TOP
  val time = List(YearWeek(2016, 34))
  val time = List(YearWeek(2016, 34), YearWeek(2016, 36), YearWeek(2016, 37), YearWeek(2016, 38))*/

  // revenue of vsx knockout front close bra in village pointer
  // KMM Query (Outdated)
  /*val attributes = List("itm_msty_cd")
  val metric = Metric(List(MetricFilter("net_sales")))
  val viewFilter = ViewFilter(List(
    Filter("itm_msty_cd", Operator.IN, "11083507"),
    Filter("loc_id", Operator.IN, "1017268")))
  val rank = Rank.ALL
  val time = List(YearMonthDay(2016, 9, 28))*/

  // KMM Query New
/*    val attributes = List.empty
    val metric = Metric(List(MetricFilter("revenue")))
    val viewFilter = ViewFilter(List(
      Filter("itm-msty-cd", Operator.IN, "5000004686"),
      Filter("loc-id", Operator.IN, "1017268")))
    val rank = Rank.TOP
    val time = List(YearMonthDay(2016, 9, 28))*/


  // style with max revenue in vsx knockout front close bra in village pointer
  // KMM Query (Outdated)
  /*val attributes = List("sty_num")
  val metric = Metric(List(MetricFilter("net_sales")))
  val viewFilter = ViewFilter(List(
    Filter("itm_msty_cd", Operator.IN, "5000004686"),
    Filter("loc_id", Operator.IN, "1017268")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 28))*/

  // KMM Query New
/*  val attributes = List("Product Style")
    val metric = Metric(List(MetricFilter("revenue")))
    val viewFilter = ViewFilter(List(
      Filter("itm-msty-cd", Operator.IN, "5000004686"),
      Filter("loc-id", Operator.IN, "1017268")))
    val rank = Rank.TOP
    val time = List(YearMonthDay(2016, 9, 28))*/

  // ??
/*  val attributes = List("size")
  val metric = Metric(List(MetricFilter("Revenue")))
  val viewFilter = ViewFilter(List(
    Filter("sz1-cd", Operator.IN, "size")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 28))*/

  // product with max revenue in in VSX KNOCKOUT FRONT CLOSE BRA
/*  val attributes = List("SKU", "size")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("itm-msty-cd", Operator.IN, "5000004686")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 28))*/

  // revenue of small size in vs sport angel n i in village pointe
/*  val attributes = List.empty
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("sz1-cd", Operator.IN, "SMALL"),
    Filter("sty-num", Operator.IN, "11042790"),
    Filter("loc-id", Operator.IN, "1017268")))
  val rank = Rank.ALL
  val time = List(YearMonthDay(2016, 9, 28))*/

/*  val attributes = List()
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("itm-scls-cd", Operator.IN, "11020002")))
  val rank = Rank.ALL
  val time = List(YearMonthDay(2016,9,9))*/

  // ***********

  //1 127545fee443b9a236b784afd70c201d
/*  val attributes = List.empty
  val metric = Metric(List(MetricFilter("revenue", Some(Operator.GEQ), Some(4000))))
  val viewFilter = ViewFilter(List(
    Filter("colorfamily", Operator.IN, "Black")))
  val rank = Rank.ALL
  val time = List(YearWeek(2016, 39))*/

  //2   f9af368192e73258ff415b738e0ca5ab
/*  val attributes = List("color family","product class")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("itm-cls-cd",Operator.IN,"40210107"),
    Filter("prod-ctgy-cd", Operator.IN, "3021")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 15))*/

  //3 60a90931bf79b87242867c32f3a75a38
/*  val attributes = List("year", "week")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("colorfamily", Operator.IN, "Black")))
  val rank = Rank.ALL
  val time = List(YearWeek(2016, 35), YearWeek(2016, 36), YearWeek(2016, 37), YearWeek(2016, 38), YearWeek(2016, 39))*/

  //4  83033e93d539912750bc89357f6d7f83
/*  val attributes = List("color family", "year", "week")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter =
    ViewFilter(List(Filter("colorfamily", Operator.EQ, "Floral Jacobean")))
  val rank = Rank.ALL
  val time = List(YearWeek(2016, 37))*/


  //5  2da9b7078a7943bfe99ed63a57041631
/*  val attributes = List("color family")
  val metric = Metric(List(MetricFilter("revenue")))
  val viewFilter = ViewFilter(List(
    Filter("prod-ctgy-cd", Operator.IN, "3021"),
    Filter("itm-cls-cd", Operator.IN, "40210107")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 15))*/

  //6 e44ac7d69e8b9c9843a8cebc429b6ad3
/*  val attributes = List("Class")
  val metric = Metric(List(MetricFilter("revenue", Some(Operator.GEQ), Some(200))))
  val viewFilter = ViewFilter(List(
    Filter("prod-ctgy-cd", Operator.IN, "3020")))
  val rank = Rank.TOP
  val time = List(YearMonthDay(2016, 9, 9))*/

  //7 9dfd0619e3bd20ac985f9f53b48015cb
    val attributes = List("color family","product class")
    val metric = Metric(List(MetricFilter("revenue")))
    val viewFilter = ViewFilter(List(
      Filter("itm-cls-cd",Operator.IN,"40210107"),
      Filter("prod-ctgy-cd", Operator.IN, "3021")))
    val rank = Rank.TOP
    val time = List(YearWeek(2016,36))

  val query3 = KmmQuery(attributes, metric, viewFilter, time, rank, 3)

// KOQuery
//  val name="cust_anly_week_cls"
//  val attributes=List("max-age", "min-age", "max-income", "min-income", "avg-basket-value")
//  val viewFilter=ViewFilter(List(Filter("itm-cls-cd" ,Operator.IN,"40200365")))
//  val td=List(YearWeek(2017,32))
//
//  val query3 = KOQuery(name,attributes,Some(viewFilter),Some(td))

  actor ! query3

  // To refresh KMM cache
  // actor ! RefreshKMMCache("Refresh Cache")

  //  actor ! ("CASSANDRA", query3)
  //system.scheduler.schedule(5.seconds, 15.seconds, actor, ("CASSANDRA", query3))
}
