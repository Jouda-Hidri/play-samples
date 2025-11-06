package controllers

import javax.inject._
import play.api.mvc._
import scala.collection.mutable


@Singleton
class SearchController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  def showForm = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.searchForm(request))
  }

  val words = mutable.Map[String, Int]()


  def submitSearch = Action { implicit request =>
    val searchTerm = request.body.asFormUrlEncoded
      .flatMap(_.get("query").flatMap(_.headOption))

    searchTerm match {
      case Some(word) =>
        // ✅ word is now a String
        words.update(word, words.getOrElse(word, 0) + 1)
        val sorted = words.toSeq.sortBy(-_._2)
        val top5 = sorted.take(5)
        Ok(s"Updated word map: $top5")

      case None =>
        BadRequest("No query provided")
    }
  }
}
