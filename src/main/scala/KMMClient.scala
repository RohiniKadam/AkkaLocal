import akka.actor.{ActorSystem, Props}
import com.diwo.common.akka.message.cassandra.helper.CassandraHelperCommand.CassandraQuery
import com.diwo.kmm.common.koq.util.YearWeek
import com.diwo.kmm.common.koq._
import com.diwo.kmm.common.koq.ko.{KOSparkQuery, QueryFilter}

object KMMClient extends App {

  val system = ActorSystem("AkkaLocal")

  val remote = system.actorSelection("akka.tcp://KMM@127.0.0.1:2551/user/KPRouter")

  val c = CassandraQuery("select * from agg_day_sku_sto limit 1")


  val attributes = List("itm_cls_desc", "itm_sbrnd_cd", "itm_sty_desc", "net_sales", "net_sls_qty")
  val optionalAttributes = List("sales variance ly", "sales variance", "sales contribution", "year", "week")
  val metric = Metric(List(MetricFilter("sales")))
  val viewFilter1 = ViewFilter(List(Filter("enh_est_age_val", Operator.LT, Some(20)),
    Filter("enh_est_age_val", Operator.GT, Some(30))))
  val rank = Rank.ALL
  val time = List(YearWeek(2017, 30))

  val kOSparkQuery = KOSparkQuery("agg_watch_cube", attributes, Some(QueryFilter(List(viewFilter1), Operator.AND)),
    Some(time), None)

  private val actor = system.actorOf(Props(classOf[MicrostrategyClient]), s"MicrostrategyClient")


  actor ! c

}
